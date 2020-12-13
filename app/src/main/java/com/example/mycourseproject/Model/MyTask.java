package com.example.mycourseproject.Model;

/*
    Класс "Задание".
 */
public class MyTask implements Comparable<MyTask> {

    // Поля.
    private String title;
    private String description;
    private long date;
    private long done;
    private long id;

    public MyTask() {
    }

    public MyTask(String title, String description, long date, long done, long id) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.done = done;
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

    public long getDone() {
        return done;
    }

    public void setDone(long done) {
        this.done = done;
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
        if (done != myTask.done) return false;
        if (id != myTask.id) return false;
        if (title != null ? !title.equals(myTask.title) : myTask.title != null) return false;
        return description != null ? description.equals(myTask.description) : myTask.description == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (int) (done ^ (done >>> 32));
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
