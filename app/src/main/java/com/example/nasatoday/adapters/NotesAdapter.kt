package com.example.nasatoday.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nasatoday.R
import com.example.nasatoday.databinding.NotesItemBinding
import com.example.nasatoday.interfaces.ItemTouchHelperAdapter
import com.example.nasatoday.interfaces.ItemTouchHelperViewHolder
import com.example.nasatoday.utils.Note
import com.example.nasatoday.utils.setZoom

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(), ItemTouchHelperAdapter {

    private val notesList = arrayListOf<Note>()

    inner class NotesViewHolder(val binding: NotesItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

        override fun onItemSelected() {
            binding.root.setZoom(true)
        }

        override fun onItemClear() {
            binding.root.setZoom(false)
        }

        fun bind(position: Int) {
            binding.materialCheckBox.isChecked = notesList[position].done
            binding.etNoteText.requestFocus()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<NotesItemBinding>(inflater, R.layout.notes_item, parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = notesList.count()

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        notesList.removeAt(fromPosition.apply {
            notesList.add(
                if (toPosition > fromPosition) toPosition - 1 else toPosition,
                notesList[fromPosition]
            )
        })
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        notesList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addNote() = notesList.add(Note())

}