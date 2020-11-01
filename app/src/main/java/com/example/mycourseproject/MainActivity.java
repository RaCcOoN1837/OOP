package com.example.mycourseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;


import java.util.ArrayList;
import com.example.mycourseproject.Storage;

/*
    Главный экран.
 */
public class MainActivity extends AppCompatActivity {

    // Инифиализируем наши компоненты.
    LottieAnimationView btnAddTask;
    RecyclerView recyclerView; // Прокручиваемый список.
    TaskAdapter taskAdapter; // Адаптер. (Предназначен для отображения данных из list в RecyclerView)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Подгружаем главный экран.

        // Связываем наши компоненты с XML файлом.
        this.btnAddTask = findViewById(R.id.STARTbtnAddTask);
        this.taskAdapter = new TaskAdapter(this); // Создаем адаптер.
        this.recyclerView = findViewById(R.id.STARTtasks);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(taskAdapter); // Устанавливаем адаптер для нашего RecyclerView.

        // Открываем форму добавления задания по нажатию кнопки "Создать".
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), NewTaskActivity.class);
                startActivity(intent);
            }
        });

        Storage.getStorage().add(new MyTask("Сходить в Ленту", "Купить продукты", "Nov 04", true, "999"));
        taskAdapter.notifyDataSetChanged(); // Уведомляем об изменениях.
    }
}