package com.felipe.dailyhelper.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.felipe.dailyhelper.R

class WorkFragment : Fragment() {

    private lateinit var tvNoWorkLog: TextView

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
    }
}
