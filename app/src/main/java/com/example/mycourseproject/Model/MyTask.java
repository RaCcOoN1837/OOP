package com.example.mycourseproject.Model;

/*
    Класс "Задание".
 */
public class MyTask implements Comparable<MyTask> {

    // Поля.
    private String title;
    private String description;
    private long date;
    private boolean isDone;
    private long id;

    public MyTask() {
    }

    public MyTask(String title, String description, long date, boolean isDone, long id) {
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        this.isDone = done;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyTask myTask = (MyTask) o;

        if (date != myTask.date) return false;
        if (isDone != myTask.isDone) return false;
        if (id != myTask.id) return false;
        if (title != null ? !title.equals(myTask.title) : myTask.title != null) return false;
        return description != null ? description.equals(myTask.description) : myTask.description == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (isDone ? 1 : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public int compareTo(MyTask o) {
        if (this.getId() > o.getId()) {
            return 1;
        } else if (this.getId() < o.getId()) {
            return -1;
        } else {
            return 0;
        }
    }
}
