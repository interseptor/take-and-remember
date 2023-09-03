package ru.evka.takeandremember

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.evka.takeandremember.adapter.TodoListAdapter
import ru.evka.takeandremember.databinding.ListFragmentBinding
import ru.evka.takeandremember.viewmodel.TodoListViewModel


class ListFragment : Fragment() {
    private var _binding: ListFragmentBinding? = null
    private val todoListViewModel: TodoListViewModel by activityViewModels()

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
        binding.todoList.adapter = TodoListAdapter()
        binding.todoList.layoutManager = LinearLayoutManager(binding.todoList.context)
        todoListViewModel.uiState
            .onEach { value -> (binding.todoList.adapter as TodoListAdapter).items = value.items }
            .launchIn(lifecycleScope)

        todoListViewModel.createRandomList()

        binding.button.setOnClickListener { todoListViewModel.createRandomList() }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}