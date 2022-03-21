package com.chester095.nasa.view.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.chester095.nasa.databinding.FragmentRecyclerItemEarthBinding
import com.chester095.nasa.databinding.FragmentRecyclerItemHeaderBinding
import com.chester095.nasa.databinding.FragmentRecyclerItemMarsBinding

class RecyclerFragmentAdapter(
    private val onListItemClickListener: OnListItemClickListener,
    private var dataSet: MutableList<Pair<Int, Data>>,
    private val onStartDragListener: OnStartDragListener
) : RecyclerView.Adapter<RecyclerFragmentAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].second.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            TYPE_EARTH -> {
                val itemBinding: FragmentRecyclerItemEarthBinding =
                    FragmentRecyclerItemEarthBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                return EarthViewHolder(itemBinding.root)
            }
            TYPE_MARS -> {
                val itemBinding: FragmentRecyclerItemMarsBinding =
                    FragmentRecyclerItemMarsBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                return MarsViewHolder(itemBinding.root)
            }
            else -> {
                val itemBinding: FragmentRecyclerItemHeaderBinding =
                    FragmentRecyclerItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                return HeaderViewHolder(itemBinding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isNotEmpty()&&(holder is MarsViewHolder)){
            val pair = createCombinePayload(payloads as List<Change<Pair<Int, Data>>>)
            FragmentRecyclerItemMarsBinding.bind(holder.itemView).marsTextView.text = pair.newData.second.someText
        }else{
            super.onBindViewHolder(holder, position, payloads)
        }

    }

    override fun getItemCount() = dataSet.size

    fun addItem() {
        dataSet.add(generateNewItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateNewItem() = Pair(ITEM_CLOSE, Data(someText ="new Mars", type = TYPE_MARS))


    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Pair<Int, Data>)
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Int, Data>) {
            FragmentRecyclerItemEarthBinding.bind(itemView).apply {
                earthImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data.second)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewAdapter {
        override fun bind(data: Pair<Int, Data>) {
            FragmentRecyclerItemMarsBinding.bind(itemView).apply {
                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data.second)
                }
                addItemImageView.setOnClickListener { addItemByPosition() }
                removeItemImageView.setOnClickListener { removeItem() }
                moveItemUp.setOnClickListener {
                    moveUp()
                }
                moveItemDown.setOnClickListener {
                    moveDown()
                }
                marsTextView.setOnClickListener {
                    dataSet[layoutPosition] = dataSet[layoutPosition].let {
                        val currentState = if (it.first == ITEM_CLOSE) ITEM_OPEN else ITEM_CLOSE
                        Pair(currentState, it.second)
                    }
                    notifyItemChanged(layoutPosition)
                }
                marsDescriptionTextView.visibility = if (data.first == ITEM_CLOSE) View.GONE else View.VISIBLE

                dragHandleImageView.setOnTouchListener { v, event ->
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        onStartDragListener.onStartDrag(this@MarsViewHolder)
                    }
                    false
                }

            }
        }

        private fun moveUp() {
            if (layoutPosition > 1) {
                dataSet.removeAt(layoutPosition).apply {
                    dataSet.add(layoutPosition - 1, this)
                }
                notifyItemMoved(layoutPosition, layoutPosition - 1)
            }
        }

        private fun moveDown() {
            if (layoutPosition < dataSet.size-1) {
                dataSet.removeAt(layoutPosition).apply {
                    dataSet.add(layoutPosition + 1, this)
                }
                notifyItemMoved(layoutPosition, layoutPosition + 1)
            }
        }

        private fun addItemByPosition() {
            dataSet.add(layoutPosition + 1, generateNewItem())
            notifyItemInserted(layoutPosition + 1)
        }

        private fun removeItem() {
            dataSet.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerFragmentAdapter.BaseViewHolder(view) {
        override fun bind(data: Pair<Int, Data>) {
            FragmentRecyclerItemHeaderBinding.bind(itemView).apply {
                header.setOnClickListener {
                    onListItemClickListener.onItemClick(data.second)
                }
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        dataSet.removeAt(fromPosition).apply {
            if (toPosition < 1) {
                dataSet.add(1, this)
            } else dataSet.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }
}