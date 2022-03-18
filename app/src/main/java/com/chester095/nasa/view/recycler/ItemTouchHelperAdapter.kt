package com.chester095.nasa.view.recycler

import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int,toPosition: Int)
    fun onItemDismiss(position: Int)
}

interface ItemTouchHelperViewAdapter {
    fun onItemSelected()
    fun onItemClear()
}

fun interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}

