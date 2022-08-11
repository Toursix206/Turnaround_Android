package org.android.turnaround.domain.entity

import java.util.Date

data class Todo(
    val category: String,
    val label: String,
    val startTime: Date,
    val endTime: Date
)
