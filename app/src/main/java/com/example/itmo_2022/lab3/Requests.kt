package com.example.itmo_2022.lab3

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class Requests : IRequests {

    //private val baseURL = "http://10.0.2.2:8080"
    private val baseURL = "http://192.168.3.2:8080"
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun getAllGames(): List<GameModel> {
        return client.get("$baseURL/games").body()
    }

    override suspend fun getGame(id: Int): GameModel? {
        val res = client.get("$baseURL/games/$id")

        return if (res.status == HttpStatusCode.OK)
            res.body()
        else
            null
    }

    override suspend fun createGame(
        title: String,
        year: Int,
        description: String,
        imageURL: String
    ): HttpStatusCode {
        val game = GameModel(1, title, year, description, imageURL)
        return client.post("$baseURL/games") {
            contentType(ContentType.Application.Json)
            setBody(game)
        }.status
    }

    override suspend fun updateGame(
        id: Int,
        title: String,
        year: Int,
        description: String,
        imageURL: String
    ): HttpStatusCode {
        val game = GameModel(id, title, year, description, imageURL)
        return client.put("$baseURL/games") {
            contentType(ContentType.Application.Json)
            setBody(game)
        }.status
    }

    override suspend fun deleteGame(id: Int): HttpStatusCode {
        return client.delete("$baseURL/games/$id").status
    }
}

val requests: IRequests = Requests()