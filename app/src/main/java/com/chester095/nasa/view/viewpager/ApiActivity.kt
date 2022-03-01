package com.chester095.nasa.view.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chester095.nasa.R
import com.chester095.nasa.databinding.ActivityApiBinding
import com.chester095.nasa.view.KEY_CURRENT_THEME
import com.chester095.nasa.view.KEY_SP
import com.google.android.material.tabs.TabLayoutMediator

class ApiActivity : AppCompatActivity() {
    lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getCurrentTheme())
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPager2Adapter(this)
        val tabTitles = arrayOf("Earth", "Mars", "System")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> tab.text = tabTitles[position] }.attach()

        binding.tabLayout.getTabAt(EARTH)?.customView = layoutInflater.inflate(R.layout.activity_api_tablayout_item_earth, null)
        binding.tabLayout.getTabAt(MARS)?.customView = layoutInflater.inflate(R.layout.activity_api_tablayout_item_mars, null)
        binding.tabLayout.getTabAt(SYSTEM)?.customView = layoutInflater.inflate(R.layout.activity_api_tablayout_item_system, null)
    }
    
    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }
}