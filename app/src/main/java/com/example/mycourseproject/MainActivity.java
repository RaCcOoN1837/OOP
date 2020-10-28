package com.example.mycourseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    RecyclerView tasks; // Прокручиваемый список.
    ArrayList<MyTask> list; // Служит источником данных для RecyclerView.
    TaskAdapter taskAdapter; // Адаптер. (Предназначен для отображения данных из list в RecyclerView)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Подгружаем главный экран.

        // Связываем наши компоненты с XML файлом.
        this.tvTitle = findViewById(R.id.tvTitle);
        this.tvEnd = findViewById(R.id.tvEnd);
        this.btnAddTask = findViewById(R.id.btnAddTask);
        this.tasks = findViewById(R.id.tasks);

        // Открываем форму добавления задания по нажатию кнопки "Создать".
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(intent);
            }
        });

        // Работа с данными.
        this.list = new ArrayList<>();
        this.tasks.setLayoutManager(new LinearLayoutManager(this));

        // Получение данных из БД.

        // Ссылка для работы с базой данных.
        this.reference = FirebaseDatabase.getInstance().getReference().child("TaskBox");
        this.reference.addValueEventListener(new ValueEventListener() {

            // Получаем данные из БД и добавляем
            // их в list для отображения в RecyclerView.
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    MyTask task = dataSnapshot1.getValue(MyTask.class); // Считываем задание из БД
                    list.add(task); // Добавляем задание в список.
                }
                taskAdapter = new TaskAdapter(MainActivity.this, list); // Создаем адаптер.
                tasks.setAdapter(taskAdapter); // Устанавливаем адаптер для нашего RecyclerView.
                taskAdapter.notifyDataSetChanged(); // Уведомляем об изменениях.
            }

            // Отображение ошибок.
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), "Нет данных", Toast.LENGTH_SHORT).show();
            }
        });
    }
}