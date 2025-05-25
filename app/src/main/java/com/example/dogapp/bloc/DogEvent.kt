package com.example.dogapp.bloc

sealed class DogEvent {
    object FetchDogImage : DogEvent()
} 