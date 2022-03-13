package com.chester095.nasa.view.animations

import android.os.Bundle
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.chester095.nasa.R
import com.chester095.nasa.databinding.ActivityAnimationsBonusStartBinding

class AnimationsActivity : AppCompatActivity() {

    private var flag = true
    private val duration = 1000L
    private val mTension = 10f
    lateinit var binding: ActivityAnimationsBonusStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsBonusStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backgroundImage.setOnClickListener {
            flag = !flag
            if (flag) {
                val constraintSet = ConstraintSet()
                constraintSet.clone(this, R.layout.activity_animations_bonus_start)
                val changeBounds = ChangeBounds()
                changeBounds.duration = duration
                changeBounds.interpolator = AnticipateOvershootInterpolator(mTension)
                TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)
                constraintSet.applyTo(binding.constraintContainer)
            } else {
                val constraintSet = ConstraintSet()
                constraintSet.clone(this, R.layout.activity_animations_bonus_end)
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