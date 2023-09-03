package ru.evka.takeandremember.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.evka.takeandremember.R

class TodoListAdapter(items: List<String> = ArrayList()) : RecyclerView.Adapter<TodoListItemHolder>() {
    var items: List<String> = items
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_item, parent, false)
        return TodoListItemHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListItemHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.item_title).text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }
}