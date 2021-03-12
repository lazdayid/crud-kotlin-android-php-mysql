package com.lazday.crudkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity : AppCompatActivity() {

    private val api by lazy { ApiRetrofit().endpoint }

    private lateinit var editNote: EditText
    private lateinit var buttonSave: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        setupView()
        setupListener()
    }

    private fun setupView(){
        editNote = findViewById(R.id.edit_note)
        buttonSave = findViewById(R.id.button_save)
    }

    private fun setupListener(){
        buttonSave.setOnClickListener {
            api.create( editNote.text.toString() )
                    .enqueue(object : Callback<SubmitModel> {
                        override fun onResponse(call: Call<SubmitModel>, response: Response<SubmitModel>) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                        applicationContext,
                                        response.body()!!.message,
                                        Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                        }
                        override fun onFailure(call: Call<SubmitModel>, t: Throwable) {

                        }
                    })
        }
    }
}