package com.chester095.nasa.view.viewpager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.chester095.nasa.R
import com.chester095.nasa.databinding.FragmentApiBinding
import com.google.android.material.tabs.TabLayoutMediator

class ApiFragment : Fragment() {


    private var _binding: FragmentApiBinding? = null
    private val binding: FragmentApiBinding
    get() {
        return _binding!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = ViewPager2Adapter(requireContext() as FragmentActivity)
        val tabTitles = arrayOf("Earth", "Mars", "System")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> tab.text = tabTitles[position] }.attach()

        binding.tabLayout.getTabAt(EARTH)?.customView = layoutInflater.inflate(R.layout.activity_api_tablayout_item_earth, null)
        binding.tabLayout.getTabAt(MARS)?.customView = layoutInflater.inflate(R.layout.activity_api_tablayout_item_mars, null)
        binding.tabLayout.getTabAt(SYSTEM)?.customView = layoutInflater.inflate(R.layout.activity_api_tablayout_item_system, null)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}