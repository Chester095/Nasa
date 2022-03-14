package com.chester095.nasa.view.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import coil.load
import com.chester095.nasa.BuildConfig
import com.chester095.nasa.R
import com.chester095.nasa.databinding.FragmentMainBinding
import com.chester095.nasa.view.MainActivity
import com.chester095.nasa.view.settings.SettingsFragment
import com.chester095.nasa.viewmodel.AppState
import com.chester095.nasa.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar

class PictureOfTheDayFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    private lateinit var pictureOfTheDayViewModel: PictureOfTheDayViewModel

    companion object {
        fun newInstance(): PictureOfTheDayFragment {
            return PictureOfTheDayFragment()
        }

        private var flag = false
        private const val TODAY = 0
        private const val YESTERDAY = 1
        private const val BEFORE_YESTERDAY = 2
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pictureOfTheDayViewModel = (context as MainActivity).pictureOfTheDayViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                if (binding.inputLayout.isEndIconCheckable) {
                    data =
                        Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
                }
                else {}
            })
        }

        binding.chipsGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipToday -> {
                    binding.chipsGroup.check(R.id.chipToday)
                    viewModel.getPODFromServer((TODAY))
                }
                R.id.chipYesterday -> {
                    binding.chipsGroup.check(R.id.chipYesterday)
                    viewModel.getPODFromServer((YESTERDAY))
                }
                R.id.chipDayBeforeYesterday -> {
                    binding.chipsGroup.check(R.id.chipDayBeforeYesterday)
                    viewModel.getPODFromServer((BEFORE_YESTERDAY))
                }
                R.id.chipEarthToday -> {
                    binding.chipsGroup.check(R.id.chipEarthToday)
                    viewModel.getEpic()
                }
                R.id.chipMarsToday -> {
                    binding.chipsGroup.check(R.id.chipMarsToday)
                    viewModel.getMarsPicture()
                }
                else -> viewModel.getPODFromServer((TODAY))
            }

            bottomSheetBehavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED


            bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
/*                    when (newState) {
*//*                        BottomSheetBehavior.STATE_DRAGGING ->
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
                            Toast.makeText(requireContext(), "STATE_SETTLING", Toast.LENGTH_SHORT).show()*//*
                    }*/
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    Log.d("mylogs", "slideOffset $slideOffset")
                }
            })


        }

        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

        val behavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
        var isMain = true
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
//                    Toast.makeText(requireContext(), "app_bar_fav", Toast.LENGTH_SHORT).show()
            }
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack("").commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager, "ff")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    private fun showAVideoUrl(videoUrl: String) = with(binding) {
        imageView.visibility = View.GONE
        videoOfTheDay.visibility = View.VISIBLE
        videoOfTheDay.text = "Сегодня у нас без картинки дня, но есть  видео дня! " +
                "$videoUrl \n кликни >ЗДЕСЬ< чтобы открыть в новом окне"
        videoOfTheDay.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(videoUrl)
            }
            startActivity(i)
        }
    }

    private fun renderData(appState: AppState) {

        when (appState) {
            is AppState.Error ->
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            is AppState.Loading -> {
                Toast.makeText(requireContext(), "Грузится...", Toast.LENGTH_SHORT).show()
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                binding.imageView.load(R.drawable.progress_animation)
            }
            is AppState.SuccessPOD -> {
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                setData(appState)
                zoomImage()
            }
            is AppState.SuccessEarthEpic -> {
                val strDate = appState.serverResponseData.last().date.split(" ").first()
                val image = appState.serverResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/${strDate.replace("-", "/", true)}/png/" +
                        "$image.png?api_key=${BuildConfig.NASA_API_KEY}"
                BottomSheetBehavior.from(binding.included.bottomSheetContainer).state = BottomSheetBehavior.STATE_HIDDEN
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                binding.imageView.load(url)
                zoomImage()
            }
            is AppState.SuccessMars -> {
                if (appState.serverResponseData.photos.isEmpty()) {
                    Snackbar.make(binding.root, "В этот день curiosity не сделал ни одного снимка", Snackbar.LENGTH_SHORT).show()
                } else {
                    val url = appState.serverResponseData.photos.first().imgSrc
                    BottomSheetBehavior.from(binding.included.bottomSheetContainer).state = BottomSheetBehavior.STATE_HIDDEN
                    binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    binding.imageView.load(url)
                    zoomImage()
                }
            }
            else -> {}
        }
    }

    private fun zoomImage(){
        binding.imageView.setOnClickListener {
            flag = !flag
            val changeBounds = ChangeBounds()
            val changeImageTransform = ChangeImageTransform()
            changeBounds.duration = 3000
            changeImageTransform.duration = 3000
            TransitionManager.beginDelayedTransition(binding.main, changeImageTransform)
            if (flag){
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
        }
    }

    private fun setData(data: AppState.SuccessPOD) {
        val url = data.serverResponseData.hdurl
        if (url.isNullOrEmpty()) {
            val videoUrl = data.serverResponseData.url
            videoUrl?.let { showAVideoUrl(it) }
        } else {
            BottomSheetBehavior.from(binding.included.bottomSheetContainer).state = BottomSheetBehavior.STATE_COLLAPSED
            binding.imageView.load(url)
            binding.included.bottomSheetDescriptionHeader.text = data.serverResponseData.title
            binding.included.bottomSheetDescription.text = data.serverResponseData.explanation
        }
    }

}