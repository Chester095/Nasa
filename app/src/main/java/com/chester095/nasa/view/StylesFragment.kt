package com.chester095.nasa.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.chester095.nasa.R
import com.chester095.nasa.databinding.FragmentStylesBinding
import com.google.android.material.tabs.TabLayout


class StylesFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = StylesFragment()
    }
    private var _binding: FragmentStylesBinding? = null

    val binding: FragmentStylesBinding
        get() = _binding!!

    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStylesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*        initTheme()
        initThemeListener()*/
        initBtnStyle()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initBtnStyle() {
        binding.btnOrange.setOnClickListener {
            if (parentActivity.getCurrentTheme() != R.style.MyThemeOrange) {
                parentActivity.setCurrentTheme(R.style.MyThemeOrange)
                parentActivity.recreate()
            }
        }
        binding.btnBlueGray.setOnClickListener {
            if (parentActivity.getCurrentTheme() != R.style.MyThemeBlueGray) {
                parentActivity.setCurrentTheme(R.style.MyThemeBlueGray)
                parentActivity.recreate()
            }
        }
        binding.btnIndigo.setOnClickListener {
            if (parentActivity.getCurrentTheme() != R.style.MyThemeIndigo) {
                parentActivity.setCurrentTheme(R.style.MyThemeIndigo)
                parentActivity.recreate()
            }
        }
    }

/*    private fun initTheme() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB -> {
                binding.themeSystem.visibility = View.VISIBLE
            }
            else -> {
                binding.themeSystem.visibility = View.GONE
            }
        }
        when {
            getSavedTheme() == THEME_LIGHT -> binding.themeLight.isChecked = true
            getSavedTheme() == THEME_DARK -> binding.themeDark.isChecked = true
            getSavedTheme() == THEME_SYSTEM -> binding.themeSystem.isChecked = true
            getSavedTheme() == THEME_UNDEFINED -> {
                when {
                    resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) ==
                            Configuration.UI_MODE_NIGHT_NO -> binding.themeLight.isChecked = true
                    resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) ==
                            Configuration.UI_MODE_NIGHT_YES -> binding.themeDark.isChecked = true
                    resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) ==
                            Configuration.UI_MODE_NIGHT_UNDEFINED -> binding.themeLight.isChecked = true
                }
            }
        }
    }

    private fun initThemeListener() {
        binding.themeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.themeLight -> setTheme(AppCompatDelegate.MODE_NIGHT_NO, THEME_LIGHT)
                R.id.themeDark -> setTheme(AppCompatDelegate.MODE_NIGHT_YES, THEME_DARK)
                R.id.themeSystem -> setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, THEME_SYSTEM)
            }
        }
    }

    private fun setTheme(themeMode: Int, prefsMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        saveTheme(prefsMode)
    }

    private fun saveTheme(theme: Int) = parentActivity.sharedPrefs.edit().putInt(KEY_THEME, theme).apply()
    private fun getSavedTheme() = parentActivity.sharedPrefs.getInt(KEY_THEME, THEME_UNDEFINED)*/
}