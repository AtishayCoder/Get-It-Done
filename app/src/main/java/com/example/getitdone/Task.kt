package com.example.getitdone

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey (autoGenerate = true) val taskID: Int = 0,
    val title: String,
    val desc: String? = null,
    val isStarred: Boolean = false,
)
