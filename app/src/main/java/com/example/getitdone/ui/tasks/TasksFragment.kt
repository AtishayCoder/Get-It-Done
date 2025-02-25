package com.example.getitdone.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.getitdone.data.Task
import com.example.getitdone.databinding.TasksFragmentBinding
import kotlin.getValue

class TasksFragment : Fragment(), TasksAdapter.TaskClickListener {

    private val viewModel: TasksViewModel by viewModels()
    private lateinit var binding: TasksFragmentBinding
    private val adapter = TasksAdapter(this)

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
        fetchAllTasks()
        binding.recyclerView.adapter = adapter
    }

    fun fetchAllTasks() {
        viewModel.fetchTasks {
            requireActivity().runOnUiThread {
                adapter.setTasks(it)
            }
        }
    }

    override fun onTaskUpdated(task: Task) {
        viewModel.updateTask(task)
    }

    override fun onTaskDeleted(task: Task) {
        viewModel.deleteTask(task)
    }
}