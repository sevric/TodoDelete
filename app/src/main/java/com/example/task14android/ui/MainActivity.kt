package com.example.task14android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task14android.databinding.ActivityMainBinding
import com.example.task14android.db.TodoItemData
import com.example.task14android.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val todoAdapter by lazy {
        CustomRecyclerAdapter(
            object : CustomRecyclerAdapter.TodoOnClickListener {
                override fun setOnItemClick(adapterPosition: Int) {
                    var elementToEditID = 0
                    var elementToEditName = ""
                    var elementToEditColor = ""

                    viewModel.getAllNotes()
                        .value?.get(adapterPosition)?.apply {
                            elementToEditID = this.id
                            elementToEditName = this.name
                            elementToEditColor = this.color
                        }

                    val bundle = Bundle().apply {
                        putInt(DialogItemDetailsFragment.ID_GETTING_TAG, elementToEditID)
                        putString(DialogItemDetailsFragment.NAME_GETTING_TAG, elementToEditName)
                        putString(DialogItemDetailsFragment.COLOR_GETTING_TAG, elementToEditColor)
                    }

                    openDialogFragment(bundle)
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setDBObserver()
        setRVAdapterAndLayoutManager()
        setOnAddBtnClick()
    }

    private fun setRVAdapterAndLayoutManager() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = todoAdapter
        setOnAdapterItemSwipe()
    }

    private fun setOnAddBtnClick() {
        binding.btnAddTodo.setOnClickListener {
            openDialogFragment(null)
        }
    }

    private fun setDBObserver() {
        viewModel.getAllNotes().observe(this) { items ->
            setTodoListForAdapter(items)
        }
    }

    private fun setOnAdapterItemSwipe() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                lifecycleScope.launch(Dispatchers.IO) {
//                    val todoItem = viewModel.getTodoByID(position)
                    val todoItem = viewModel.getAllNotes().value?.get(position)
                    if (todoItem != null) {
                        viewModel.removeNote(todoItem)
                    }
                }
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerView)
    }

    private fun openDialogFragment(arguments: Bundle?) {
        val addTodoDialogFragment = DialogItemDetailsFragment()
        addTodoDialogFragment.arguments = arguments
        addTodoDialogFragment.show(supportFragmentManager, "create_todo")
//        addTodoDialogFragment.dialog?.setCanceledOnTouchOutside(false)
        addTodoDialogFragment.isCancelable = false
    }

    private fun setTodoListForAdapter(data: List<TodoItemData>) {
        lifecycleScope.launch(Dispatchers.Main) {
            todoAdapter.todoItemList = data
        }
    }
}

