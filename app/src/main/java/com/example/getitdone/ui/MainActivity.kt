package com.example.getitdone.ui

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getitdone.data.GetItDoneDatabase
import com.example.getitdone.data.Task
import com.example.getitdone.data.TaskDao
import com.example.getitdone.databinding.ActivityMainBinding
import com.example.getitdone.databinding.DialogAddTaskBinding
import com.example.getitdone.ui.tasks.TasksFragment
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
        DialogAddTaskBinding.inflate(layoutInflater).apply {
            editTextTask.requestFocus()

            buttonSave.isEnabled = false

            val dialog = BottomSheetDialog(this@MainActivity)
            dialog.setContentView(root)

            editTextTask.addTextChangedListener { input ->
                buttonSave.isEnabled = checkValidity(input)
            }

            buttonShowDetails.setOnClickListener {
                if (editTextTaskDetails.visibility == View.GONE) {
                    editTextTaskDetails.visibility = View.VISIBLE
                } else if (editTextTaskDetails.visibility == View.VISIBLE) {
                    editTextTaskDetails.visibility = View.GONE
                }
            }

            buttonSave.setOnClickListener {
                thread {
                    val task = Task(
                        title = editTextTask.text.toString(),
                        desc = editTextTaskDetails.text.toString(),
                        isStarred = checkboxStar.isChecked,
                        isCompleted = false
                    )
                    taskDao.createTask(task)
                }

                dialog.dismiss()
                tasksFragment.fetchAllTasks()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(root.windowToken, 0)
            }

            dialog.show()
        }
    }

    fun checkValidity(input: Editable?): Boolean = !input?.trim().isNullOrEmpty() && input.length > 1

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment {
            return tasksFragment
        }

    }
}