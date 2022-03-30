package com.chester095.nasa.view.ux

import com.chester095.nasa.databinding.FragmentUxTextBinding


class TextFragment :
    ViewBindingFragment<FragmentUxTextBinding>(FragmentUxTextBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = TextFragment()
    }
}