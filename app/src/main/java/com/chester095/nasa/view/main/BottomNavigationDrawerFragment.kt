package com.chester095.nasa.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chester095.nasa.R
import com.chester095.nasa.databinding.BottomNavigationLayoutBinding
import com.chester095.nasa.view.StylesFragment
import com.chester095.nasa.view.animations.AnimationsActivity
import com.chester095.nasa.view.bottomnavigation.ApiBottomActivity
import com.chester095.nasa.view.coordinator.CoordinatorFragment
import com.chester095.nasa.view.viewpager.ApiActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding: BottomNavigationLayoutBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.navigation_animation -> {
                    startActivity(Intent(requireContext(), AnimationsActivity::class.java))
                }
                R.id.navigation_style -> {
                    hideBottomNav()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, StylesFragment.newInstance()).addToBackStack("").commit()
                }
                R.id.navigation_coordinator -> {
                    hideBottomNav()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CoordinatorFragment.newInstance()).addToBackStack("").commit()
                }
                R.id.navigation_one -> {
                    startActivity(Intent(requireContext(), ApiActivity::class.java))
                }
                R.id.navigation_two -> {
                    startActivity(Intent(requireContext(), ApiBottomActivity::class.java))
                }
            }
            true
        }
    }

    private fun hideBottomNav() {
        binding.navigationView.visibility = View.GONE
        binding.navigationView.descendantFocusability

    }
}