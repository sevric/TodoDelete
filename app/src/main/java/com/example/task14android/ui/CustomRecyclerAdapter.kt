package com.example.task14android.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task14android.databinding.RecyclerItemBinding
import com.example.task14android.db.TodoItemData

class CustomRecyclerAdapter(
    private var itemClickListener: TodoOnClickListener
) : RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolderLive>() {

    var todoItemList: List<TodoItemData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CustomViewHolderLive(
        val todoItemBinding: RecyclerItemBinding
    ) : RecyclerView.ViewHolder(todoItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderLive {
        return CustomViewHolderLive(
            RecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolderLive, position: Int) {
        val todoItem = todoItemList[position]

        holder.todoItemBinding.rvItemName.text = todoItem.name
        holder.todoItemBinding.rvItemName.setBackgroundColor(393)
        holder.itemView.setOnClickListener {
            itemClickListener.setOnItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return todoItemList.size
    }

    interface TodoOnClickListener {
        fun setOnItemClick(adapterPosition: Int)
    }
}