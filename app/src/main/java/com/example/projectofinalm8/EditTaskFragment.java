package com.example.projectofinalm8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class EditTaskFragment extends Fragment {

    private TaskViewModel taskViewModel;
    private EditText editTextTitle, editTextDescription;
    private Button buttonSave;
    private int taskId;  // ID de la tarea a editar

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        // Inicializar UI
        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        buttonSave = view.findViewById(R.id.buttonSave);

        // Inicializar ViewModel
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Obtener el ID de la tarea desde el Bundle
        if (getArguments() != null) {
            taskId = getArguments().getInt("taskId", -1);
            if (taskId != -1) {
                // Cargar la tarea desde ViewModel
                taskViewModel.getTaskById(taskId).observe(getViewLifecycleOwner(), task -> {
                    if (task != null) {
                        editTextTitle.setText(task.getTitle());
                        editTextDescription.setText(task.getDescription());
                    }
                });
            }
        }

        // Guardar cambios al hacer clic en el botón
        buttonSave.setOnClickListener(v -> saveTask());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Recuperar taskId del Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            taskId = bundle.getInt("taskId", -1);
        }

        // Cargar la tarea en el ViewModel
        taskViewModel.getTaskById(taskId).observe(getViewLifecycleOwner(), task -> {
            if (task != null) {
                // Asignar el título y descripción en los EditText
                editTextTitle.setText(task.getTitle());
                editTextDescription.setText(task.getDescription());
            }
        });
    }

    private void saveTask() {
        String newTitle = editTextTitle.getText().toString().trim();
        String newDescription = editTextDescription.getText().toString().trim();
        System.out.println("Se supone que se mete aqui " + (!newTitle.isEmpty() && taskId != -1) + " " + newTitle.isEmpty() + " " + taskId);
        if (!newTitle.isEmpty() && taskId != -1) {
            Task updatedTask = new Task(taskId, newTitle, newDescription);
            System.out.println("EditTaskFragment update " + updatedTask);
            taskViewModel.update(updatedTask);

            // Volver a la pantalla anterior
            Navigation.findNavController(requireView()).popBackStack();
        }
    }
}
