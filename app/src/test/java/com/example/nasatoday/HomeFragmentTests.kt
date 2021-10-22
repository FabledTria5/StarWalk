package com.example.nasatoday

import android.view.View
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nasatoday.fragments.HomeFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTests {

    lateinit var scenario: FragmentScenario<HomeFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_Space)
    }

    @Test
    fun homeFragment_NotNull() {
        scenario.onFragment { fragment ->
            assertNotNull(fragment)
        }
    }

    @Test
    fun homeFragmentInitialization_ViewNotNull() {
        scenario.onFragment { fragment ->
            assertNotNull(fragment.view)
        }
    }

    @Test
    fun homeFragmentInitialization_LoadingIsVisible() {
        scenario.onFragment { fragment ->
            val loadingVisibility =
                fragment.view?.findViewById<CircularProgressIndicator>(R.id.circularProgressIndicator)?.visibility
            assertEquals(View.VISIBLE, loadingVisibility)
        }
    }

    @Test
    fun homeFragmentInitialization_ImageInvisible() {
        scenario.onFragment { fragment ->
            val imageVisibility =
                fragment.view?.findViewById<MaterialCardView>(R.id.cvImageContainer)?.visibility
            assertEquals(View.INVISIBLE, imageVisibility)
        }
    }

    @Test
    fun homeFragmentInitialization_DateIsNotEmpty() {
        scenario.onFragment { fragment ->
            val dateText =
                fragment.view?.findViewById<TextView>(R.id.tvDate)?.text
            assertNotEquals("", dateText)
        }
    }

}