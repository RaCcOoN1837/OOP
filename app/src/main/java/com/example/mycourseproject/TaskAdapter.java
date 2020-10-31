package com.example.mycourseproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        TextView tvTaskTitle, tvTaskDescription, key;
        LinearLayout clickArea;
        CheckBox checkbox;
        boolean isDone = false;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Связываем наши компоненты с XML файлом.
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskDescription = itemView.findViewById(R.id.tvTaskDescription);
            clickArea = itemView.findViewById(R.id.clickArea);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {

        myViewHolder.tvTaskTitle.setText(this.tasks.get(position).getTitle());
        myViewHolder.tvTaskDescription.setText(this.tasks.get(position).getDescription());

        // Получаем параметры изменяемого задания.
        final String currentTitle = this.tasks.get(position).getTitle();
        final String currentDescription = this.tasks.get(position).getDescription();
        final String currentKey = this.tasks.get(position).getId();

        // Открываем форму редактирования задания по нажатию на область текста.
        myViewHolder.clickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("title", currentTitle);
                intent.putExtra("description", currentDescription);
                intent.putExtra("key", currentKey);
                context.startActivity(intent);
            }
        });

        myViewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myViewHolder.isDone = true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
