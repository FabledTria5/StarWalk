package com.example.nasatoday.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import coil.load
import com.example.nasatoday.R
import com.example.nasatoday.databinding.FragmentViewpagerItemBinding

class ViewPagerItemFragment : Fragment() {

    private lateinit var binding: FragmentViewpagerItemBinding

    companion object {

        private const val IMAGE_URL = "image_URL"
        private const val IMAGE_NAME = "image_name"
        private const val IMAGE_DATE = "image_date"

        fun getInstance(imageURL: String, imageName: String, imageDate: String): ViewPagerItemFragment {
            val fragment = ViewPagerItemFragment()
            Bundle().apply {
                putString(IMAGE_URL, imageURL)
                putString(IMAGE_NAME, imageName)
                putString(IMAGE_DATE, imageDate)
                fragment.arguments = this
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_viewpager_item, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setImageInfo()
    }

    private fun setImageInfo() {
        binding.imageItem.load(arguments?.getString(IMAGE_URL))
        binding.tvPictureTitle.text = arguments?.getString(IMAGE_NAME)
        binding.tvImageDate.text = arguments?.getString(IMAGE_DATE)
    }

}