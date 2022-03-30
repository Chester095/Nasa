package com.chester095.nasa.view.ux

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.chester095.nasa.R
import com.chester095.nasa.databinding.FragmentUxBinding
import com.chester095.nasa.view.MainActivity


class UXFragment : Fragment() {
    private var _binding: FragmentUxBinding? = null
    val binding: FragmentUxBinding
        get() = _binding!!

    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_navigation_view_ux, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.bottomNavigationViewUX.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_ux_text -> navigateTo(TextFragment())
                R.id.fragment_ux_button -> navigateTo(ButtonFragment())
                R.id.fragment_ux_tutorial -> navigateTo(TutorialFragment())
            }
            true
        }
        binding.bottomNavigationViewUX.selectedItemId = R.id.fragment_ux_text
    }

    private fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.slide_out
        ).replace(R.id.container3, fragment).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}