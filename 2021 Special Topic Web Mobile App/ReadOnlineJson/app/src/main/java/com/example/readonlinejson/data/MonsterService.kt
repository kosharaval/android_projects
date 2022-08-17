package com.example.readonlinejson.data

import retrofit2.Response
import retrofit2.http.GET

interface MonsterService {
    @GET("/feed/monster_data.json")
    suspend fun getMonsterData(): Response<List<Monster>>

    // actual data: https://774906.youcanlearnit.net/feed/monster_data.json
}