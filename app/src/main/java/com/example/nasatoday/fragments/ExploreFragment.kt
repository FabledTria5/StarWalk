package com.example.nasatoday.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasatoday.R
import com.example.nasatoday.adapters.NotesAdapter
import com.example.nasatoday.databinding.FragmentExploreBinding
import com.example.nasatoday.utils.Constants.WIKI_URL
import com.example.nasatoday.utils.ItemTouchHelperCallback

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(layoutInflater, R.layout.fragment_explore, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupListeners()
        binding.isListEmpty = true
    }

    private fun setupRecyclerView() {
        notesAdapter = NotesAdapter()
        binding.rvNotesList.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(context)
            addOnChildAttachStateChangeListener(object :
                RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewAttachedToWindow(view: View) = Unit

                override fun onChildViewDetachedFromWindow(view: View) {
                    if (notesAdapter.itemCount == 0) binding.isListEmpty = true
                }
            })
        }
        ItemTouchHelper(ItemTouchHelperCallback(notesAdapter))
            .attachToRecyclerView(binding.rvNotesList)
    }

    private fun setupListeners() {
        binding.tilWikiSearch.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_URL${binding.editText.text.toString()}")
            })
        }

        binding.btnAddNote.setOnClickListener {
            binding.isListEmpty = false
            notesAdapter.apply {
                addNote()
                notifyDataSetChanged()
            }
        }
    }
}