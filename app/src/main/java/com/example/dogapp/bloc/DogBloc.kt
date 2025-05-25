package com.example.dogapp.bloc

import com.example.dogapp.repository.DogRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogBloc(private val repository: DogRepository) {
    private val _state = MutableStateFlow<DogState>(DogState.Initial)
    val state: StateFlow<DogState> = _state

    fun onEvent(event: DogEvent) {
        when (event) {
            is DogEvent.FetchDogImage -> fetchDogImage()
        }
    }

    private fun fetchDogImage() {
        CoroutineScope(Dispatchers.IO).launch {
            _state.value = DogState.Loading
            try {
                val dogImage = repository.getRandomDogImage()
                _state.value = DogState.Loaded(dogImage)
            } catch (e: Exception) {
                _state.value = DogState.Error(e.message ?: "Unknown error")
            }
        }
    }
} 