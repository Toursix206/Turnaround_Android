package org.android.turnaround.presentation.home

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class SlideUpTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val pageWidth: Int = view.width
        val pageHeight: Int = view.height
        if (-1 < position && position < 0) {
            val scaleFactor = 1 - abs(position) * 0.1f
            val verticalMargin = pageHeight * (1 - scaleFactor) / 2
            val horizontalMargin = pageWidth * (1 - scaleFactor) / 2
            if (position < 0) view.translationX = horizontalMargin - verticalMargin / 2
            else view.translationX = -horizontalMargin + verticalMargin / 2
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
        }
        view.translationY = view.width * -position
        if (position > 0) {
            val yPosition: Float = position * view.height
            view.translationY = yPosition
        }
    }
}