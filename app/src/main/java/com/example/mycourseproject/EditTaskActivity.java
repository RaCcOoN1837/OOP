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
    Button btnSaveChanges, btnDelete;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Связываем наши компоненты с XML файлом.
        title = findViewById(R.id.etTaskTitle);
        description = findViewById(R.id.etTaskDescription);
        date = findViewById(R.id.etTaskDate);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnDelete = findViewById(R.id.btnDelete);

        // Получаем параметры выбраного задания.
        title.setText(getIntent().getStringExtra("title"));
        description.setText(getIntent().getStringExtra("description"));
        date.setText(getIntent().getStringExtra("date"));
        final String key = getIntent().getStringExtra("key"); // Не забываем про ключ задания.

        // Ссылка для работы с базой данных.
        reference = FirebaseDatabase.getInstance().getReference().child("TaskBox").child("Task" + key);

        // Удаление задание по нажатию кнопки "Удалить".
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Удаляем задание из базы данных.
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        // Проверяем успешность удаления.
                        if (task.isSuccessful()) {

                            // Возвращаемся на главный экран.
                            Intent intent = new Intent(EditTaskActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {

                            // Показываем уведомление об ошибке.
                            Toast.makeText(getApplicationContext(), "Ошибка удаления!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // Сохранение изменений по нажатию кнопки "Сохранить".
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String newTitle = title.getText().toString();
                        String newDescription = description.getText().toString();
                        String newDate = date.getText().toString();

                        // Передаем обновленные данные в БД.
                        if ((!newTitle.equals("")) && (!newDate.equals(""))) {

                            dataSnapshot.getRef().child("title").setValue(newTitle);
                            dataSnapshot.getRef().child("description").setValue(newDescription);
                            dataSnapshot.getRef().child("date").setValue(newDate);
                            dataSnapshot.getRef().child("key").setValue(key); // Не забываем передать ключ.

                            // Возвращаемся на главный экран.
                            Intent intent = new Intent(EditTaskActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {

                            // Показываем уведомление об ошибке.
                            Toast toast = Toast.makeText(getApplicationContext(), "Заполните поля!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, -670);
                            toast.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}