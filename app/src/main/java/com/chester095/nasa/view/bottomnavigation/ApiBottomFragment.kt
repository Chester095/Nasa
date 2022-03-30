package com.chester095.nasa.view.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chester095.nasa.R
import com.chester095.nasa.databinding.FragmentApiBottomBinding
import com.chester095.nasa.view.viewpager.EarthFragment
import com.chester095.nasa.view.viewpager.MarsFragment
import com.chester095.nasa.view.viewpager.SystemFragment
import com.google.android.material.badge.BadgeDrawable

class ApiBottomFragment : Fragment() {


    private var _binding: FragmentApiBottomBinding? = null
    private val binding: FragmentApiBottomBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.bottom_view_earth -> navigateTo(EarthFragment())
                R.id.bottom_view_mars -> navigateTo(MarsFragment())
                R.id.bottom_view_system -> navigateTo(SystemFragment())
            }
            true
        }

        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_system

        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_mars)
        badge.number = 100
        badge.maxCharacterCount = 3
        badge.badgeGravity = BadgeDrawable.TOP_END
    }

    private fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container2, fragment).commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}