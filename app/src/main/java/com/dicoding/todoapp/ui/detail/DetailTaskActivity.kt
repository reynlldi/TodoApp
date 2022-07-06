package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailTaskViewModel
    private var mTaskId = 0

    private lateinit var btnDelete: Button
    private lateinit var edDesc: EditText
    private lateinit var edTitle: EditText
    private lateinit var edDueDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        edTitle = findViewById(R.id.detail_ed_title)
        edDesc = findViewById(R.id.detail_ed_description)
        edDueDate = findViewById(R.id.detail_ed_due_date)
        btnDelete = findViewById(R.id.btn_delete_task)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)

        //TODO 11 : Show detail task and implement delete action
        if(intent.extras != null){
            mTaskId = intent.extras!!.getInt(TASK_ID)
            viewModel.setTaskId(mTaskId)
        }

        viewModel.task.observe(this){
            showTask(it)
        }

        val btnDelete = findViewById<Button>(R.id.btn_delete_task)
        btnDelete.setOnClickListener {
            viewModel.deleteTask()
            finish()
        }
    }

    private fun showTask(task: Task?) {
        if(task!=null){
            edTitle.setText(task.title)
            edDesc.setText(task.description)
            edDueDate.setText(DateConverter.convertMillisToString(task.dueDateMillis))
        }
    }
}