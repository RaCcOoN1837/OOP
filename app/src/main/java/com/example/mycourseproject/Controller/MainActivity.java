package com.example.mycourseproject.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;


import com.example.mycourseproject.Model.Storage.DBHelper;
import com.example.mycourseproject.Model.MyTask;
import com.example.mycourseproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    Главный экран.
 */
public class MainActivity extends AppCompatActivity {

    private List<MyTask> list = new ArrayList<>();
    private DBHelper helper;

    // Инифиализируем наши компоненты.
    private LottieAnimationView btnAddTask; // Анимированная чудо-кнопка.
    private RecyclerView recyclerView; // Прокручиваемый список.
    private TaskAdapter taskAdapter; // Адаптер. (Предназначен для отображения данных из list в RecyclerView)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Подгружаем главный экран.

        // Связываем наши компоненты с XML файлом.
        this.btnAddTask = findViewById(R.id.STARTbtnAddTask);
        this.recyclerView = findViewById(R.id.STARTtasks);

        this.helper = new DBHelper(this);
        this.taskAdapter = new TaskAdapter(this, helper.getAllTasks()); // Создаем адаптер.
        // Это дает определенный выигрыш в скорости разворачивания списка.
        this.recyclerView.setHasFixedSize(true);
        // Задаем раскладчик - в данном случае - по вертикали.
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(taskAdapter); // Устанавливаем адаптер для нашего RecyclerView.

        // Открываем форму добавления задания по нажатию кнопки "+".
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this.getApplicationContext(), NewTaskActivity.class));
            }
        });

        Collections.sort(list); // Сортируем список заданий.
        taskAdapter.notifyDataSetChanged(); // Уведомляем об изменениях.
    }

    @Override
    public void onBackPressed() {
        // "Парализуем" кнопку "Назад", так как все необходимые кнопки у нас имеются.
    }
}