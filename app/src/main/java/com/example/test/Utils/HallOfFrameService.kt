package com.example.test.Utils

import com.example.test.Models.HallOfFrame
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface HallOfFrameService {

    @GET("?action=allhof")
    fun getAllHof(): Call<List<HallOfFrame>>

    @FormUrlEncoded
    @POST("?action=add")
    fun addHof(
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("maxpoints") maxPoints: Int,
        @Field("countnumber") countNumber: Int
    ): Call<HallOfFrame>

    companion object {
        var gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://brodalee.alwaysdata.net/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

}