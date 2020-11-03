package com.example.mycourseproject;

/*
    Класс "Задание".
 */
public class MyTask {

    // Поля.
    private String title;
    private String description;
    private String date;
    private boolean isDone;
    private String id;

    public MyTask() {
    }

    public MyTask(String title, String description, String date, boolean isDone, String id) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.isDone = isDone;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyTask myTask = (MyTask) o;

        if (isDone != myTask.isDone) return false;
        if (title != null ? !title.equals(myTask.title) : myTask.title != null) return false;
        if (description != null ? !description.equals(myTask.description) : myTask.description != null)
            return false;
        if (date != null ? !date.equals(myTask.date) : myTask.date != null) return false;
        return id != null ? id.equals(myTask.id) : myTask.id == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (isDone ? 1 : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
