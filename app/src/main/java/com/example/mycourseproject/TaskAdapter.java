package com.example.mycourseproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

/*
    Экран редактирования задания.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTask> tasks;

    public TaskAdapter(Context context, ArrayList<MyTask> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    // Класс единичного элемента RecyclerView.
    class MyViewHolder extends RecyclerView.ViewHolder {

        // Инициализируем наши компоненты.
        TextView tvTaskTitle, tvTaskDescription,tvTaskDate, key;
        LinearLayout clickArea;
        LottieAnimationView checkbox;
        boolean isChecked = false;
        int viewHolderIndex;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Связываем наши компоненты с XML файлом.
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskDescription = itemView.findViewById(R.id.tvTaskDescription);
            tvTaskDate =  itemView.findViewById(R.id.tvTaskDate);
            clickArea = itemView.findViewById(R.id.clickArea);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }

    // Необходимые методы адаптера.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {

        myViewHolder.tvTaskTitle.setText(this.tasks.get(position).getTitle());
        myViewHolder.tvTaskDescription.setText(this.tasks.get(position).getDescription());
        myViewHolder.tvTaskDate.setText(this.tasks.get(position).getDate());

        // Получаем параметры изменяемого задания.
        final String currentTitle = this.tasks.get(position).getTitle();
        final String currentDescription = this.tasks.get(position).getDescription();
        final String currentDate = this.tasks.get(position).getDate();
        final String currentKey = this.tasks.get(position).getKey();

        // Открываем форму редактирования задания по нажатию на область текста.
        myViewHolder.clickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("title", currentTitle);
                intent.putExtra("description", currentDescription);
                intent.putExtra("date", currentDate);
                intent.putExtra("key", currentKey);
                context.startActivity(intent);
            }
        });

        // Анимация галочки при нажатии.
        myViewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myViewHolder.isChecked) {

                    myViewHolder.checkbox.setSpeed(-1);
                    myViewHolder.checkbox.playAnimation();
                    myViewHolder.isChecked = false;
                } else {

                    myViewHolder.checkbox.setSpeed(1);
                    myViewHolder.checkbox.playAnimation();
                    myViewHolder.isChecked = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
