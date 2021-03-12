package com.lazday.crudkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class NoteAdapter (
    var notes: ArrayList<NoteModel.Data>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_note,
                parent, false
            )
        )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.textNote.text = note.note
        holder.imageDelete.setOnClickListener {
            listener.onDelete(note)
        }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val textNote = view.findViewById<TextView>(R.id.text_note)
        val imageDelete = view.findViewById<ImageView>(R.id.image_delete)
        val container = view.findViewById<MaterialCardView>(R.id.container)
    }

    fun setData(data: List<NoteModel.Data>) {
        notes.clear()
        notes.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onUpdate(data: NoteModel.Data)
        fun onDelete(data: NoteModel.Data)
    }

}