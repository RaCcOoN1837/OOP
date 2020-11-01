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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
    Экран редактирования задания.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
//    ArrayList<MyTask> tasks;

//    public TaskAdapter(Context context, ArrayList<MyTask> tasks) {
    public TaskAdapter(Context context) {
        this.context = context;
//        this.tasks = tasks;
    }

    // Класс единичного элемента RecyclerView.
    class MyViewHolder extends RecyclerView.ViewHolder {

        // Инициализируем наши компоненты.
        TextView tvTitle, tvDescription, tvDate;
        ConstraintLayout clickArea;
        CheckBox checkbox;
        String key;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Связываем наши компоненты с XML файлом.
            tvTitle = itemView.findViewById(R.id.ELEMENTtvTitle);
            tvDescription = itemView.findViewById(R.id.ELEMENTtvDescription);
            tvDate = itemView.findViewById(R.id.ELEMENTtvDate);
            clickArea = itemView.findViewById(R.id.ELEMENTclickArea);
            checkbox = itemView.findViewById(R.id.ELEMENTcheckbox);
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
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

        final MyTask myTask = Storage.getStorage().get(position);

        myViewHolder.tvTitle.setText(myTask.getTitle());
        myViewHolder.tvDescription.setText(myTask.getDescription());
        myViewHolder.tvDate.setText(myTask.getDate());

        /*
            НЕ РАБОТАЕТ!!!
         */
        myViewHolder.checkbox.setActivated(true);

        // Получаем параметры изменяемого задания.
        final String currentTitle = myTask.getTitle();
        final String currentDescription = myTask.getDescription();
        final String currentDate = myTask.getDate();
        final boolean currentIsDone = myTask.isDone();
        final String currentId = myTask.getId();

        // Открываем форму редактирования задания по нажатию на область текста.
        myViewHolder.clickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("title", currentTitle);
                intent.putExtra("description", currentDescription);
                intent.putExtra("date", currentDate);
//                intent.putExtra("isDone", currentIsDone);
                intent.putExtra("id", currentId);

                // Передаем индекс задания в EditTaskActivity.
                intent.putExtra("position", position);
                context.startActivity(intent);

            }
        });

        myViewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                /*
                    РЕАЛИЗОВАТЬ!!!
                 */
            }
        });
    }

    @Override
    public int getItemCount() {
        return Storage.getStorage().size();
    }
}
