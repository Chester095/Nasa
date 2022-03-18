package com.chester095.nasa.view.bottomnavigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chester095.nasa.R
import com.chester095.nasa.databinding.ActivityApiBottomBinding
import com.chester095.nasa.view.KEY_CURRENT_THEME
import com.chester095.nasa.view.KEY_SP
import com.chester095.nasa.view.viewpager.EarthFragment
import com.chester095.nasa.view.viewpager.MarsFragment
import com.chester095.nasa.view.viewpager.SystemFragment
import com.google.android.material.badge.BadgeDrawable

class ApiBottomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiBottomBinding
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getCurrentTheme())

        binding = ActivityApiBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.bottom_view_earth -> {
                    navigateTo(EarthFragment())
                    true
                }
                R.id.bottom_view_mars -> {
                    navigateTo(MarsFragment())
                    true
                }
                R.id.bottom_view_system -> {
                    navigateTo(SystemFragment())
                    true
                }
                else -> true
            }
        }

        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_system

        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_mars)
        badge.number = 100
        badge.maxCharacterCount = 3
        badge.badgeGravity = BadgeDrawable.TOP_END
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    private fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }
}