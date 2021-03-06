package com.felipe.dailyhelper.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.felipe.dailyhelper.R
import com.felipe.dailyhelper.adapters.WorkLogAdapter
import com.felipe.dailyhelper.database.entities.WorkLog
import com.felipe.dailyhelper.database.repository.WorkLogRepository
import com.felipe.dailyhelper.viewmodel.WorkLogListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import androidx.recyclerview.widget.LinearLayoutManager
import com.felipe.dailyhelper.listeners.OnItemClicked
import com.felipe.dailyhelper.util.DateUtil
import java.util.*


class WorkFragment : Fragment(), Observer<List<WorkLog>>,
    OnItemClicked.OnWorkLogItemClicked {

    private lateinit var viewModel: WorkLogListViewModel

    private lateinit var adapter: WorkLogAdapter

    private lateinit var tvNoWorkLog: TextView
    private lateinit var fabNewWorkLog: FloatingActionButton
    private lateinit var tvTitle: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView
    private lateinit var ibChooseDate: ImageButton
    private lateinit var ibChooseTime: ImageButton
    private lateinit var recyclerView: RecyclerView

    private lateinit var tvEditDate: TextView
    private lateinit var tvEditMorningIn: TextView
    private lateinit var tvEditMorningOut: TextView
    private lateinit var tvEditAfternoonIn: TextView
    private lateinit var tvEditAfternoonOut: TextView

    private lateinit var ibChooseEditDate: ImageButton
    private lateinit var ibChooseEditMorningIn: ImageButton
    private lateinit var ibChooseEditMorningOut: ImageButton
    private lateinit var ibChooseEditAfternoonIn: ImageButton
    private lateinit var ibChooseEditAfternoonOut: ImageButton

    companion object {
        const val BUTTON_FINISH = 1
        const val BUTTON_EDIT = 2
        const val LAYOUT = 3

        const val MORNING_IN = 1
        const val MORNING_OUT = 2
        const val AFTERNOON_IN = 3
        const val AFTERNOON_OUT = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WorkLogListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_work, container, false)

        initComponents(view)

        viewModel.getWorkLogList().observe(this, Observer<List<WorkLog>> { workLogs ->
            workLogs?.let {
                populateWorkLogList(workLogs)
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()

        setObserver(getWorkLogRepository().getUndone())
    }

    override fun onChanged(list: List<WorkLog>?) {
        if (list == null || list.isEmpty()) {
            recyclerView.visibility = View.GONE
            tvNoWorkLog.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            tvNoWorkLog.visibility = View.GONE
            populateWorkLogList(list)
        }

    }

    override fun onWorkLogClick(id: Int, workLog: WorkLog) {
        when (id) {
            BUTTON_FINISH -> {
                getWorkLogRepository().setDone(workLog.id)
            }
            BUTTON_EDIT -> {
                setEditLayout(workLog)
            }
            LAYOUT -> {
                if (workLog.firstIn == 0L) {
                    Toast.makeText(context!!, "Um erro ocorreu", Toast.LENGTH_SHORT).show()
                } else if (workLog.firstOut == 0L) {
                    setUpWorkLogDialog(MORNING_OUT, workLog)
                } else if (workLog.secondIn == 0L) {
                    setUpWorkLogDialog(AFTERNOON_IN, workLog)
                } else if (workLog.secondOut == 0L) {
                    setUpWorkLogDialog(AFTERNOON_OUT, workLog)
                } else {
                    askIfItsDone(workLog.id)
                }
            }
        }
    }

    private fun initComponents(view: View) {
        tvNoWorkLog = view.findViewById(R.id.tv_no_work_log)
        fabNewWorkLog = view.findViewById(R.id.fab_add_new_work_log)
        recyclerView = view.findViewById(R.id.rv_work_log)

        fabNewWorkLog.setOnClickListener { setUpWorkLogDialog(MORNING_IN) }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.deleteItem(position, context!!)
            }
        }).attachToRecyclerView(recyclerView)
    }

    private fun getWorkLogRepository(): WorkLogRepository {
        return WorkLogRepository.getInstance(context!!)
    }

    private fun setObserver(list: LiveData<List<WorkLog>>) {
        list.observe(this, this)
    }

    private fun populateWorkLogList(workLogList: List<WorkLog>) {
        if (recyclerView.adapter == null) {
            adapter = WorkLogAdapter(workLogList, this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            adapter.updateView(workLogList)
        }
    }

    private fun setUpWorkLogDialog(operation: Int, workLog: WorkLog? = null) {
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_new_work_log, null)

        prepareWorkLogLayout(view)
        setOperation(operation, workLog)

        val builder = AlertDialog.Builder(context!!)
        builder.setView(view)
        builder.setPositiveButton("Save") { _, _ ->
            saveOperation(operation, workLog, tvDate.text.toString(), tvTime.text.toString())
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun prepareWorkLogLayout(view: View) {
        ibChooseDate = view.findViewById(R.id.ib_choose_date)
        ibChooseTime = view.findViewById(R.id.ib_choose_time)

        tvDate = view.findViewById(R.id.tv_date)
        tvTime = view.findViewById(R.id.tv_time)
        tvTitle = view.findViewById(R.id.tv_title)

        ibChooseDate.setOnClickListener(chooseDate(tvDate))
        ibChooseTime.setOnClickListener(chooseTime(tvTime))

        tvDate.text = DateUtil.getCurrentStringDate()
        tvTime.text = DateUtil.getCurrentStringTime()

    }

    private fun setEditLayout(workLog: WorkLog?) {
        if (workLog != null) {
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.layout_edit_work_log, null)

            prepareEditLayout(view, workLog)

            val builder = AlertDialog.Builder(context!!)
            builder.setView(view)
            builder.setPositiveButton("Save") { _, _ ->
                val date = tvEditDate.text.toString()
                workLog.date = DateUtil.dateStringToLong(date)
                workLog.firstIn = DateUtil.timeStringToLong(tvEditMorningIn.text.toString(), date)
                workLog.firstOut = DateUtil.timeStringToLong(tvEditMorningOut.text.toString(), date)
                workLog.secondIn = DateUtil.timeStringToLong(tvEditAfternoonIn.text.toString(), date)
                workLog.secondOut = DateUtil.timeStringToLong(tvEditAfternoonOut.text.toString(), date)

                workLog.lunchTime = workLog.secondIn - workLog.firstOut
                workLog.total = workLog.secondOut - workLog.secondIn + workLog.firstOut - workLog.firstIn

                WorkLogRepository.getInstance(context!!).update(workLog)
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        } else {
            Toast.makeText(context!!, "An error occurred.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun prepareEditLayout(view: View, workLog: WorkLog) {
        tvEditDate = view.findViewById(R.id.tv_edit_date)
        tvEditMorningIn = view.findViewById(R.id.tv_edit_morning_in)
        tvEditMorningOut = view.findViewById(R.id.tv_edit_morning_out)
        tvEditAfternoonIn = view.findViewById(R.id.tv_edit_afternoon_in)
        tvEditAfternoonOut = view.findViewById(R.id.tv_edit_afternoon_out)

        tvEditDate.text = DateUtil.dateLongToString(workLog.date)
        tvEditMorningIn.text = DateUtil.timeLongToString(workLog.firstIn)
        tvEditMorningOut.text = DateUtil.timeLongToString(workLog.firstOut)
        tvEditAfternoonIn.text = DateUtil.timeLongToString(workLog.secondIn)
        tvEditAfternoonOut.text = DateUtil.timeLongToString(workLog.secondOut)

        ibChooseEditDate = view.findViewById(R.id.ib_choose_edit_date)
        ibChooseEditMorningIn = view.findViewById(R.id.ib_choose_edit_morning_in)
        ibChooseEditMorningOut = view.findViewById(R.id.ib_choose_edit_morning_out)
        ibChooseEditAfternoonIn = view.findViewById(R.id.ib_choose_edit_afternoon_in)
        ibChooseEditAfternoonOut = view.findViewById(R.id.ib_choose_edit_afternoon_out)

        ibChooseEditDate.setOnClickListener(chooseDate(tvEditDate))
        ibChooseEditMorningIn.setOnClickListener(chooseTime(tvEditMorningIn))
        ibChooseEditMorningOut.setOnClickListener(chooseTime(tvEditMorningOut))
        ibChooseEditAfternoonIn.setOnClickListener(chooseTime(tvEditAfternoonIn))
        ibChooseEditAfternoonOut.setOnClickListener(chooseTime(tvEditAfternoonOut))
    }

    private fun askIfItsDone(id: Int) {

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Are you done?")
        builder.setMessage("It seems that you have finished this work log. Do you want to confirm it as done?")
        builder.setPositiveButton("Confirm") { _, _ ->
            WorkLogRepository.getInstance(context!!).setDone(id)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun setOperation(operation: Int, workLog: WorkLog? = null) {
        when (operation) {
            MORNING_IN -> {
                tvTitle.text = "Morning In"
                ibChooseDate.visibility = View.VISIBLE
            }
            MORNING_OUT -> {
                tvTitle.text = "Morning Out"
                tvDate.text = DateUtil.dateLongToString(workLog!!.date)
                ibChooseDate.visibility = View.INVISIBLE

            }
            AFTERNOON_IN -> {
                tvTitle.text = "Afternoon In"
                tvDate.text = DateUtil.dateLongToString(workLog!!.date)
                ibChooseDate.visibility = View.INVISIBLE
            }
            AFTERNOON_OUT -> {
                tvTitle.text = "Afternoon Out"
                tvDate.text = DateUtil.dateLongToString(workLog!!.date)
                ibChooseDate.visibility = View.INVISIBLE
            }
        }
    }

    private fun saveOperation(operation: Int, workLog: WorkLog?, date: String, time: String) {
        when (operation) {
            MORNING_IN -> {
                registerFirstIn(
                    WorkLog(
                        DateUtil.dateStringToLong(date),
                        DateUtil.timeStringToLong(time, date)
                    )
                )
            }
            MORNING_OUT -> {
                workLog!!.firstOut = DateUtil.timeStringToLong(time, date)
                registerFirstOut(workLog)
            }
            AFTERNOON_IN -> {
                workLog!!.secondIn = DateUtil.timeStringToLong(time, date)
                registerSecondIn(workLog)
            }
            AFTERNOON_OUT -> {
                workLog!!.secondOut = DateUtil.timeStringToLong(time, date)
                registerSecondOut(workLog)
            }
        }
    }

    private fun chooseDate(textView: TextView): View.OnClickListener {
        return View.OnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                textView.text = getDate(day, month, year)
            }, year, month, day)

            datePicker.show()
        }
    }

    private fun chooseTime(textView: TextView): View.OnClickListener {
        return View.OnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minutes = c.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(
                context!!,
                TimePickerDialog.OnTimeSetListener { _: TimePicker, hour: Int, minutes: Int ->
                    val text = "$hour:$minutes"
                    textView.text = getTime(hour, minutes)
                },
                hour,
                minutes,
                true
            )

            timePicker.show()
        }

    }

    private fun getDate(day: Int = -1, month: Int = -1, year: Int = -1): String {
        var day1 = day
        var month1 = month
        var year1 = year

        if (day == -1 || month == -1 || year == -1) {
            day1 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            month1 = Calendar.getInstance().get(Calendar.MONTH)
            year1 = Calendar.getInstance().get(Calendar.YEAR)
        }

        val d = if (day1 < 10) "0$day1" else day1
        var m = (month1 + 1).toString()
        m = if (m.toInt() < 10) "0$m" else m

        return "$d/$m/$year1"
    }

    private fun getTime(hour: Int = -1, minutes: Int = -1): String {

        var hour1 = hour
        var minutes1 = minutes

        if (hour == -1 || minutes == -1) {
            hour1 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            minutes1 = Calendar.getInstance().get(Calendar.MINUTE)
        }

        val h = if (hour1 < 10) "0$hour1" else hour1
        var m = if (minutes1 < 10) "0$minutes1" else minutes1

        return "$h:$m"
    }

    private fun registerFirstIn(workLog: WorkLog?) {
        getWorkLogRepository().logFirstIn(workLog!!)
    }

    private fun registerFirstOut(workLog: WorkLog?) {
        getWorkLogRepository().logFirstOut(workLog!!.firstIn, workLog.firstOut, workLog.id)
    }

    private fun registerSecondIn(workLog: WorkLog?) {
        getWorkLogRepository().logSecondIn(workLog!!.firstOut, workLog.secondIn, workLog.id)
    }

    private fun registerSecondOut(workLog: WorkLog?) {
        getWorkLogRepository().logSecondOut(workLog!!.secondIn, workLog.secondOut, workLog.id, workLog.total)
    }
}
