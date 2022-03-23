package com.chester095.nasa.view.coordinator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.chester095.nasa.R

class CoordinatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coordinator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonBehavior = ButtonBehavior(requireContext())
        val button = view.findViewById<Button>(R.id.fab)
        (button.layoutParams as CoordinatorLayout.LayoutParams).behavior = buttonBehavior
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoordinatorFragment()
    }
}