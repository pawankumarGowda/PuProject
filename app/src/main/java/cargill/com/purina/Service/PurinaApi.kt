package cargill.com.purina.Service

import cargill.com.purina.dashboard.Model.Home.Animals
import cargill.com.purina.splash.Model.Languages
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PurinaApi {
    @GET("/language")
    suspend fun getLanguages(): Response<Languages>

    @GET("/species/{id}")
    suspend fun getAnimals(@Path("id") languageCode:String): Response<Animals>

}