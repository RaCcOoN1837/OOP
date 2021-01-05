package com.example.mycourseproject.Model.Storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mycourseproject.Model.MyTask;

import java.util.List;

public class TaskDBStorage implements TaskStorage {

    private DBHelper helper;

    @Override
    public void insertTask(Context context, MyTask myTask) {

        this.helper = new DBHelper(context);
        helper.insertTask(myTask);
    }

    @Override
    public void updateTask(Context context, MyTask myTask, long oldID) {

        this.helper = new DBHelper(context);
        helper.updateTask(myTask, oldID);
    }

    @Override
    public void updateStatus(Context context, MyTask myTask, long oldID) {

        this.helper = new DBHelper(context);
        helper.updateStatus(myTask, oldID);
    }

    @Override
    public void deleteTask(Context context, MyTask myTask) {

        this.helper = new DBHelper(context);
        helper.deleteTask(myTask);
    }

    @Override
    public void clearDataBase(Context context) {

        this.helper = new DBHelper(context);
        helper.clearDataBase();
    }

    @Override
    public MyTask getTask(Context context, long ID) {

        this.helper = new DBHelper(context);
        return helper.getTask(ID);
    }

    @Override
    public List<MyTask> getAllTasks(Context context) {

        this.helper = new DBHelper(context);
        return helper.getAllTasks();
    }
}
