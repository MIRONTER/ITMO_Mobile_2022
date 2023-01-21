package com.example.itmo_2022.lab3

import kotlinx.serialization.Serializable

@Serializable
data class GameModel (val id: Int, val title: String, val year: Int, val description: String, val imageURL: String)
