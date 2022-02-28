package com.example.smartgame.api

import com.example.smartgame.model.Games
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface CallGames {

    @GET("game")
    fun getGame(): Call<List<Games>>

    @GET("game/{id}")
    fun getOneGame(@Path("id") id: Int): Call<Games>

    @PUT("game/{id}")
    fun getDiscount(@Path("id") id: Int): Call<Games>
}
