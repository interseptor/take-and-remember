package ru.evka.takeandremember.adapter;

import android.util.Log;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import ru.evka.takeandremember.R;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListItemHolder>
{
    private List<String> items;

    public TodoListAdapter(List<String> items)
    {
        this.items = items;
    }

    @NonNull
    @NotNull
    @Override
    public TodoListItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new TodoListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TodoListItemHolder holder, int position)
    {
        holder.itemView.<TextView>findViewById(R.id.item_title).setText(items.get(position));
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }
}
