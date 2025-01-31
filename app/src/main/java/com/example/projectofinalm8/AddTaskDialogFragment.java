package com.example.projectofinalm8;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddTaskDialogFragment extends DialogFragment {

    public interface TaskDialogListener {
        void onTaskAdded(String taskName);
    }

    private TaskDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (TaskDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("El fragmento padre debe implementar TaskDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_task, null);

        EditText editTextTask = view.findViewById(R.id.editTextTask);
        Button btnAddTask = view.findViewById(R.id.btnAddTask);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        builder.setView(view);
        AlertDialog dialog = builder.create();

        btnAddTask.setOnClickListener(v -> {
            String taskName = editTextTask.getText().toString().trim();
            if (!taskName.isEmpty()) {
                listener.onTaskAdded(taskName);
                dismiss();
            }
        });

        btnCancel.setOnClickListener(v -> dismiss());

        return dialog;
    }
}
