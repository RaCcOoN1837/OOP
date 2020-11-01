package com.example.mycourseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/*
    Экран добавления нового задания.
 */
public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Инифиализируем наши компоненты.
    TextView tvDate;
    EditText etTitle, etDescription;
    Button btnAddTask, btnCancel;

    // При открытии экрана:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        // Связываем наши компоненты с XML файлом.
        tvDate = findViewById(R.id.NEWtvDate);
        etTitle = findViewById(R.id.NEWetTitle);
        etDescription = findViewById(R.id.NEWetDescription);
        btnAddTask = findViewById(R.id.NEWbtnAddTask);
        btnCancel = findViewById(R.id.NEWbtnCancel);

        // Открытие календаря по нажатию на кнопку Календарь.
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "");
            }
        });

        // Возвращение на главный экран по нажатию кнопки "Отмена".
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Возвращаемся на главный экран.
                startActivity(new Intent(NewTaskActivity.this, MainActivity.class));
            }
        });

        // Добавление задания по нажатию кнопки "Добавить".
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle = etTitle.getText().toString();
                String newDescription = etDescription.getText().toString();
                String newDate = tvDate.getText().toString();



                if ((!newTitle.equals("")) && (!newDate.equals(""))) {

                    // Создаем новое задание.
                    MyTask myTask = new MyTask(newTitle, newDescription, newDate, false, "777");
                    Storage.getStorage().add(myTask);

                    // Возвращаемся на главный экран.
                    startActivity(new Intent(NewTaskActivity.this, MainActivity.class));
                } else {

                    // Показываем уведомление об ошибке.
                    Toast toast = Toast.makeText(getApplicationContext(), "Заполните поля!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, -670);
                    toast.show();
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
        String currentDate = dateFormat.format(calendar.getTime());

        tvDate.setText(currentDate);
    }
}