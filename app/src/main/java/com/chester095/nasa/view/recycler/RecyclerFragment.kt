package com.chester095.nasa.view.recycler

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.chester095.nasa.R
import com.chester095.nasa.databinding.FragmentRecyclerBinding
import com.chester095.nasa.view.MainActivity
import com.chester095.nasa.view.main.BottomNavigationDrawerFragment
import com.chester095.nasa.view.main.PictureOfTheDayFragment

class RecyclerFragment : Fragment() {

    var data = arrayListOf<Pair<Int, Data>>()

    private lateinit var adapter: RecyclerFragmentAdapter
    private var _binding: FragmentRecyclerBinding? = null
    private val binding: FragmentRecyclerBinding
        get() {
            return _binding!!
        }

    companion object {
        var filterSomeTextEarth = false
        var filterSomeTextMars = false
        var filterWeightMoreThousand = false
        var filterWeightLessThousand = false
    }

    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setSupportActionBar(binding.recyclerBottomAppBar)
        setHasOptionsMenu(true)
        if (data.size == 0) {
            data.add(0, Pair(ITEM_CLOSE, Data(id = 0, someText = "Заголовок", type = TYPE_HEADER, weight = 1000000000)))
            data.add(1, Pair(ITEM_CLOSE, Data(id = 1, someText = "Earth", type = TYPE_EARTH)))
            data.add(2, Pair(ITEM_CLOSE, Data(id = 2, someText = "Earth", type = TYPE_EARTH)))
            data.add(3, Pair(ITEM_CLOSE, Data(id = 3, someText = "Mars 1", type = TYPE_MARS, weight = 1000)))
            data.add(4, Pair(ITEM_CLOSE, Data(id = 4, someText = "Earth", type = TYPE_EARTH)))
            data.add(5, Pair(ITEM_CLOSE, Data(id = 5, someText = "Earth", type = TYPE_EARTH)))
            data.add(6, Pair(ITEM_CLOSE, Data(id = 6, someText = "Earth", type = TYPE_EARTH)))
            data.add(7, Pair(ITEM_CLOSE, Data(id = 7, someText = "Mars 2", type = TYPE_MARS, weight = 2000)))
        }

        val lat = 23
        val lon = 21
        val pair1 = Pair(lat, lon)
        val pair2 = lat to lon
        val pair3 = Triple(lon, lon, lon)
        pair1.first
        pair1.second

        pair2.first
        pair2.second

        pair3.first
        pair3.second
        pair3.third


        adapter = RecyclerFragmentAdapter({
            Toast.makeText(requireContext(), it.someText, Toast.LENGTH_SHORT).show()
        }, data, {
            itemTouchHelper.startDrag(it)
        })

        binding.recyclerView.adapter = adapter
        filterCheck()
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerFAB.setOnClickListener {
            adapter.addItem()
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }
    }

    private fun filterCheck() {
        if (filterSomeTextEarth) {
            adapter.filterSomeTextEarth()
        } else if (filterSomeTextMars) {
            adapter.filterSomeTextMars()
        }
        if (filterWeightMoreThousand) {
            adapter.filterWeightMoreThousand()
        } else if (filterWeightLessThousand) {
            adapter.filterWeightLessThousand()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_find_recycler -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    .replace(R.id.container, FilterFragment.newInstance())
                    .addToBackStack("").commit()
            }
            R.id.app_bar_sort_recycler -> {
                adapter.sortItemsByWeight()
            }
            android.R.id.home -> {
                Log.d("!!! R.id.home", "")
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager, "ff")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class ItemTouchHelperCallback(private val adapter: RecyclerFragmentAdapter) : ItemTouchHelper.Callback() {

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipe = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(drag, swipe)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.onItemDismiss(viewHolder.adapterPosition)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (viewHolder !is RecyclerFragmentAdapter.MarsViewHolder) {
                return super.onSelectedChanged(viewHolder, actionState)
            }
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                (viewHolder as ItemTouchHelperViewAdapter).onItemSelected()
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            if (viewHolder !is RecyclerFragmentAdapter.MarsViewHolder) {
                return super.clearView(recyclerView, viewHolder)
            }
            (viewHolder as ItemTouchHelperViewAdapter).onItemClear()
            super.clearView(recyclerView, viewHolder)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar_recycler, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}