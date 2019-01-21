package com.felipe.dailyhelper.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.felipe.dailyhelper.R
import com.felipe.dailyhelper.database.repository.UserRepository
import com.felipe.dailyhelper.util.PreferencesUtil

class ProfileFragment : Fragment() {

    private lateinit var titleName: TextView
    private lateinit var tvName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        initComponents(view)
        loadUser()

        return view
    }

    private fun initComponents(view: View) {
        titleName = view.findViewById(R.id.title_name)
        tvName = view.findViewById(R.id.tv_name)
        tvUsername = view.findViewById(R.id.tv_username)
        tvEmail = view.findViewById(R.id.tv_email)
        tvPhone = view.findViewById(R.id.tv_phone)
    }

    private fun loadUser() {
        val user = UserRepository.getInstance(activity!!).find(PreferencesUtil(activity!!).userId)

        if (user != null) {
            titleName.text = user.username
            tvName.text = user.name
            tvUsername.text = user.username
            tvEmail.text = user.email
            tvPhone.text = user.phone
        }
    }

}
