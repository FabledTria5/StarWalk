package com.example.nasatoday.fragments

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
import com.example.nasatoday.utils.TemporaryData
import com.example.nasatoday.utils.Themes
import recycler.coverflow.CoverFlowLayoutManger

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

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
        setupRecyclerView()
        setupListeners()
        binding.themeName = getString(R.string.space_theme)
    }

    private fun setupListeners() {
        binding.btnSaveTheme.setOnClickListener {
            when (binding.rvThemeSwitch.selectedPos) {
                0 -> TemporaryData.theme = Themes.MOON.themeResource
                1 -> TemporaryData.theme = Themes.SPACE.themeResource
                2 -> TemporaryData.theme = Themes.MARS.themeResource
            }
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
            adapter = ThemesAdapter(
                arrayListOf(
                    ResourcesCompat.getDrawable(resources, Themes.MOON.theme, null),
                    ResourcesCompat.getDrawable(resources, Themes.SPACE.theme, null),
                    ResourcesCompat.getDrawable(resources, Themes.MARS.theme, null)
                )
            )
            setOnItemSelectedListener {
                when (it) {
                    Themes.MOON.ordinal -> binding.themeName = context.getString(R.string.moon_theme)
                    Themes.SPACE.ordinal -> binding.themeName = context.getString(R.string.space_theme)
                    Themes.MARS.ordinal -> binding.themeName = context.getString(R.string.mars_theme)
                }
            }
            smoothScrollToPosition(1)
        }
    }
}
