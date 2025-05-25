package com.example.dogapp.bloc

import com.example.dogapp.model.DogImage

sealed class DogState {
    object Initial : DogState()
    object Loading : DogState()
    data class Loaded(val dogImage: DogImage) : DogState()
    data class Error(val message: String) : DogState()
} 