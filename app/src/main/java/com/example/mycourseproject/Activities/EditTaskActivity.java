package com.example.mycourseproject.Activities;

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

import com.example.mycourseproject.CustomComparator;
import com.example.mycourseproject.DatePickerFragment;
import com.example.mycourseproject.MyTask;
import com.example.mycourseproject.R;
import com.example.mycourseproject.Storage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class EditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Инициализируем наши компоненты.
    TextView tvDate;
    EditText etTitle, etDescription;
    Button btnSave, btnDelete, btnCancel;

    Date tempDate;

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
        int position = getIntent().getIntExtra("position", 0);
        MyTask myTask = Storage.getStorage().get(position);

        etTitle.setText(myTask.getTitle());
        etDescription.setText(myTask.getDescription());
//        tvDate.setText(myTask.getDate());
        final long id = myTask.getId();
        final boolean isDone = myTask.isDone();

        // Открываем календарь.
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

                // Возвращаемся на главный экран.
                startActivity(new Intent(EditTaskActivity.this, MainActivity.class));
            }
        });

        // Удаляем задание
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Получаем переданный индекс.
                int position = getIntent().getIntExtra("position", 0);

                // Удаляем задание из списка.
                MyTask myTask = Storage.getStorage().get(position);
                Storage.getStorage().remove(myTask);

                startActivity(new Intent(EditTaskActivity.this, MainActivity.class));
            }
        });

        // Изменяем задание
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle = etTitle.getText().toString();
                String newDescription = etDescription.getText().toString();
                Date newDate = tempDate;
                long newId = newDate.getTime();

                if ((!newTitle.equals("")) && (!newDate.equals(""))) {

                    // Получаем переданный индекс.
                    int position = getIntent().getIntExtra("position", 0);

                    // Изменяем задание.
                    MyTask myTask = Storage.getStorage().get(position);
                    myTask.setTitle(newTitle);
                    myTask.setDescription(newDescription);
                    myTask.setDate(newDate);
                    myTask.setId(newId);

                    // Сортируем список.
                    Collections.sort(Storage.getStorage().getList(), new CustomComparator());

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

        tempDate = calendar.getTime();
        tvDate.setText(currentDate);
    }
}