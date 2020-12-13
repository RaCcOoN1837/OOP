package com.example.mycourseproject.Model;

public class MyConstants {

    //Задаем названия столбцов таблицы.
    public static final String DB_NAME = "TASK_DATABASE.db";
    public static final String TABLE_NAME = "TASK_TABLE";
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String DATE = "DATE";
    public static final String DONE = "DONE";
    public static final int DB_VERSION = 1;
    /* Версия нужна для фиксации изменений в структуре бд.
       Например при изменении или добавлении колонны необходимо
       присвоить номеру версии другое значение*/

    // Строковая константа создания структуры таблицы.
    public static final String CREATE_TABLE_STRUCTURE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY, " +
                    TITLE + " TEXT, " +
                    DESCRIPTION + " TEXT, " +
                    DATE + " INTEGER, " +
                    DONE + " INTEGER" + ");";

    // Строковая константа удаления структуры таблицы.
    public static final String DELETE_TABLE_STRUCTURE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
