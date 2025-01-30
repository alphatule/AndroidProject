package com.example.projectofinalm8;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends Fragment {

    private TaskViewModel taskViewModel;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private Button buttonSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_detail, container, false);

        // Inicializa los elementos del formulario
        editTextTitle = rootView.findViewById(R.id.editTextTitle);
        editTextDescription = rootView.findViewById(R.id.editTextDescription);
        buttonSave = rootView.findViewById(R.id.buttonSave);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        buttonSave.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();
            Task newTask = new Task(title, description, false); // Tarea no completada por defecto
            taskViewModel.insert(newTask);

            // Navegar de vuelta a TaskListFragment
//            Navigation.findNavController(rootView).navigate(R.id.action_taskDetailFragment_to_taskListFragment);
            Navigation.findNavController(requireView()).navigate(R.id.action_taskDetailFragment_to_taskListFragment);

        });

        return rootView;
    }
}