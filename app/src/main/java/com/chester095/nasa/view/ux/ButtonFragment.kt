package com.chester095.nasa.view.ux

import com.chester095.nasa.databinding.FragmentUxButtonBinding


class ButtonFragment :
    ViewBindingFragment<FragmentUxButtonBinding>(FragmentUxButtonBinding::inflate) {
    companion object {
        @JvmStatic
        fun newInstance() = ButtonFragment()
    }
}