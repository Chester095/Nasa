package com.chester095.nasa.view.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.chester095.nasa.R
import com.chester095.nasa.databinding.FragmentAnimationsBinding
import com.chester095.nasa.databinding.FragmentAnimationsBonusStartBinding

class AnimationsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AnimationsFragment()
    }

    private var _binding: FragmentAnimationsBonusStartBinding? = null
    private val binding: FragmentAnimationsBonusStartBinding
        get() {
            return _binding!!
        }

    private var flag = true
    private val duration = 1000L
    private val mTension = 10f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationsBonusStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backgroundImage.setOnClickListener {
            flag = !flag
            if (flag) {
                val constraintSet = ConstraintSet()
                constraintSet.clone(requireContext(), R.layout.fragment_animations_bonus_start)
                val changeBounds = ChangeBounds()
                changeBounds.duration = duration
                changeBounds.interpolator = AnticipateOvershootInterpolator(mTension)
                TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)
                constraintSet.applyTo(binding.constraintContainer)
            } else {
                val constraintSet = ConstraintSet()
                constraintSet.clone(requireContext(), R.layout.fragment_animations_bonus_end)
                /*constraintSet.connect(R.id.title,ConstraintSet.END,R.id.constraint_container,ConstraintSet.END)
                constraintSet.connect(R.id.title,ConstraintSet.START,R.id.constraint_container,ConstraintSet.START)
                constraintSet.connect(R.id.title,ConstraintSet.TOP,R.id.constraint_container,ConstraintSet.TOP)*/

                val changeBounds = ChangeBounds()
                changeBounds.duration = duration
                changeBounds.interpolator = AnticipateOvershootInterpolator(mTension)
                TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)
                constraintSet.applyTo(binding.constraintContainer)
            }

        }


    }


}