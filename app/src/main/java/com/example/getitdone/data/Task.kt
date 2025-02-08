package com.example.getitdone.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey (autoGenerate = true) val taskID: Int = 0,
    val title: String,
    val desc: String? = null,
    val isStarred: Boolean = false,
    val isCompleted: Boolean = false,
)
