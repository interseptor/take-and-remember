package ru.evka.takeandremember

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.evka.takeandremember.adapter.TodoListAdapter
import ru.evka.takeandremember.databinding.ListFragmentBinding


class ListFragment : Fragment() {
    private var _binding: ListFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val objects = ArrayList<String>()
        objects.add("123")
        objects.add("223")
        objects.add("333")
        objects.add("Liza")
        binding.todoList.adapter = TodoListAdapter(objects)
        binding.todoList.layoutManager = LinearLayoutManager(binding.todoList.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}