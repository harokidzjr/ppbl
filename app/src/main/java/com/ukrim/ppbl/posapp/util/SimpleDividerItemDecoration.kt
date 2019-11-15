package com.ukrim.ppbl.posapp.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.ukrim.ppbl.posapp.R


class SimpleDividerItemDecoration(context: Context) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
    private val mDivider: Drawable = context.resources.getDrawable(R.drawable.line_divider)

    override fun onDrawOver(c: Canvas, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        val left = parent.paddingLeft + 70
        val right = parent.width - parent.paddingRight - 70

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as androidx.recyclerview.widget.RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }
}