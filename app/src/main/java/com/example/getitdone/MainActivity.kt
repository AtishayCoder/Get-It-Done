package com.example.getitdone

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getitdone.data.GetItDoneDatabase
import com.example.getitdone.data.Task
import com.example.getitdone.data.TaskDao
import com.example.getitdone.databinding.ActivityMainBinding
import com.example.getitdone.databinding.DialogAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: GetItDoneDatabase
    private lateinit var taskDao: TaskDao
    private val tasksFragment: TasksFragment = TasksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = "Tasks"
        }.attach()
        binding.fab.setOnClickListener {
            showAddTaskDialog()
        }
        db = GetItDoneDatabase.getDatabase(context = this)
        taskDao = db.getTaskDao()
    }

    private fun showAddTaskDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
        dialogBinding.editTextTask.requestFocus()

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.buttonShowDetails.setOnClickListener {
            if (dialogBinding.editTextTaskDetails.visibility == View.GONE) {
                dialogBinding.editTextTaskDetails.visibility = View.VISIBLE
            } else if (dialogBinding.editTextTaskDetails.visibility == View.VISIBLE) {
                dialogBinding.editTextTaskDetails.visibility = View.GONE
            }
        }

        dialogBinding.buttonSave.setOnClickListener {
            thread {
                val task = Task(
                    title = dialogBinding.editTextTask.text.toString(),
                    desc = dialogBinding.editTextTaskDetails.text.toString(),
                    isStarred = dialogBinding.checkboxStar.isChecked,
                    isCompleted = false
                )
                taskDao.createTask(task)
            }

            dialog.dismiss()
            tasksFragment.fetchAllTasks()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(dialogBinding.root.windowToken, 0)
        }

        dialog.show()
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment {
            return tasksFragment
        }

    }
}