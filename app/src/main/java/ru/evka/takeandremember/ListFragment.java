package ru.evka.takeandremember;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import org.jetbrains.annotations.NotNull;
import ru.evka.takeandremember.adapter.TodoListAdapter;
import ru.evka.takeandremember.databinding.ListFragmentBinding;

import java.util.ArrayList;

public class ListFragment extends Fragment
{
    private ListFragmentBinding binding;

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    )
    {
        binding = ListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> objects = new ArrayList<>();
        objects.add("1234");
        objects.add("223");
        objects.add("333");
        this.binding.todoList.setAdapter(new TodoListAdapter(objects));
        this.binding.todoList.setLayoutManager(new LinearLayoutManager(this.binding.todoList.getContext()));
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}
