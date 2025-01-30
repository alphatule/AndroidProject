package com.example.projectofinalm8;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment {

    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout
        View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);

        // Inicializar RecyclerView
        recyclerView = rootView.findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);

        // Obtener el ViewModel
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Observar las tareas y actualizar el RecyclerView cuando cambien
        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> {
            taskAdapter.setTasks(tasks);
        });

        // Acción de navegación (cuando se haga clic en un ítem de la lista)
        taskAdapter.setOnTaskClickListener(task -> {
            // Navegar a Task   DetailFragment
//            Navigation.findNavController(rootView).navigate(R.id.action_taskListFragment_to_taskDetailFragment);
            Navigation.findNavController(requireView()).navigate(R.id.action_taskListFragment_to_taskDetailFragment);
        });

        return rootView;
    }
}