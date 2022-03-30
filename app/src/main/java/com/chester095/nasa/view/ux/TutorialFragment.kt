package com.chester095.nasa.view.ux

import android.os.Bundle
import android.view.*
import com.chester095.nasa.databinding.FragmentUxButtonBinding
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity


class TutorialFragment :
    ViewBindingFragment<FragmentUxButtonBinding>(FragmentUxButtonBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBad()
    }

    private fun showBad() {
        val builder =  GuideView.Builder(requireContext())
            .setTitle("Это неправильно")
            .setContentText("Мы не работаем с цветами и размерами")
            .setGravity(Gravity.center)
            .setDismissType(DismissType.anywhere)
            .setTargetView(binding.btnTestBad)
            .setGuideListener {
                // тут запустим сразу вторую подсказку
                showGood()
            }
        builder.build().show()
    }

    private fun showGood() {
        val builder = GuideView.Builder(requireContext())
            .setTitle("Это правильно")
            .setContentText("Мы работаем с прозначностью")
            .setGravity(Gravity.center)
            .setDismissType(DismissType.anywhere)
            .setTargetView(binding.layoutGood)
        builder.build().show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TutorialFragment()
    }
}