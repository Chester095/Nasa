package com.chester095.nasa.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.chester095.nasa.R
import com.chester095.nasa.databinding.FragmentSettingsBinding
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*


class SettingsFragment : Fragment() {


    private var _binding: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipsGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.chipsGroup.findViewById<Chip>(checkedId)?.let { it ->
                Toast.makeText(requireContext(), "${it.text} ${checkedId}", Toast.LENGTH_SHORT).show()
            }
        }
        binding.chipEntry.setOnCloseIconClickListener {
            Toast.makeText(requireContext(), "chipEntry close", Toast.LENGTH_SHORT).show()
        }
        binding.tabs.getTabAt(0)!!.text = "Сегодня"
        binding.tabs.getTabAt(0)!!.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_search, null)
        binding.tabs.getTabAt(1)!!.text = "Вчера"
        binding.tabs.getTabAt(1)!!.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_favourite_menu, null)
        binding.tabs.getTabAt(2)!!.text = "Позавчера"
        binding.tabs.getTabAt(2)!!.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_plus_fab, null)
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}