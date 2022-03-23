package com.chester095.nasa.view.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chester095.nasa.databinding.FragmentRecyclerFiltersBinding


class FilterFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = FilterFragment()
    }

    val bundle = Bundle()
    private var _binding: FragmentRecyclerFiltersBinding? = null
    private val binding: FragmentRecyclerFiltersBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRadioButtons()

        binding.rbSomeTextEarth.setOnClickListener {
            RecyclerFragment.filterSomeTextEarth = true
            RecyclerFragment.filterSomeTextMars = false
            binding.rbSomeTextMars.isChecked = false
        }
        binding.rbSomeTextMars.setOnClickListener {
            RecyclerFragment.filterSomeTextMars = true
            RecyclerFragment.filterSomeTextEarth = false
            binding.rbSomeTextEarth.isChecked = false
        }
        binding.rbWeightMoreThousand.setOnClickListener {
            RecyclerFragment.filterWeightMoreThousand = true
            RecyclerFragment.filterWeightLessThousand = false
            binding.rbWeightLessThousand.isChecked = false
        }
        binding.rbWeightLessThousand.setOnClickListener {
            RecyclerFragment.filterWeightLessThousand = true
            RecyclerFragment.filterWeightMoreThousand = false
            binding.rbWeightMoreThousand.isChecked = false
        }
    }

    private fun setRadioButtons(){
        if (RecyclerFragment.filterSomeTextEarth)  binding.rbSomeTextEarth.isChecked = true
        else if (RecyclerFragment.filterSomeTextMars)  binding.rbSomeTextMars.isChecked = true
        if (RecyclerFragment.filterWeightMoreThousand)  binding.rbWeightMoreThousand.isChecked = true
        else if (RecyclerFragment.filterWeightLessThousand)  binding.rbWeightLessThousand.isChecked = true

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}