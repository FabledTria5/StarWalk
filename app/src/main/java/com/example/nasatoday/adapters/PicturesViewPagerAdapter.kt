package com.example.nasatoday.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

@Suppress("DEPRECATION")
class PicturesViewPagerAdapter(
    fragmentManager: FragmentManager,
    val fragments: List<Fragment>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int = fragments.count()

    override fun getItem(position: Int) = fragments[position]

}