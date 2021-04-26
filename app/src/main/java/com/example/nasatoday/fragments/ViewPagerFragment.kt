package com.example.nasatoday.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.nasatoday.R
import com.example.nasatoday.adapters.ViewPagerAdapter
import com.example.nasatoday.databinding.FragmentViewPagerBinding
import com.example.nasatoday.utils.ZoomOutTransformer
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class ViewPagerFragment : Fragment() {

    companion object {
        private const val TAG = "ViewPagerFragment"
    }

    private lateinit var binding: FragmentViewPagerBinding
    private val args: ViewPagerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(layoutInflater, R.layout.fragment_view_pager, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter(getFragments(), childFragmentManager, lifecycle)
            isUserInputEnabled = false
            setPageTransformer(ZoomOutTransformer())
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = args.response[position].date
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()

        Log.d(TAG, "onViewCreated: ${childFragmentManager.fragments.size}")
    }

    private fun getFragments(): ArrayList<PictureFragment> {
        val arrayList = arrayListOf<PictureFragment>()
        for (item in args.response) {
            arrayList.add(PictureFragment.getInstance(item))
        }
        return arrayList.reversed() as ArrayList<PictureFragment>
    }
}