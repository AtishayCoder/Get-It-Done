package com.example.getitdone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.getitdone.data.GetItDoneDatabase
import com.example.getitdone.data.Task
import com.example.getitdone.databinding.ItemTaskBinding
import kotlin.concurrent.thread

class TasksAdapter(val tasks: List<Task>) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size


    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.textViewTitle.text = task.title
            binding.textViewDesc.text = task.desc
            binding.textViewDesc.visibility = if (task.desc?.length != 0) View.VISIBLE else View.GONE
        }
    }
}