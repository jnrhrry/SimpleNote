package com.januar.simplenote.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.januar.simplenote.CustomOnItemClickListener
import com.januar.simplenote.NoteAddUpdateActivity
import com.januar.simplenote.R
import com.januar.simplenote.entity.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(private val activity: Activity) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    var listNotes = ArrayList<Note>()
    set(listNotes){
        if (listNotes.size > 0){
            this.listNotes.clear()
        }
        this.listNotes.addAll(listNotes)
        notifyDataSetChanged()
    }
    fun addItem(note: Note){
        this.listNotes.add(note)
        notifyItemInserted(this.listNotes.size - 1)
    }
    fun updateItem(position: Int, note: Note){
        this.listNotes[position] = note
        notifyItemChanged(position, note)
    }
    fun removeItem(position: Int){
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }
    override fun getItemCount(): Int = this.listNotes.size

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
         holder.bind(listNotes[position])
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(note: Note){
            with(itemView){
                tvItemTitle.text = note.title
                tvItemDate.text = note.date
                tvItemDescription.text = note.description
                cvItemNote.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, NoteAddUpdateActivity::class.java)
                        intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
                        intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
                        activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE)
                    }

                } ))
            }
        }
    }

}