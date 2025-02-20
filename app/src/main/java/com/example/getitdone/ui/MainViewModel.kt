package com.example.getitdone.ui

import androidx.lifecycle.ViewModel
import com.example.getitdone.GetItDoneApplication
import com.example.getitdone.data.GetItDoneDatabase
import com.example.getitdone.data.Task
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {
    private val taskDao = GetItDoneApplication.dao

    fun createTask (title: String, desc: String?, star: Boolean) {
        val task = Task(
            title = title,
            desc = desc,
            isStarred = star,
            isCompleted = false
        )

        thread {
            taskDao.createTask(task)
        }
    }
}