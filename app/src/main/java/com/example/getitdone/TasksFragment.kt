package com.example.getitdone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.getitdone.data.GetItDoneDatabase
import androidx.fragment.app.Fragment
import com.example.getitdone.data.Task
import com.example.getitdone.data.TaskDao
import com.example.getitdone.databinding.TasksFragmentBinding
import kotlin.concurrent.thread

class TasksFragment : Fragment() {

    private lateinit var binding: TasksFragmentBinding
    private val taskDao: TaskDao by lazy {
        GetItDoneDatabase.getDatabase(requireContext()).getTaskDao()
    }
    private var tasks: List<Task> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TasksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun fetchAllTasks() {
        thread {
            tasks = taskDao.getAllTasks()
            requireActivity().runOnUiThread {
                binding.recyclerView.adapter = TasksAdapter(
                    tasks = tasks
                )
            }
        }
    }
}