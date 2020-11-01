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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Инициализируем наши компоненты.
    TextView tvDate;
    EditText etTitle, etDescription;
    Button btnSave, btnDelete, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Связываем наши компоненты с XML файлом.
        etTitle = findViewById(R.id.EDITetTitle);
        etDescription = findViewById(R.id.EDITetDescription);
        tvDate = findViewById(R.id.EDITtvDate);
        btnSave = findViewById(R.id.EDITbtnSaveChanges);
        btnDelete = findViewById(R.id.EDITbtnDelete);
        btnCancel = findViewById(R.id.EDITbtnCancel);

        // Получаем параметры выбраного задания.
        etTitle.setText(getIntent().getStringExtra("title"));
        etDescription.setText(getIntent().getStringExtra("description"));
        tvDate.setText(getIntent().getStringExtra("date"));
        final String id = getIntent().getStringExtra("id");
//        final boolean isDone = getIntent().getBooleanExtra("isDone", false);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "");
            }
        });

        // "Отмена".
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EditTaskActivity.this, MainActivity.class));
            }
        });

        // "Удалить".
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Получаем переданный индекс.
                int position = getIntent().getIntExtra("position", 0);

                // Получаем задание по индексу и редактируем его.
                MyTask myTask = Storage.getStorage().get(position);

                Storage.getStorage().remove(myTask);

                startActivity(new Intent(EditTaskActivity.this, MainActivity.class));
            }
        });

        // Сохранение изменений по нажатию кнопки "Сохранить".
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle = etTitle.getText().toString();
                String newDescription = etDescription.getText().toString();
                String newDate = tvDate.getText().toString();

                if ((!newTitle.equals("")) && (!newDate.equals(""))) {

                    // Получаем переданный индекс.
                    int position = getIntent().getIntExtra("position", 0);

                    // Получаем задание по индексу и редактируем его.
                    MyTask myTask = Storage.getStorage().get(position);

                    Storage.getStorage().remove(myTask);

                    myTask.setTitle(newTitle);
                    myTask.setDescription(newDescription);
                    myTask.setDate(newDate);

                    Storage.getStorage().add(myTask);

                    // Возвращаемся на главный экран.
                    startActivity(new Intent(EditTaskActivity.this, MainActivity.class));
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