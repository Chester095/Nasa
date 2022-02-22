package com.chester095.nasa.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.chester095.nasa.R
import com.chester095.nasa.databinding.FragmentMainBinding
import com.chester095.nasa.view.MainActivity
import com.chester095.nasa.view.settings.SettingsFragment
import com.chester095.nasa.viewmodel.PictureOfTheDayData
import com.chester095.nasa.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainFragment : Fragment() {

    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.sendRequest()

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED


        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING ->
                        Toast.makeText(requireContext(), "STATE_DRAGGING", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_COLLAPSED ->
                        Toast.makeText(requireContext(), "STATE_COLLAPSED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_EXPANDED ->
                        Toast.makeText(requireContext(), "STATE_EXPANDED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_HALF_EXPANDED ->
                        Toast.makeText(requireContext(), "STATE_HALF_EXPANDED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_HIDDEN ->
                        Toast.makeText(requireContext(), "STATE_HIDDEN", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_SETTLING ->
                        Toast.makeText(requireContext(), "STATE_SETTLING", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("mylogs", "slideOffset $slideOffset")
            }

        })

        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

        val behavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
        binding.fab.setOnClickListener {
            if (isMain) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                binding.fab.setImageResource(R.drawable.ic_back_fab)
            } else {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                binding.fab.setImageResource(R.drawable.ic_plus_fab)
            }
            isMain = !isMain
        }
    }

    var isMain = true

    private fun renderData(pictureOfTheDayData: PictureOfTheDayData) {
        when (pictureOfTheDayData) {
            is PictureOfTheDayData.Error -> {
                Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show()
            }
            is PictureOfTheDayData.Loading -> {
                Toast.makeText(requireContext(), "Грузится...", Toast.LENGTH_SHORT).show()
            }
            is PictureOfTheDayData.Success -> {
                binding.included.bottomSheetDescriptionHeader.text = pictureOfTheDayData.serverResponse.title
                binding.included.bottomSheetDescription.text = pictureOfTheDayData.serverResponse.explanation
                binding.imageView.load(pictureOfTheDayData.serverResponse.url) {
                    placeholder(R.drawable.ic_no_photo_vector)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
                Toast.makeText(requireContext(), "app_bar_fav", Toast.LENGTH_SHORT).show()
            }
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance()).addToBackStack("").commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager, "ff")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
