package com.example.fairmoneytestapp.ui.listUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fairmoneytestapp.R
import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.databinding.ActivityMainBinding
import com.example.fairmoneytestapp.ui.userDetail.UserDetailActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),MainActivityAdapter.CharacterItemListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainActivityAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        viewModel.getSubjects()

    }

    private fun setupRecyclerView() {
        adapter = MainActivityAdapter(this)
        binding.charactersRv.layoutManager = LinearLayoutManager(this)
        binding.charactersRv.adapter = adapter
    }


    private fun setupObservers() {
        viewModel.fetchingSubject.observe(this, Observer {
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

        viewModel.subjects.observe(this, Observer {
            if (it.isNotEmpty())
                adapter.setItems(it)
        })

    }


    override fun onClickedCharacter(characterId: String) {
        Toast.makeText(this,characterId, Toast.LENGTH_SHORT).show()

        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.EXTRAS_MOVIE_ID, characterId)
        startActivity(intent)
    }

    private fun showError(msg: String) {
        Snackbar.make(vparent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}