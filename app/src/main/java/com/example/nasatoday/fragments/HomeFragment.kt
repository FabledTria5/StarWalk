package com.example.nasatoday.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.nasatoday.R
import com.example.nasatoday.databinding.FragmentHomeBinding
import com.example.nasatoday.model.PictureOfTheDayData
import com.example.nasatoday.repository.NasaRepository
import com.example.nasatoday.utils.toast
import com.example.nasatoday.viewmodels.HomeViewModel
import com.example.nasatoday.viewmodels.factories.HomeViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

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
        observeImageOfTheDay()
        observeTime()
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
                    binding.imageURL = pictureData.pictureOfTheDayModel.url
                    binding.ivPictureOfTheDay.setOnClickListener {
                        val extras =
                            FragmentNavigatorExtras(binding.ivPictureOfTheDay to "pictureBig")
                        HomeFragmentDirections.openPicture(picture = pictureData.pictureOfTheDayModel)
                            .also {
                                findNavController().navigate(it, extras)
                            }
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        val repository = NasaRepository()
        val viewModelFactory = HomeViewModelFactory(repository = repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun setEnterAnimation() {
        val sharedText = view?.findViewById<TextView>(R.id.textView)
        val sharedImage = view?.findViewById<ImageView>(R.id.imageView)

        sharedImage?.viewTreeObserver?.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                sharedImage.viewTreeObserver.removeOnPreDrawListener(this)
                requireActivity().startPostponedEnterTransition()
                return true
            }
        })

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