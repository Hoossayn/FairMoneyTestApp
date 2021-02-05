package com.example.fairmoneytestapp.ui.onboarding.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fairmoneytestapp.R
import com.example.fairmoneytestapp.ui.listUser.MainActivity
import com.example.fairmoneytestapp.ui.userDetail.UserDetailActivity
import kotlinx.android.synthetic.main.fourth_fragment.*

class FourthFragment : Fragment() {

    companion object {
        fun newInstance() = FourthFragment()
    }

    private lateinit var viewModel: FourthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fourth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FourthViewModel::class.java)


        continue_btn.setOnClickListener {
            this.startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

}