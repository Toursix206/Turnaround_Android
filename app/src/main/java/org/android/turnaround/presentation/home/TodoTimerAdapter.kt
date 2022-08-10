package org.android.turnaround.presentation.home

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.android.turnaround.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.milliseconds



private fun getIgnoredTimeDays(time: Long): Long {
    return Calendar.getInstance().apply {
        timeInMillis = time
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}