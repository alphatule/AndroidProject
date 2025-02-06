package com.example.projectofinalm8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.ViewGroup;
import android.widget.TextView;

public class TaskDetailFragment extends Fragment {

    private TaskViewModel taskViewModel;
    private TextView textViewTaskTitle;
    private TextView textViewTaskDescription;
    private int taskId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_detail, container, false);

        // Configurar Toolbar
        Toolbar toolbar = rootView.findViewById(R.id.toolbar_task_detail);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        // Inicializar UI
        textViewTaskTitle = rootView.findViewById(R.id.textViewTaskTitle);
        textViewTaskDescription = rootView.findViewById(R.id.textViewTaskDescription);

        // Obtener el ViewModel
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Obtener ID de la tarea (si fue pasada como argumento)
        System.out.println("WTAS DAS " + taskId + " - " + getArguments() + " - ");
        if (getArguments() != null) {
            taskId = getArguments().getInt("taskId");
//            loadTaskDetails(taskId);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el taskId de los argumentos
        if (getArguments() != null) {
            taskId = getArguments().getInt("taskId", -1); // Valor predeterminado -1 si no se pasa nada
            System.out.println("Task ID en onViewCreated: " + taskId);  // Depuración
        }

        // Obtenemos la tarea por su ID y mostramos los detalles
        taskViewModel.getTaskById(taskId).observe(getViewLifecycleOwner(), task -> {
            if (task != null) {
                textViewTaskTitle.setText(task.getTitle());
                // Aquí puedes poner también la descripción, si la tienes
                textViewTaskDescription.setText(task.getDescription());
            }
        });
    }

//    private void loadTaskDetails(int id) {
//        taskViewModel.getTaskById(id).observe(getViewLifecycleOwner(), task -> {
//            if (task != null) {
//                textViewTaskTitle.setText(task.getTitle());
//            }
//        });
//    }

    // Inflar menú en Toolbar
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.task_detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Manejar clic en el botón de edición
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit_task) {
            Bundle bundle = new Bundle();
            bundle.putInt("taskId", taskId);
            Navigation.findNavController(requireView()).navigate(R.id.action_taskDetailFragment_to_editTaskFragment, bundle);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
