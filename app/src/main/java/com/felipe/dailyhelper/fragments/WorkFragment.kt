package com.felipe.dailyhelper.fragments


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.felipe.dailyhelper.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class WorkFragment : Fragment() {

    private lateinit var tvNoWorkLog: TextView
    private lateinit var fabNewWorkLog: FloatingActionButton
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_work, container, false)

        initComponents(view)

        return view
    }


    private fun initComponents(view: View) {
        tvNoWorkLog = view.findViewById(R.id.tv_no_work_log)
        fabNewWorkLog = view.findViewById(R.id.fab_add_new_work_log)

        fabNewWorkLog.setOnClickListener(addNewWorkLog())
    }

    private fun addNewWorkLog(): View.OnClickListener {
        return View.OnClickListener {

            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.layout_new_work_log, null)

            (view.findViewById(R.id.ib_choose_date) as ImageButton).setOnClickListener(chooseDate())
            (view.findViewById(R.id.ib_choose_time) as ImageButton).setOnClickListener(chooseTime())
            tvDate = view.findViewById(R.id.tv_date)
            tvTime = view.findViewById(R.id.tv_time)

            tvDate.text = getDate()
            tvTime.text = getTime()

            val builder = AlertDialog.Builder(context!!)
            builder.setView(view)
            builder.setPositiveButton("Create") { _, _ ->
                Toast.makeText(context!!, "oaisjdfoia", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(context!!, "asdfasdf", Toast.LENGTH_SHORT).show()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun chooseDate(): View.OnClickListener {
        return View.OnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                tvDate.text = getDate(day, month, year)
            }, year, month, day)

            datePicker.show()
        }
    }

    private fun chooseTime(): View.OnClickListener {
        return View.OnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minutes = c.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(
                context!!,
                TimePickerDialog.OnTimeSetListener { _: TimePicker, hour: Int, minutes: Int ->
                    val text = "$hour:$minutes"
                    tvTime.text = getTime(hour, minutes)
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

        if (hour == -1 || minutes == -1 ) {
            hour1 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            minutes1 = Calendar.getInstance().get(Calendar.MINUTE)
        }

        val h = if (hour1 < 10) "0$hour1" else hour1
        var m = if (minutes1 < 10) "0$minutes1" else minutes1

        return "$h:$m"
    }
}
