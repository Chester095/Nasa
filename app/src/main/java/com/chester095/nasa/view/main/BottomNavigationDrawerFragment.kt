package com.chester095.nasa.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chester095.nasa.R
import com.chester095.nasa.databinding.BottomNavigationLayoutBinding
import com.chester095.nasa.view.StylesFragment
import com.chester095.nasa.view.animations.AnimationsFragment
import com.chester095.nasa.view.bottomnavigation.ApiBottomFragment
import com.chester095.nasa.view.coordinator.CoordinatorFragment
import com.chester095.nasa.view.recycler.RecyclerFragment
import com.chester095.nasa.view.ux.UXFragment
import com.chester095.nasa.view.viewpager.ApiFragment
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
                R.id.navigation_ux -> navigateTo(UXFragment())
                R.id.navigation_recycler -> navigateTo(RecyclerFragment())
                R.id.navigation_animation -> navigateTo(AnimationsFragment())
                R.id.navigation_style -> navigateTo(StylesFragment())
                R.id.navigation_coordinator -> navigateTo(CoordinatorFragment())
                R.id.navigation_api -> navigateTo(ApiFragment())
                R.id.navigation_api_bottom -> navigateTo(ApiBottomFragment())
            }
            dismiss()
            true
        }
    }

    private fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.container, fragment).addToBackStack("").commit()
    }

}