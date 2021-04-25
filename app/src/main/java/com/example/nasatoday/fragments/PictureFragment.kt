package com.example.nasatoday.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.nasatoday.R
import com.example.nasatoday.adapters.PicturesViewPagerAdapter
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
        fillImages()
    }

    private fun fillImages() {
        binding.viewPager.adapter =
            PicturesViewPagerAdapter(requireActivity().supportFragmentManager, getFragments())
        fillInfo(0)
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit

            override fun onPageSelected(position: Int) = fillInfo(position)

            override fun onPageScrollStateChanged(state: Int) = Unit
        })
    }

    private fun getFragments(): List<Fragment> {
        val fragments = arrayListOf<Fragment>()
        for (image in args.pictures) {
            val fragment = ViewPagerItemFragment.getInstance(
                imageURL = image.hdurl,
                imageName = image.title,
                imageDate = image.date
            )
            fragments.add(fragment)
        }
        return fragments.reversed()
    }

    private fun setupAnimation() {
        val animation =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    private fun fillInfo(selectedPicturePosition: Int) {
        val selectedPicture = args.pictures.reversed()[selectedPicturePosition]
        binding.pictureBottomSheet.tvPictureDescription.text = selectedPicture.explanation
    }
}