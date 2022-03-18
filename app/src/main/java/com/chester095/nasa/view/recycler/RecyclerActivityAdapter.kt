package com.chester095.nasa.view.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.chester095.nasa.databinding.ActivityRecyclerItemEarthBinding
import com.chester095.nasa.databinding.ActivityRecyclerItemHeaderBinding
import com.chester095.nasa.databinding.ActivityRecyclerItemMarsBinding

class RecyclerActivityAdapter(
    private val onListItemClickListener: OnListItemClickListener,
    private var dataSet: MutableList<Pair<Int, Data>>,
    private val onStartDragListener:OnStartDragListener
) : RecyclerView.Adapter<RecyclerActivityAdapter.BaseViewHolder>(),ItemTouchHelperAdapter {

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].second.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            TYPE_EARTH -> {
                val itemBinding: ActivityRecyclerItemEarthBinding =
                    ActivityRecyclerItemEarthBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                return EarthViewHolder(itemBinding.root)
            }
            TYPE_MARS -> {
                val itemBinding: ActivityRecyclerItemMarsBinding =
                    ActivityRecyclerItemMarsBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                return MarsViewHolder(itemBinding.root)
            }
            else -> {
                val itemBinding: ActivityRecyclerItemHeaderBinding =
                    ActivityRecyclerItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                return HeaderViewHolder(itemBinding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun addItem() {
        dataSet.add(generateNewItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateNewItem() = Pair(ITEM_CLOSE, Data("new Mars", type = TYPE_MARS))


    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Pair<Int, Data>)
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Int, Data>) {
            ActivityRecyclerItemEarthBinding.bind(itemView).apply {
                earthImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data.second)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view),ItemTouchHelperViewAdapter {
        override fun bind(data: Pair<Int, Data>) {
            ActivityRecyclerItemMarsBinding.bind(itemView).apply {
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
                        val currentState = if(it.first== ITEM_CLOSE) ITEM_OPEN else  ITEM_CLOSE
                        Pair(currentState,it.second)
                    }
                    notifyItemChanged(layoutPosition)
                }
                marsDescriptionTextView.visibility = if(data.first== ITEM_CLOSE) View.GONE else View.VISIBLE

                dragHandleImageView.setOnTouchListener { v, event ->
                    if(MotionEventCompat.getActionMasked(event)==MotionEvent.ACTION_DOWN){
                        onStartDragListener.onStartDrag(this@MarsViewHolder)
                    }
                    false
                }
                
            }
        }

        private fun moveUp() {
            // TODO HW убрать java.lang.IndexOutOfBoundsException
            dataSet.removeAt(layoutPosition).apply {
                dataSet.add(layoutPosition - 1, this)
            }
            notifyItemMoved(layoutPosition, layoutPosition - 1)
        }

        private fun moveDown() {
            // TODO HW убрать java.lang.IndexOutOfBoundsException
            dataSet.removeAt(layoutPosition).apply {
                dataSet.add(layoutPosition + 1, this)
            }
            notifyItemMoved(layoutPosition, layoutPosition + 1)
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

    inner class HeaderViewHolder(view: View) : RecyclerActivityAdapter.BaseViewHolder(view) {
        override fun bind(data: Pair<Int, Data>) {
            ActivityRecyclerItemHeaderBinding.bind(itemView).apply {
                header.setOnClickListener {
                    onListItemClickListener.onItemClick(data.second)
                }
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        // TODO HW Запретить поднимать элементы выше, чем Заголовок

        dataSet.removeAt(fromPosition).apply {
            dataSet.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }
}