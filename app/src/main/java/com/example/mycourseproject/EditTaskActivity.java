package com.example.mycourseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTaskActivity extends AppCompatActivity {

    // Инициализируем наши компоненты.
    EditText title, description, date;
    Button btnSaveChanges, btnDelete, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Связываем наши компоненты с XML файлом.
        title = findViewById(R.id.etTaskTitle);
        description = findViewById(R.id.etTaskDescription);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        // Получаем параметры выбраного задания.
        title.setText(getIntent().getStringExtra("title"));
        description.setText(getIntent().getStringExtra("description"));
        final String key = getIntent().getStringExtra("key"); // Не забываем про ключ задания.

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditTaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Удаление задание по нажатию кнопки "Удалить".
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                    ДОБАВИТЬ РЕАЛИЗАЦИЮ УДАЛЕНИЯ ЗАДАНИЯ!
                 */

                startActivity(new Intent(EditTaskActivity.this, MainActivity.class));
            }
        });

        // Сохранение изменений по нажатию кнопки "Сохранить".
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle = title.getText().toString();
                String newDescription = description.getText().toString();
                String newDate = date.getText().toString();

                if ((!newTitle.equals("")) && (!newDate.equals(""))) {

                    /*
                        ДОБАВИТЬ РЕАЛИЗАЦИЮ ИЗМЕНЕНИЯ ЗАДАНИЯ!
                     */

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
}