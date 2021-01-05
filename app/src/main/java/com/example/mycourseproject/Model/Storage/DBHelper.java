package com.example.mycourseproject.Model.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mycourseproject.Model.MyTask;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    public DBHelper(@Nullable Context context) {
        super(context, MyConstants.DATABASE_NAME, null, MyConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Передаем SQL-команду на создание таблицы.
        db.execSQL(MyConstants.CREATE_TABLE_STRUCTURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Передаем SQL-команду на удаление.
        db.execSQL(MyConstants.DELETE_TABLE_STRUCTURE);
        onCreate(db);
    }

    public void insertTask(MyTask myTask){
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyConstants.ID, myTask.getId());
        contentValues.put(MyConstants.TITLE, myTask.getTitle());
        contentValues.put(MyConstants.DESCRIPTION, myTask.getDescription());
        contentValues.put(MyConstants.DATE, myTask.getDate());
        contentValues.put(MyConstants.DONE, myTask.isDone());

        database.insert(MyConstants.TABLE_NAME , null , contentValues);
    }

    public void updateTask(MyTask myTask, long oldID) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyConstants.ID, myTask.getId());
        contentValues.put(MyConstants.TITLE, myTask.getTitle());
        contentValues.put(MyConstants.DESCRIPTION, myTask.getDescription());
        contentValues.put(MyConstants.DATE, myTask.getDate());
        contentValues.put(MyConstants.DONE, myTask.isDone());

        database.update(MyConstants.TABLE_NAME , contentValues , "ID = ?" , new String[]{String.valueOf(oldID)});
    }

    public void updateStatus(MyTask myTask, long oldID){
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyConstants.DONE, myTask.isDone());
        contentValues.put(MyConstants.ID, myTask.getId());
        database.update(MyConstants.TABLE_NAME , contentValues , "ID = ?" , new String[]{String.valueOf(oldID)});
    }

    public void deleteTask(MyTask myTask) {
        database = this.getWritableDatabase();
        database.delete(MyConstants.TABLE_NAME , "ID=?" , new String[]{String.valueOf(myTask.getId())});
    }

    public void clearDataBase() {
        database = this.getWritableDatabase();
        database.delete(MyConstants.TABLE_NAME , null, null);
    }

    public MyTask getTask(long ID) {
        database = this.getWritableDatabase();
        Cursor cursor = null;
        MyTask myTask = new MyTask();

        database.beginTransaction();
        try {
            // Этот запрос вернет 1 строку таблицы с указанным ID.
            cursor = database.query(MyConstants.TABLE_NAME , null , "ID = ?", new String[] { String.valueOf(ID) } , null , null , null);
            if (cursor !=null){
                if (cursor.moveToFirst()){
                    do {
                        myTask.setId(cursor.getLong(cursor.getColumnIndex(MyConstants.ID)));
                        myTask.setTitle(cursor.getString(cursor.getColumnIndex(MyConstants.TITLE)));
                        myTask.setDescription(cursor.getString(cursor.getColumnIndex(MyConstants.DESCRIPTION)));
                        myTask.setDate(cursor.getLong(cursor.getColumnIndex(MyConstants.DATE)));
                        long status = cursor.getLong(cursor.getColumnIndex(MyConstants.DONE));
                        if (status == 1) myTask.setDone(true);
                        else myTask.setDone(false);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            database.endTransaction();
            cursor.close();
        }
        return myTask;
    }

    public List<MyTask> getAllTasks(){

        database = this.getWritableDatabase();
        Cursor cursor = null;
        List<MyTask> taskList = new ArrayList<>();

        database.beginTransaction();
        try {
            // Этот запрос вернет все строки таблицы.
            cursor = database.query(MyConstants.TABLE_NAME , null , null , null , null , null , null);
            if (cursor !=null){
                if (cursor.moveToFirst()){
                    do {
                        MyTask myTask = new MyTask();

                        myTask.setId(cursor.getLong(cursor.getColumnIndex(MyConstants.ID)));
                        myTask.setTitle(cursor.getString(cursor.getColumnIndex(MyConstants.TITLE)));
                        myTask.setDescription(cursor.getString(cursor.getColumnIndex(MyConstants.DESCRIPTION)));
                        myTask.setDate(cursor.getLong(cursor.getColumnIndex(MyConstants.DATE)));
                        long status = cursor.getLong(cursor.getColumnIndex(MyConstants.DONE));
                        if (status == 1) myTask.setDone(true);
                        else myTask.setDone(false);

                        taskList.add(myTask);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            database.endTransaction();
            cursor.close();
        }
        return taskList;
    }
}
