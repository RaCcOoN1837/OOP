package com.example.mycourseproject.Controller;

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

import com.example.mycourseproject.Model.Storage.DBHelper;
import com.example.mycourseproject.Model.MyTask;
import com.example.mycourseproject.Model.Storage.TaskDBStorage;
import com.example.mycourseproject.Model.Storage.TaskStorage;
import com.example.mycourseproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private Context context;
    private TaskStorage storage;
    private List<MyTask> list;

    public TaskAdapter(Context context, List<MyTask> list) {
        this.context = context;
        this.list = list;
    }

    // Класс единичного элемента RecyclerView.
    class MyViewHolder extends RecyclerView.ViewHolder {

        // Инициализируем наши компоненты.
        private TextView tvTitle, tvDescription, tvDate;
        private ConstraintLayout clickArea;
        private CheckBox checkbox;

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

        storage = new TaskDBStorage();

        final MyTask myTask = list.get(position);

        // Сохраняем текущий ID, так как он нам пригодится в дальнейшем.
        final long oldID = myTask.getId();

        // Напоняем наш ViewHolder контентом соответствующего задания.
        myViewHolder.tvTitle.setText(myTask.getTitle());
        myViewHolder.tvDescription.setText(myTask.getDescription());
        myViewHolder.tvDate.setText(new SimpleDateFormat("MMM dd").format(myTask.getDate()));

        // Активируем галку, если задание числится как выполненное.
        if (myTask.isDone()) {
            myViewHolder.checkbox.toggle();
        }

        // Открываем форму редактирования задания по нажатию на область текста.
        myViewHolder.clickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Редактировать разрешено только невыполненные задания.
                if (!myTask.isDone()) {

                    // Открываем activity для редактирования задания.
                    // Не забываем впридачу положить ему ID задания.
                    Intent intent = new Intent(context, EditTaskActivity.class);
                    intent.putExtra("id", myTask.getId());
                    context.startActivity(intent);
                }
            }
        });

        myViewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Меняем статус задания в зависимости от статуса галки его ViewHolder'a.
                if (isChecked) {
                    myTask.setDone(true);
                } else {
                    myTask.setDone(false);
                }

                /*
                    Редактируем ID задания в зависимости от его статуса
                    и обновляем данные в БД.
                    (Нужно для корректной сортировки заданий в списке)
                 */
                if (myTask.isDone()) {
                    myTask.setId(myTask.getId() + 1000000000000L);
                    storage.updateStatus(context, myTask, oldID);

                } else if (!myTask.isDone()) {
                    myTask.setId(myTask.getId() - 1000000000000L);
                    storage.updateStatus(context, myTask, oldID);
                }

                // Обновляем наше активити для отображения актуальных данных.
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
