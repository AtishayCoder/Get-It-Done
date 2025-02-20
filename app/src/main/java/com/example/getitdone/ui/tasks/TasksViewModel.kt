package com.example.getitdone.ui.tasks

import androidx.lifecycle.ViewModel
import com.example.getitdone.GetItDoneApplication
import com.example.getitdone.data.Task
import kotlin.concurrent.thread

class TasksViewModel : ViewModel() {
    private val taskDao = GetItDoneApplication.dao

    fun fetchTasks(callback: (List<Task>) -> Unit) {
        thread {
            val tasks = taskDao.getAllTasks()
            callback(tasks)
        }
    }

    fun updateTask(task: Task) {
        thread {
            taskDao.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        thread {
            taskDao.deleteTask(task)
        }
    }
}