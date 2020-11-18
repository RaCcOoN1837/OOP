package com.example.mycourseproject.Controller;

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

import com.example.mycourseproject.View.MyTask;
import com.example.mycourseproject.R;
import com.example.mycourseproject.View.Storage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class EditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Инициализируем наши компоненты.
    TextView tvDate;
    EditText etTitle, etDescription;
    Button btnSave, btnDelete, btnCancel;

    Date currentDate;

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
        final MyTask myTask = Storage.getStorage().get(position);

        etTitle.setText(myTask.getTitle());
        etDescription.setText(myTask.getDescription());
        currentDate = myTask.getDate();
        tvDate.setText(new SimpleDateFormat("MMM dd").format(currentDate));

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
//                MyTask myTask = Storage.getStorage().get(position);
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

                if ((!newTitle.equals("")) && (currentDate != null)) {

                    Date newDate = currentDate;
                    long newId = newDate.getTime();

                    // Получаем переданный индекс.
                    int position = getIntent().getIntExtra("position", 0);

                    // Изменяем задание.
//                    MyTask myTask = Storage.getStorage().get(position);
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

        this.currentDate = calendar.getTime();
        tvDate.setText(new SimpleDateFormat("MMM dd").format(currentDate));
    }
}