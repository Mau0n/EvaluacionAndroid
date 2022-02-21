package com.evaluacionandroid.request

import com.evaluacionandroid.model.NationalityResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    companion object {

        private const val BASE_URL = "https://api.nationalize.io/"

        fun create() : APIService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(APIService::class.java)

        }
    }

    @GET(" ")
    fun getNationalitiesProbability(@Query("name") name: String): Call<NationalityResult>

}