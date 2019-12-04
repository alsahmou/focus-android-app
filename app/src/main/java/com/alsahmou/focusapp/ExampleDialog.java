package com.alsahmou.focusapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment implements ExampleDialog1.ExampleDialogListner {

    private EditText editTextUsername;
    private TextView dialogText;
    private ExampleDialog1 dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Task Fixeer")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNeutralButton("Change Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        openDialog1();


                    }
                })
                .setIcon(R.drawable.ic_android);


        editTextUsername = view.findViewById(R.id.edit_username);
        dialogText = view.findViewById(R.id.dialogtext);
        dialog = new ExampleDialog1();
        System.out.println(dialog.mCurrentTask+"current task in first");
        dialogText.setText(dialog.mCurrentTask);
        return builder.create();

    }

    public void openDialog1() {
        ExampleDialog1 exampleDialog = new ExampleDialog1();
        exampleDialog.show(getFragmentManager(), "asdasd");
    }

    @Override
    public void applyTexts(String task) {
        dialogText.setText(task);
    }
}
