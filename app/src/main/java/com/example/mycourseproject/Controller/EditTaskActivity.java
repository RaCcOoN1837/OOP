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

public class EditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Инициализируем наши компоненты.
    private TextView tvDate;
    private EditText etTitle, etDescription;
    private Button btnSave, btnDelete, btnCancel;

    private long currentDate;
    private DBHelper helper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        context = this;
        helper = new DBHelper(this);

        // Связываем наши компоненты с XML файлом.
        etTitle = findViewById(R.id.EDITetTitle);
        etDescription = findViewById(R.id.EDITetDescription);
        tvDate = findViewById(R.id.EDITtvDate);
        btnSave = findViewById(R.id.EDITbtnSaveChanges);
        btnDelete = findViewById(R.id.EDITbtnDelete);
        btnCancel = findViewById(R.id.EDITbtnCancel);

        // Получаем ID выбраного задания.
        long currentID = getIntent().getLongExtra("id", 0);

        // Получаем текущее задание по его ID.
        final MyTask myTask = helper.getTask(currentID);

        // Сохраняем текущий ID, так как он нам пригодится в дальнейшем.
        final long oldID = myTask.getId();

        // Отображаем текущие параметры задания в полях для редактирования.
        etTitle.setText(myTask.getTitle());
        etDescription.setText(myTask.getDescription());
        currentDate = myTask.getDate();
        tvDate.setText(new SimpleDateFormat("MMM dd").format(new Date(currentDate)));

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

        // Удаляем задание.
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Удаляем задание из БД.
                helper.deleteTask(myTask);

                // Возвращаемся на главный экран.
                startActivity(new Intent(EditTaskActivity.this, MainActivity.class));
            }
        });

        // Изменяем задание.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle = etTitle.getText().toString();
                String newDescription = etDescription.getText().toString();

                // Проверка на пустые поля.
                if ((!newTitle.equals("")) && (currentDate != 0)) {

                    long newDate = currentDate;
                    long newId = newDate;

                    // Изменяем задание.
                    myTask.setTitle(newTitle);
                    myTask.setDescription(newDescription);
                    myTask.setDate(newDate);
                    myTask.setId(newId);

                    // Обновляем задание в БД.
                    helper.updateTask(myTask, oldID);

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

        this.currentDate = calendar.getTime().getTime();
        tvDate.setText(new SimpleDateFormat("MMM dd").format(new Date(currentDate)));
    }

    @Override
    public void onBackPressed() {
        // "Парализуем" кнопку "Назад", так как все необходимые кнопки у нас имеются.
    }
}