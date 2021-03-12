package com.lazday.crudkotlin

import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @GET("data.php") fun notes(): Call<NoteModel>

    @FormUrlEncoded
    @POST("create.php")
    fun create( @Field("note") note: String ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("delete.php")
    fun delete( @Field("id") id: String ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("update.php")
    fun update(
            @Field("id") id: String,
            @Field("note") note: String
    ): Call<SubmitModel>
}