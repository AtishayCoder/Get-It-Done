package com.example.getitdone

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getitdone.data.Task
import com.example.getitdone.databinding.ItemTaskBinding

class TasksAdapter(val listener: TaskUpdatedListener) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var tasks: List<Task> = listOf()

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

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks.sortedBy {
            it.isCompleted
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.apply {
                checkbox.isChecked = task.isCompleted
                toggleStar.isChecked = task.isStarred
                if (task.isCompleted) {
                    textViewTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    textViewDesc.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textViewTitle.paintFlags = 0
                    textViewDesc.paintFlags = 0
                }
                textViewTitle.text = task.title
                textViewDesc.text = task.desc
                checkbox.setOnClickListener {
                    listener.onTaskUpdated(task.copy(isCompleted = checkbox.isChecked))
                }
                toggleStar.setOnClickListener {
                    listener.onTaskUpdated(task.copy(isStarred = toggleStar.isChecked))
                }
            }
        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }
}