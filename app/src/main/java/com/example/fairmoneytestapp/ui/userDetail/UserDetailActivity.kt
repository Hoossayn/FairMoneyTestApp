package com.example.fairmoneytestapp.ui.userDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.fairmoneytestapp.R
import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.data.model.entities.User
import com.example.fairmoneytestapp.databinding.ActivityMainBinding
import com.example.fairmoneytestapp.databinding.ActivityUserDetailBinding
import com.example.fairmoneytestapp.ui.listUser.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_detail.*

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel: UserDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.getStringExtra(EXTRAS_MOVIE_ID)?.let { id ->
            viewModel.getUserDetail(id)
            setupObservers(id)
        } ?: showError("Unknown Movie")

    }

    private fun setupObservers(id:String) {

        viewModel.getUserFromRemote(id).observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                }
                Resource.Status.ERROR ->
                    it.message?.let {
                        showError(it)
                        binding.progressBar.visibility = View.GONE

                    }

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })

        viewModel.userFromLocal(id).observe(this, Observer {
            if (it != null)
                bindCharacter(it)
        })

    }


    private fun bindCharacter(user: User) {
        binding.name.text = "${user.firstName} ${user.lastName}"
        binding.emailAddress.text = user.email
        binding.phoneNumber.text = user.phone
        Glide.with(binding.root)
            .load(user.picture)
            .transform(CircleCrop())
            .into(binding.image)
    }

    private fun showError(msg: String) {
        Snackbar.make(vDetails, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }

    companion object {
        const val EXTRAS_MOVIE_ID = "movie_id"
    }
}