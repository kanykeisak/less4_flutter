package com.example.dogapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.dogapp.bloc.DogBloc
import com.example.dogapp.bloc.DogEvent
import com.example.dogapp.bloc.DogState
import com.example.dogapp.repository.DogRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var dogBloc: DogBloc
    private lateinit var imageView: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView
    private lateinit var fetchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        imageView = findViewById(R.id.dogImageView)
        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)
        fetchButton = findViewById(R.id.fetchButton)

        // Initialize BLoC
        dogBloc = DogBloc(DogRepository())

        // Set up button click listener
        fetchButton.setOnClickListener {
            dogBloc.onEvent(DogEvent.FetchDogImage)
        }

        // Observe state changes
        lifecycleScope.launch {
            dogBloc.state.collectLatest { state ->
                updateUI(state)
            }
        }

        // Initial fetch
        dogBloc.onEvent(DogEvent.FetchDogImage)
    }

    private fun updateUI(state: DogState) {
        when (state) {
            is DogState.Initial -> {
                progressBar.visibility = View.VISIBLE
                imageView.visibility = View.GONE
                errorText.visibility = View.GONE
            }
            is DogState.Loading -> {
                progressBar.visibility = View.VISIBLE
                imageView.visibility = View.GONE
                errorText.visibility = View.GONE
            }
            is DogState.Loaded -> {
                progressBar.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                errorText.visibility = View.GONE
                imageView.load(state.dogImage.message) {
                    crossfade(true)
                    error(R.drawable.ic_error)
                }
            }
            is DogState.Error -> {
                progressBar.visibility = View.GONE
                imageView.visibility = View.GONE
                errorText.visibility = View.VISIBLE
                errorText.text = state.message
            }
        }
    }
} 