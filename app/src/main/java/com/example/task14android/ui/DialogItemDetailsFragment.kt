package com.example.task14android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.task14android.databinding.FragmentDialogItemDetailsBinding
import com.example.task14android.db.TodoItemData
import com.example.task14android.viewmodel.DialogDetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogItemDetailsFragment : DialogFragment() {
    private var _binding: FragmentDialogItemDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[DialogDetViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayoutFromArguments()
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            upsertTodo()
            dialog?.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setLayoutFromArguments() {
        val textOfElementToEdit = arguments?.getString(NAME_GETTING_TAG)
        binding.etWhatToDo.setText(textOfElementToEdit)
    }

    private fun upsertTodo() {
        val todoName = binding.etWhatToDo.text.toString()

        if (arguments == null) {
            val newTodo = TodoItemData(0, todoName, "#ff00ff")
            insertTodo(newTodo)
        } else {
//        val todoID = requireArguments().getInt(ID_GETTING_TAG)
            val todoID = arguments?.getInt(ID_GETTING_TAG)
//        val todoColor = requireArguments().getString(COLOR_GETTING_TAG)
            val todoColor = arguments?.getString(COLOR_GETTING_TAG)

            val newTodo = TodoItemData(todoID!!, todoName, todoColor!!)
            updateTodo(newTodo)
        }
    }

    private fun insertTodo(newTodo: TodoItemData) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.addTodo(newTodo)
        }
    }

    private fun updateTodo(newTodo: TodoItemData) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.updateTodo(newTodo)
        }
    }

//        private fun closeCurrentFragment() {
//        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
//    }

    companion object {
        const val ID_GETTING_TAG = "EDITABLE_ITEM_ID"
        const val NAME_GETTING_TAG = "EDITABLE_ITEM_NAME"
        const val COLOR_GETTING_TAG = "EDITABLE_ITEM_COLOR"
    }
}