package com.example.mycourseproject.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycourseproject.Model.Storage.DBHelper;
import com.example.mycourseproject.Model.MyTask;
import com.example.mycourseproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
    Экран добавления нового задания.
 */
public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Инифиализируем наши компоненты.
    private TextView tvDate;
    private EditText etTitle, etDescription;
    private Button btnAddTask, btnCancel;

    private long newDate;
    private DBHelper helper;
    private Context context;

    // При открытии экрана:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        context = this;
        helper = new DBHelper(this);

        // Связываем наши компоненты с XML файлом.
        tvDate = findViewById(R.id.NEWtvDate);
        etTitle = findViewById(R.id.NEWetTitle);
        etDescription = findViewById(R.id.NEWetDescription);
        btnAddTask = findViewById(R.id.NEWbtnAddTask);
        btnCancel = findViewById(R.id.NEWbtnCancel);

        // Открываем календарь.
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "");
            }
        });

        // Отмена (Возвращаемся на главный экран).
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Возвращаемся на главный экран.
                startActivity(new Intent(NewTaskActivity.this, MainActivity.class));
            }
        });

        // Добавляем задание.
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle = etTitle.getText().toString();
                String newDescription = etDescription.getText().toString();

                // Задания без названия нам не нужны.
                if ((!newTitle.equals("")) && (!tvDate.getText().equals(""))) {

                    // Создаем новое задание.
                    MyTask myTask = new MyTask(newTitle, newDescription, newDate, 0, 0);
                    myTask.setId(myTask.getDate());

                    // Записываем его в БД.
                    helper.insertTask(myTask);

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

        this.newDate = calendar.getTime().getTime();
        tvDate.setText(new SimpleDateFormat("MMM dd").format(new Date(newDate)));
    }

    @Override
    public void onBackPressed() {
        // "Парализуем" кнопку "Назад", так как все необходимые кнопки у нас имеются.
    }
}