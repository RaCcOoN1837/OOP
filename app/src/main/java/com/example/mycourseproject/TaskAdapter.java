package com.example.mycourseproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycourseproject.Activities.EditTaskActivity;
import com.example.mycourseproject.Activities.MainActivity;

import java.util.Collections;

/*
    Экран редактирования задания.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;

    public TaskAdapter(Context context) {
        this.context = context;
    }

    // Класс единичного элемента RecyclerView.
    class MyViewHolder extends RecyclerView.ViewHolder {

        // Инициализируем наши компоненты.
        TextView tvTitle, tvDescription, tvDate;
        ConstraintLayout clickArea;
        CheckBox checkbox;

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
        myViewHolder.tvDate.setText(myTask.getDate().toString());
        if (myTask.isDone()) {
            myViewHolder.checkbox.toggle();
        }

        // Открываем форму редактирования задания по нажатию на область текста.
        myViewHolder.clickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        myViewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myTask.setDone(isChecked);
                Collections.sort(Storage.getStorage().getList(), new CustomComparator());
            }
        });
    }

    @Override
    public int getItemCount() {
        return Storage.getStorage().size();
    }
}
