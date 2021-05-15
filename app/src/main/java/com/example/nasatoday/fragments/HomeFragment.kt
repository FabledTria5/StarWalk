package com.example.nasatoday.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.transition.*
import coil.load
import com.example.nasatoday.R
import com.example.nasatoday.databinding.FragmentHomeBinding
import com.example.nasatoday.model.PictureOfTheDayData
import com.example.nasatoday.model.PictureOfTheDayModel
import com.example.nasatoday.model.PictureOfTheDayResponse
import com.example.nasatoday.repository.NasaRepository
import com.example.nasatoday.utils.focusOn
import com.example.nasatoday.utils.show
import com.example.nasatoday.utils.toast
import com.example.nasatoday.viewmodels.HomeViewModel
import com.example.nasatoday.viewmodels.factories.HomeViewModelFactory

class HomeFragment : Fragment() {

    companion object {
        @Suppress("unused")
        private const val TAG = "HomeFragment"
    }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    private var imageInitHeight = 0
    private var isExpanded = false

    private val transitionListener = object : Transition.TransitionListener {
        override fun onTransitionStart(transition: Transition) = Unit
        override fun onTransitionEnd(transition: Transition) {
            binding.cvImageContainer.focusOn(binding.nestedScrollView)
        }

        override fun onTransitionCancel(transition: Transition) = Unit
        override fun onTransitionPause(transition: Transition) = Unit
        override fun onTransitionResume(transition: Transition) = Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setEnterAnimation()
        initViewModel()
        observeTime()
        observeImageOfTheDay()
        setupListeners()
    }

    private fun observeTime() {
        viewModel.getTime()
        viewModel.currentTime.observe(viewLifecycleOwner) { binding.currentDate = it }
    }

    private fun observeImageOfTheDay() {
        viewModel.getImage()
        viewModel.imageResponse.observe(viewLifecycleOwner) { pictureData ->
            when (pictureData) {
                is PictureOfTheDayData.Error -> toast(pictureData.error.toString())
                is PictureOfTheDayData.Success -> {
                    val response = pictureData.pictureOfTheDayResponse
                    setPicture(response.last())
                    binding.ivPictureOfTheDay.setOnClickListener {
                        openImage(response)
                    }
                }
            }
        }
    }

    private fun openImage(pictureResponse: PictureOfTheDayResponse) {
        if (pictureResponse.last().thumbnail_url == null) {
            HomeFragmentDirections.openPictures(response = pictureResponse).also {
                findNavController().navigate(it)
            }
        } else {
            HomeFragmentDirections.openPictures(response = pictureResponse).also {
                findNavController().navigate(it)
            }
        }
    }

    private fun setPicture(pictureModel: PictureOfTheDayModel) {
        if (pictureModel.thumbnail_url == null) {
            loadPicture(pictureModel.url)
            binding.btnZoomIn.show()
        } else loadPicture(pictureModel.thumbnail_url)
        binding.pictureReceived = pictureModel.thumbnail_url == null
    }

    private fun loadPicture(url: String) = binding.ivPictureOfTheDay.load(url) {
        listener(
            onSuccess = { _, _ ->
                binding.imageLoaded = true
            }
        )
    }

    private fun initViewModel() {
        val repository = NasaRepository()
        val viewModelFactory = HomeViewModelFactory(repository = repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun setupListeners() {
        imageInitHeight = binding.cvImageContainer.layoutParams.height
        binding.btnZoomIn.setOnClickListener {
            isExpanded = !isExpanded
            binding.isExpanded = isExpanded
            TransitionManager.beginDelayedTransition(
                binding.container, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
                    .addListener(transitionListener)
            )
            binding.cvImageContainer.layoutParams.apply {
                height = if (isExpanded) binding.ivPictureOfTheDay.height else imageInitHeight
                binding.cvImageContainer.layoutParams = this
            }
        }
    }

    private fun setEnterAnimation() {
        val sharedText = view?.findViewById<TextView>(R.id.textView)

        sharedText?.viewTreeObserver?.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                sharedText.viewTreeObserver.removeOnPreDrawListener(this)
                requireActivity().startPostponedEnterTransition()
                return true
            }
        })
    }
}
