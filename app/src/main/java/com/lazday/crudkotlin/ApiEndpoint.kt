package com.lazday.crudkotlin

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndpoint {

    @GET("data.php") fun notes(): Call<NoteModel>

//    @FormUrlEncoded
//    @POST("login/login")
//    fun login(
//            @Field("username") username: String,
//            @Field("password") password: String
//    ): Call<LoginResponse>
}