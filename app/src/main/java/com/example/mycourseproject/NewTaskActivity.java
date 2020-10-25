package com.example.mycourseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/*
    Экран добавления нового задания.
 */
public class NewTaskActivity extends AppCompatActivity {

    // Инифиализируем наши компоненты.
    TextView tvTitle, tvAddTitle, tvAddDescription, tvAddDate;
    EditText etTaskTitle, etTaskDescription, etTaskDate;
    Button btnAddTask, btnCancel;

    DatabaseReference reference; // Ссылка для работы с базой данных.
    Integer taskNumber = Integer.MAX_VALUE - (int) System.currentTimeMillis(); // Ключи заданий будут создаваться по убыванию для нужной сортировки.
    String taskKey = Integer.toString(taskNumber); // Ключ задания (необходим для редактирования задания).


    // При открытии экрана:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        // Связываем наши компоненты с XML файлом.
        tvTitle = findViewById(R.id.tvTitle);
        tvAddTitle = findViewById(R.id.tvAddTitle);
        tvAddDescription = findViewById(R.id.tvAddDescription);
        tvAddDate = findViewById(R.id.tvAddDate);
        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        etTaskDate = findViewById(R.id.etTaskDate);
        btnAddTask = findViewById(R.id.btnAddTask);
        btnCancel = findViewById(R.id.btnCancel);

        // Возвращение на главный экран по нажатию кнопки "Отмена".
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Возвращаемся на главный экран.
                Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Добавление задания по нажатию кнопки "Добавить".
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Ссылка для работы с базой данных.
                reference = FirebaseDatabase.getInstance().getReference().child("TaskBox").child("Task" + taskKey);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String newTitle = etTaskTitle.getText().toString();
                        String newDescription = etTaskDescription.getText().toString();
                        String newDate = etTaskDate.getText().toString();

                        // Передаем данные в БД.
                        if ((!newTitle.equals("")) && (!newDate.equals(""))) {

                            dataSnapshot.getRef().child("title").setValue(newTitle);
                            dataSnapshot.getRef().child("description").setValue(newDescription);
                            dataSnapshot.getRef().child("date").setValue(newDate);
                            dataSnapshot.getRef().child("key").setValue(taskKey); // Не забываем передать ключ.

                            // Возвращаемся на главный экран.
                            Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {

                            // Показываем уведомление об ошибке.
                            Toast toast = Toast.makeText(getApplicationContext(), "Заполните поля!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, -670);
                            toast.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        });
    }
}