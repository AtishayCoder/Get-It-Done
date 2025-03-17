package com.example.getitdone.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getitdone.databinding.ActivityMainBinding
import com.example.getitdone.databinding.DialogAddTaskBinding
import com.example.getitdone.ui.tasks.TasksFragment
import com.example.getitdone.utils.InputValidator
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val tasksFragment: TasksFragment = TasksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            pager.adapter = PagerAdapter(this@MainActivity)
            TabLayoutMediator(tabs, pager) { tab, position ->
                tab.text = "Tasks"
            }.attach()
            fab.setOnClickListener { showAddTaskDialog() }
            setContentView(root)

            CoroutineScope(Dispatchers.Main).launch {
                delay(3000)
                Toast.makeText(this@MainActivity, "Hallo", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showAddTaskDialog() {
        DialogAddTaskBinding.inflate(layoutInflater).apply {
            editTextTask.requestFocus()

            buttonSave.isEnabled = false

            val dialog = BottomSheetDialog(this@MainActivity)
            dialog.setContentView(root)

            editTextTask.addTextChangedListener { input ->
                buttonSave.isEnabled = InputValidator.checkValidity(input?.toString())
            }

            buttonShowDetails.setOnClickListener {
                if (editTextTaskDetails.visibility == View.GONE) {
                    editTextTaskDetails.visibility = View.VISIBLE
                } else if (editTextTaskDetails.visibility == View.VISIBLE) {
                    editTextTaskDetails.visibility = View.GONE
                }
            }

            buttonSave.setOnClickListener {
                viewModel.createTask(
                    title = editTextTask.text.toString(),
                    desc = editTextTaskDetails.text.toString(),
                    star = checkboxStar.isChecked
                )
                dialog.dismiss()
                tasksFragment.fetchAllTasks()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(root.windowToken, 0)
            }

            dialog.show()
        }
    }


    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment {
            return tasksFragment
        }

    }
}