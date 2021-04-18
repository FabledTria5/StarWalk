package com.example.nasatoday.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.nasatoday.R
import com.example.nasatoday.databinding.FragmentPictureBinding

class PictureFragment : Fragment() {

    private lateinit var binding: FragmentPictureBinding
    private val args: PictureFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_picture, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAnimation()
        fillInfo()
    }

    private fun setupAnimation() {
        val animation =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    private fun fillInfo() {
        args.picture.apply {
            binding.pictureURL = url
            binding.pictureBottomSheet.tvPictureDescription.text = explanation
            binding.pictureBottomSheet.tvPictureTitle.text = title
        }
    }
}