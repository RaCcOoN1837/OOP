package com.example.mycourseproject.Model.Storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mycourseproject.Model.MyTask;

import java.util.List;

public interface TaskStorage {

    void insertTask(Context context, MyTask myTask);

    void updateTask(Context context, MyTask myTask, long oldID);

    void updateStatus(Context context, MyTask myTask, long oldID);

    void deleteTask(Context context, MyTask myTask);

    void clearDataBase(Context context);

    MyTask getTask(Context context, long ID);

    List<MyTask> getAllTasks(Context context);
}
