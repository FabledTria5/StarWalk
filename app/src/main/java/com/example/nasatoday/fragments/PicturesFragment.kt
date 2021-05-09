package com.example.nasatoday.fragments

import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BulletSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import coil.load
import com.example.nasatoday.R
import com.example.nasatoday.databinding.FragmentPicturesBinding
import com.example.nasatoday.model.PictureOfTheDayModel
import com.example.nasatoday.utils.getYoutubeUrl
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class PicturesFragment : Fragment() {

    companion object {

        const val RESPONSE = "response"

        fun getInstance(pictureOfTheDayModel: PictureOfTheDayModel): PicturesFragment {
            val bundle = Bundle()
            val fragment = PicturesFragment()
            bundle.putSerializable(RESPONSE, pictureOfTheDayModel)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentPicturesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_pictures, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val model = arguments?.getSerializable(RESPONSE) as PictureOfTheDayModel
        if (receivedPicture(model)) setPicture(model)
        else setVideo(model)
        decorateText()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayer.release()
    }

    private fun setVideo(model: PictureOfTheDayModel) {
        binding.dataIsPicture = false
        lifecycle.addObserver(binding.youtubePlayer)
        binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(model.url.getYoutubeUrl(), 0f)
                youTubePlayer.pause()
            }
        })
        setContentText(model)
    }

    private fun setPicture(model: PictureOfTheDayModel) {
        binding.dataIsPicture = true
        binding.ivPictureOfTheDay.load(model.hdurl) {
            placeholder(R.drawable.ic_placeholder)
        }
        setContentText(model)
    }

    private fun setContentText(model: PictureOfTheDayModel) {
        binding.contentTitle.text = model.title
        binding.contentDescription.text = model.explanation
    }

    private fun decorateText() {
        SpannableString(binding.contentTitle.text).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                setSpan(
                    BulletSpan(
                        30,
                        ContextCompat.getColor(requireContext(), R.color.white),
                        15
                    ),
                    0,
                    1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(UnderlineSpan(), 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.contentTitle.text = this
        }
    }

    private fun receivedPicture(model: PictureOfTheDayModel): Boolean {
        return model.thumbnail_url == null
    }
}