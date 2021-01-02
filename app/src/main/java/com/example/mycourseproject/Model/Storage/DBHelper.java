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

    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, MyConstants.DB_NAME, null, MyConstants.DB_VERSION);
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

    /**
     * Метод для добавления задания в БД.
     */
    public void insertTask(MyTask myTask){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyConstants.ID, myTask.getId());
        contentValues.put(MyConstants.TITLE, myTask.getTitle());
        contentValues.put(MyConstants.DESCRIPTION, myTask.getDescription());
        contentValues.put(MyConstants.DATE, myTask.getDate());
        contentValues.put(MyConstants.DONE, myTask.getDone());

        db.insert(MyConstants.TABLE_NAME , null , contentValues);
    }

    /**
     * Метод для обновления задания в БД.
     */
    public void updateTask(MyTask myTask, long oldID) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyConstants.ID, myTask.getId());
        contentValues.put(MyConstants.TITLE, myTask.getTitle());
        contentValues.put(MyConstants.DESCRIPTION, myTask.getDescription());
        contentValues.put(MyConstants.DATE, myTask.getDate());
        contentValues.put(MyConstants.DONE, myTask.getDone());

        db.update(MyConstants.TABLE_NAME , contentValues , "ID = ?" , new String[]{String.valueOf(oldID)});
    }

    /**
     * Метод для обновления статуса задания в БД.
     */
    public void updateStatus(MyTask myTask, long oldID){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyConstants.DONE, myTask.getDone());
        contentValues.put(MyConstants.ID, myTask.getId());
        db.update(MyConstants.TABLE_NAME , contentValues , "ID = ?" , new String[]{String.valueOf(oldID)});
    }

    /**
     * Метод для удаления задания из БД.
     */
    public void deleteTask(MyTask myTask) {
        db = this.getWritableDatabase();
        db.delete(MyConstants.TABLE_NAME , "ID=?" , new String[]{String.valueOf(myTask.getId())});
    }

    /**
     * Метод для удаления всей БД.
     */
    public void clearDataBase() {
        db = this.getWritableDatabase();
        db.delete(MyConstants.TABLE_NAME , null, null);
    }

    /**
     * Метод для получения задания из БД.
     */
    public MyTask getTask(long id) {
        db = this.getWritableDatabase();
        Cursor cursor = null;
        MyTask myTask = new MyTask();

        db.beginTransaction();
        try {
            // Этот запрос вернет 1 строку таблицы с указанным ID.
            cursor = db.query(MyConstants.TABLE_NAME , null , "ID = ?", new String[] { String.valueOf(id) } , null , null , null);
            if (cursor !=null){
                if (cursor.moveToFirst()){
                    do {
                        myTask.setId(cursor.getLong(cursor.getColumnIndex(MyConstants.ID)));
                        myTask.setTitle(cursor.getString(cursor.getColumnIndex(MyConstants.TITLE)));
                        myTask.setDescription(cursor.getString(cursor.getColumnIndex(MyConstants.DESCRIPTION)));
                        myTask.setDate(cursor.getLong(cursor.getColumnIndex(MyConstants.DATE)));
                        myTask.setDone(cursor.getLong(cursor.getColumnIndex(MyConstants.DONE)));

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return myTask;
    }

    /**
     * Метод для считывания всех заданий из БД.
     */
    public List<MyTask> getAllTasks(){

        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<MyTask> taskList = new ArrayList<>();

        db.beginTransaction();
        try {
            // Этот запрос вернет все строки таблицы.
            cursor = db.query(MyConstants.TABLE_NAME , null , null , null , null , null , null);
            if (cursor !=null){
                if (cursor.moveToFirst()){
                    do {
                        MyTask myTask = new MyTask();

                        myTask.setId(cursor.getLong(cursor.getColumnIndex(MyConstants.ID)));
                        myTask.setTitle(cursor.getString(cursor.getColumnIndex(MyConstants.TITLE)));
                        myTask.setDescription(cursor.getString(cursor.getColumnIndex(MyConstants.DESCRIPTION)));
                        myTask.setDate(cursor.getLong(cursor.getColumnIndex(MyConstants.DATE)));
                        myTask.setDone(cursor.getLong(cursor.getColumnIndex(MyConstants.DONE)));

                        taskList.add(myTask);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return taskList;
    }
}
