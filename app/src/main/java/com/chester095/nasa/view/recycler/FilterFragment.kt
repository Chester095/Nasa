package com.chester095.nasa.view.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.chester095.nasa.R
import com.chester095.nasa.databinding.FragmentRecyclerFiltersBinding

class FilterFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = FilterFragment()
    }

    val editTextTitle: EditText = view.findViewById(R.id.filter_title)
    val bundle = Bundle()
    private var _binding: FragmentRecyclerFiltersBinding? = null
    private val binding: FragmentRecyclerFiltersBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFilterOk.setOnClickListener {
            val title = editTextTitle.text
            val viewFragment = ViewFragment()
            val action = FilterFragmentDirections.actionFragmentOneToFragmentTwo(hello)
            findNavController().navigate(action)

        }
    }

    fun testFragmentResult() {
        val scenario = launchFragmentInContainer<ResultFragment>()
        lateinit var actualResult: String?
        scenario.onFragment { fragment ->
            fragment.parentFragmentManagager.setResultListener("requestKey") { key, bundle ->
                actualResult = bundle.getString("bundleKey")
            }
        }
        onView(withId(R.id.result_button)).perform(click())
        assertThat(actualResult).isEqualTo("result")
    }

    class ResultFragment : Fragment(R.layout.fragment_recycler) {
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            view.findViewById(R.id.result_button).setOnClickListener {
                val result = "result"
                setResult("requestKey", bundleOf("bundleKey" to result))
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}