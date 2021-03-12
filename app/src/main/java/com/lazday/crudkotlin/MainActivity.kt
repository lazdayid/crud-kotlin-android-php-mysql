package com.lazday.crudkotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var listNote: RecyclerView
    lateinit var fabCreate: FloatingActionButton
    lateinit var noteAdapter: NoteAdapter

    val api by lazy { ApiRetrofit().endpoint }
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

            }
            override fun onDelete(data: NoteModel.Data) {

            }
        })
        listNote.adapter = noteAdapter
    }

    private fun setupListener(){

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
}