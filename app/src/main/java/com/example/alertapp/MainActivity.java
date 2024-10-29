package com.example.alertapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button toast;
    Button alert;
    Button alertNewPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toast = findViewById(R.id.button2);
        alert = findViewById(R.id.button);
        alertNewPage = findViewById(R.id.button3);

        toast.setOnClickListener(view -> {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast, null);

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        });

        alert.setOnClickListener(view -> showMultiChoiceDialog());

        alertNewPage.setOnClickListener(view -> showInputDialog());
    }

    private void showMultiChoiceDialog() {

        String[] options = {"Bread", "Milk", "Chocolate"};
        boolean[] selectedOptions = {false, false, false};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your favorite food?");


        builder.setMultiChoiceItems(options, selectedOptions, (dialog, which, isChecked) -> selectedOptions[which] = isChecked);


        builder.setPositiveButton("Confirm", (dialog, which) -> {
            StringBuilder selectedItems = new StringBuilder("Selected options: ");
            for (int i = 0; i < options.length; i++) {
                if (selectedOptions[i]) {
                    selectedItems.append(options[i]).append(" ");
                }
            }
            Toast.makeText(MainActivity.this, selectedItems.toString(), Toast.LENGTH_SHORT).show();
        });


        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void showInputDialog() {
        // Create an EditText for input
        final EditText input = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Text")
                .setView(input)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    String enteredText = input.getText().toString();

                    // Start DisplayTextActivity with entered text
                    Intent intent = new Intent(MainActivity.this, DisplayTextActivity.class);
                    intent.putExtra("enteredText", enteredText);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); // Cancel the dialog
                    }
                });

        builder.create().show();
    }
}