package com.chester095.nasa.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import com.chester095.nasa.R
import com.chester095.nasa.view.main.MainFragment

const val PREFS_NAME = "theme_prefs"
const val KEY_THEME = "prefs.theme"
const val KEY_SP = "sp"
const val KEY_CURRENT_THEME = "current_theme"
const val THEME_UNDEFINED = -1
const val THEME_LIGHT = 0
const val THEME_DARK = 1
const val THEME_SYSTEM = 2

class MainActivity : AppCompatActivity() {

    val sharedPrefs: SharedPreferences by lazy {  getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getCurrentTheme())
        setContentView(R.layout.activity_main)


        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,MainFragment.newInstance()).commit()
        }
    }

    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }


}