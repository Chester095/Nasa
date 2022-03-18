package com.chester095.nasa.view.recycler


const val TYPE_EARTH=1
const val TYPE_MARS=2
const val TYPE_HEADER=3

const val ITEM_CLOSE=0
const val ITEM_OPEN=1
data class Data(val someText:String="title",val description:String="description",val type:Int = TYPE_EARTH,var weight:Int = 0)
