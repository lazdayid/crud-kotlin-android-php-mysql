package com.lazday.crudkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    val api by lazy { ApiRetrofit().endpoint }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        api.notes().enqueue(object : Callback<NoteModel> {
            override fun onResponse(call: Call<NoteModel>, response: Response<NoteModel>) {
                if (response.isSuccessful) {
                    val notes: List<NoteModel.Data> = response.body()!!.notes;
                    Log.e(TAG, notes.toString() )
                }
            }
            override fun onFailure(call: Call<NoteModel>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }
}