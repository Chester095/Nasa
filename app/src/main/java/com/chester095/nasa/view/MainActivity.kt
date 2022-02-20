package com.chester095.nasa.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import com.chester095.nasa.R
import com.chester095.nasa.view.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // SP
        setTheme(R.style.MyThemeBlueGray)
        setContentView(R.layout.activity_main)

        TextView(this).apply {
            text="sdfsdfsdg"
            textSize=30f
            gravity = Gravity.NO_GRAVITY

        }

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container,MainFragment.newInstance()).commit()
        }

//        recreate()
    }
}