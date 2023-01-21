package com.example.itmo_2022.lab3

import io.ktor.client.statement.*
import io.ktor.http.*

interface IRequests {

    suspend fun getAllGames(): List<GameModel>
    suspend fun getGame(id: Int): GameModel?
    suspend fun createGame(
        title: String,
        year: Int,
        description: String,
        imageURL: String
    ): HttpStatusCode

    suspend fun updateGame(
        id: Int,
        title: String,
        year: Int,
        description: String,
        imageURL: String
    ): HttpStatusCode

    suspend fun deleteGame(id: Int): HttpStatusCode
}