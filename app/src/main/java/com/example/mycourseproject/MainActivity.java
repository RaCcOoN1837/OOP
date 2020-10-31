package com.example.mycourseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
    Главный экран.
 */
public class MainActivity extends AppCompatActivity {

    // Инифиализируем наши компоненты.
    TextView tvTitle, tvEnd;
    LottieAnimationView btnAddTask;
    DatabaseReference reference; // Для работы с базой данных.
    RecyclerView recyclerView; // Прокручиваемый список.
    ArrayList<MyTask> list = new ArrayList<>(); // Служит источником данных для RecyclerView.
    TaskAdapter taskAdapter; // Адаптер. (Предназначен для отображения данных из list в RecyclerView)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Подгружаем главный экран.

        // Связываем наши компоненты с XML файлом.
        this.tvTitle = findViewById(R.id.tvTitle);
        this.tvEnd = findViewById(R.id.tvEnd);
        this.btnAddTask = findViewById(R.id.btnAddTask);
        this.recyclerView = findViewById(R.id.tasks);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(MainActivity.this, list); // Создаем адаптер.
        recyclerView.setAdapter(taskAdapter); // Устанавливаем адаптер для нашего RecyclerView.

        // Открываем форму добавления задания по нажатию кнопки "Создать".
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(intent);
            }
        });

        list.add(new MyTask("Тестовое задание 0", "Описание тестового задания", true, "0"));
        list.add(new MyTask("Тестовое задание 1", "Описание тестового задания", false, "1"));
        list.add(new MyTask("Тестовое задание 2", "Описание тестового задания", false, "2"));
        list.add(new MyTask("Тестовое задание 3", "Описание тестового задания", false, "3"));
        list.add(new MyTask("Тестовое задание 4", "Описание тестового задания", false, "4"));
        list.add(new MyTask("Тестовое задание 5", "Описание тестового задания", false, "5"));
        list.add(new MyTask("Тестовое задание 6", "Описание тестового задания", false, "6"));
        list.add(new MyTask("Тестовое задание 7", "Описание тестового задания", false, "7"));
        list.add(new MyTask("Тестовое задание 8", "Описание тестового задания", false, "8"));
        list.add(new MyTask("Тестовое задание 9", "Описание тестового задания", false, "9"));

        taskAdapter.notifyDataSetChanged(); // Уведомляем об изменениях.
    }
}