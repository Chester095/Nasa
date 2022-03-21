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

class RecyclerFragment : Fragment() {

    var data = arrayListOf<Pair<Int, Data>>()
    private lateinit var adapter: RecyclerFragmentAdapter
    private var _binding: FragmentRecyclerBinding? = null
    private val binding: FragmentRecyclerBinding
        get() {
            return _binding!!
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
            data.add(0, Pair(ITEM_CLOSE, Data(someText = "Заголовок", type = TYPE_HEADER)))
            data.add(1, Pair(ITEM_CLOSE, Data(someText = "Earth", type = TYPE_EARTH)))
            data.add(2, Pair(ITEM_CLOSE, Data(someText = "Earth", type = TYPE_EARTH)))
            data.add(3, Pair(ITEM_CLOSE, Data(someText = "Mars 1", type = TYPE_MARS, weight = 1000)))
            data.add(4, Pair(ITEM_CLOSE, Data(someText = "Earth", type = TYPE_EARTH)))
            data.add(5, Pair(ITEM_CLOSE, Data(someText = "Earth", type = TYPE_EARTH)))
            data.add(6, Pair(ITEM_CLOSE, Data(someText = "Earth", type = TYPE_EARTH)))
            data.add(7, Pair(ITEM_CLOSE, Data(someText = "Mars 2", type = TYPE_MARS, weight = 2000)))
        }


        /*
        подсказка по пункту
        * Добавьте назначение приоритета заметкам.
        data.filter {
           it.second.someText.equals("swefg")
           //it.second.someText.contains("swefg")
           //it.second.weight==1000
        }
             */


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
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerFAB.setOnClickListener {
            adapter.addItem()
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_find_recycler -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FilterFragment.newInstance())
                    .addToBackStack("").commit()
            }
            R.id.app_bar_sort_recycler -> {
                data.get(2).second.weight = 1000
                data.sortWith(compareBy { it.second.weight })
                data.forEach{Log.d("!!!", "   "+ it)}
/*                data.sortedWith { l, r ->
                    if (l.second.weight > r.second.weight) {
                        1
                    } else {
                        -1
                    }
//                    Log.d("!!!", "l "+ l + "   r " + r)
                }*/
            }
            android.R.id.home -> {
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