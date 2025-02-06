package com.example.projectofinalm8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment implements AddTaskDialogFragment.TaskDialogListener {

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
            System.out.println("Se hace setOnTaskClickListener " + task + " - " + task.getId());
            Bundle bundle = new Bundle();
            bundle.putInt("taskId", task.getId()); // Asegúrate de que taskId tiene el valor correcto
            Navigation.findNavController(requireView()).navigate(R.id.action_taskListFragment_to_taskDetailFragment, bundle);
        });

        // Inicializar FAB y configurar clic para abrir el diálogo
        FloatingActionButton fab = rootView.findViewById(R.id.fab_add_task);
        fab.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            AddTaskDialogFragment dialog = new AddTaskDialogFragment();
            dialog.setTargetFragment(TaskListFragment.this, 0);
            dialog.show(fragmentManager, "AddTaskDialogFragment");
        });

        return rootView;
    }

    @Override
    public void onTaskAdded(String taskName) {
        if (taskName != null && !taskName.trim().isEmpty()) {
            // Agregar la nueva tarea al ViewModel (se guardará en la BD con Room)
            Task newTask = new Task(taskName);
            taskViewModel.insert(newTask);
        }
    }
}
