package com.example.nasatoday.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.nasatoday.R
import com.example.nasatoday.databinding.FragmentVideoBinding
import com.example.nasatoday.utils.getYoutubeUrl
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideoFragment: Fragment() {

    private lateinit var binding: FragmentVideoBinding

    private val args: VideoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_video, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPlayer()
        fillData()
    }

    private fun fillData() {
        binding.tvVideoTitle.text = args.video.title
        binding.tvVideoDescription.text = args.video.explanation
    }

    private fun initPlayer() {
        lifecycle.addObserver(binding.youtubePlayer)
        binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(args.video.url.getYoutubeUrl(), 0f)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayer.release()
    }
}


