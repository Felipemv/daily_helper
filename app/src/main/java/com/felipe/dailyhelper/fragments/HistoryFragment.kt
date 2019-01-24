package com.felipe.dailyhelper.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felipe.dailyhelper.R
import com.felipe.dailyhelper.adapters.HistoryAdapter
import com.felipe.dailyhelper.database.entities.WorkLog
import com.felipe.dailyhelper.database.repository.WorkLogRepository
import com.felipe.dailyhelper.listeners.OnItemClicked
import com.felipe.dailyhelper.viewmodel.HistoryListViewModel

class HistoryFragment : Fragment(), Observer<List<WorkLog>>, OnItemClicked.OnHistoryItemClicked {

    private lateinit var viewModel: HistoryListViewModel

    private lateinit var adapter: HistoryAdapter

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HistoryListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_history, container, false)

        initComponents(view)

        viewModel.getHistory().observe(this, Observer<List<WorkLog>> { history ->
            history?.let {
                populateHistory(history)
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        setObserver(getWorkLogRepository().findAll())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_sort, menu)
    }

    override fun onChanged(t: List<WorkLog>?) {
    }

    override fun onHistoryClick(id: Int, history: WorkLog) {
    }

    private fun initComponents(view: View) {
        recyclerView = view.findViewById(R.id.rv_history)
    }

    private fun populateHistory(history: List<WorkLog>) {
        if (recyclerView.adapter == null) {
            adapter = HistoryAdapter(history, this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            adapter.updateView(history)
        }
    }

    private fun getWorkLogRepository(): WorkLogRepository {
        return WorkLogRepository.getInstance(context!!)
    }

    private fun setObserver(list: LiveData<List<WorkLog>>) {
        list.observe(this, this)
    }
}
