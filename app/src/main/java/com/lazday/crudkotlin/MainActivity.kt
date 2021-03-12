package com.lazday.crudkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private val api by lazy { ApiRetrofit().endpoint }

    private lateinit var listNote: RecyclerView
    private lateinit var fabCreate: FloatingActionButton
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupList()
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        getNote()
    }

    private fun setupView(){
        listNote = findViewById(R.id.list_note)
        fabCreate = findViewById(R.id.fab_create)
    }

    private fun setupList(){
        noteAdapter = NoteAdapter(arrayListOf(), object : NoteAdapter.OnAdapterListener {
            override fun onUpdate(data: NoteModel.Data) {
                startActivity(
                        Intent(this@MainActivity, UpdateActivity::class.java)
                                .putExtra("intent_data", data)
                )
            }
            override fun onDelete(data: NoteModel.Data) {
                deleteNote(data.id!!)
            }
        })
        listNote.adapter = noteAdapter
    }

    private fun setupListener(){
        fabCreate.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }
    }

    private fun getNote(){
        api.notes().enqueue(object : Callback<NoteModel> {
            override fun onResponse(call: Call<NoteModel>, response: Response<NoteModel>) {
                if (response.isSuccessful) {
                    val notes: List<NoteModel.Data> = response.body()!!.notes;
                    Log.e(TAG, notes.toString())
                    noteAdapter.setData( notes )
                }
            }

            override fun onFailure(call: Call<NoteModel>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }

    private fun deleteNote(id: String){
        api.delete( id )
                .enqueue(object : Callback<SubmitModel> {
                    override fun onResponse(call: Call<SubmitModel>, response: Response<SubmitModel>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                    applicationContext,
                                    response.body()!!.message,
                                    Toast.LENGTH_SHORT
                            ).show()
                            getNote()
                        }
                    }

                    override fun onFailure(call: Call<SubmitModel>, t: Throwable) {

                    }

                })
    }
}