package com.example.nasatoday.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.nasatoday.R
import com.example.nasatoday.adapters.ThemesAdapter
import com.example.nasatoday.databinding.FragmentSettingsBinding
import com.example.nasatoday.utils.Constants.Companion.MY_PREFERENCES
import com.example.nasatoday.utils.Constants.Companion.THEME_PREFERENCE
import com.example.nasatoday.utils.Themes
import recycler.coverflow.CoverFlowLayoutManger

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var themeSettings: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeFragment()
        setupRecyclerView()
        setupListeners()
    }

    private fun initializeFragment() {
        themeSettings = requireActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
    }

    private fun setupListeners() {
        binding.btnSaveTheme.setOnClickListener {
            val editor = themeSettings.edit()
            when (binding.rvThemeSwitch.selectedPos) {
                0 -> editor.putInt(THEME_PREFERENCE, Themes.MOON.ordinal)
                1 -> editor.putInt(THEME_PREFERENCE, Themes.SPACE.ordinal)
                2 -> editor.putInt(THEME_PREFERENCE, Themes.MARS.ordinal)
            }
            editor.apply()
            requireActivity().recreate()
        }
    }

    private fun setupRecyclerView() {
        val coverFlowLayoutManger = CoverFlowLayoutManger(
            false,
            false,
            false,
            0.7f
        )
        binding.rvThemeSwitch.apply {
            layoutManager = coverFlowLayoutManger
            setHasFixedSize(true)
            adapter = ThemesAdapter(getThemesResources())
            setOnItemSelectedListener { setThemeName(selectedPos) }
            scrollToPosition(themeSettings.getInt(THEME_PREFERENCE, 1))
        }
    }

    private fun getThemesResources(): ArrayList<Drawable?> {
        return arrayListOf(
            ResourcesCompat.getDrawable(resources, Themes.MOON.theme, null),
            ResourcesCompat.getDrawable(resources, Themes.SPACE.theme, null),
            ResourcesCompat.getDrawable(resources, Themes.MARS.theme, null)
        )
    }

    private fun setThemeName(themePosition: Int) {
        context.apply {
            when (themePosition) {
                Themes.MOON.ordinal -> binding.themeName = getString(R.string.moon_theme)
                Themes.SPACE.ordinal -> binding.themeName = getString(R.string.space_theme)
                Themes.MARS.ordinal -> binding.themeName = getString(R.string.mars_theme)
            }
        }
    }
}
