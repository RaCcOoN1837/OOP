package com.example.mycourseproject.View;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final Storage INST = new Storage();

    private final List<MyTask> tasks = new ArrayList<>();

    private Storage() {
    }

    public static Storage getStorage() {
        return INST;
    }

    public void add(MyTask myTask) {
        this.tasks.add(myTask);
    }

    public void remove(MyTask myTask) {
        this.tasks.remove(myTask);
    }

    public List<MyTask> getList() {
        return this.tasks;
    }

    public int size() {
        return this.tasks.size();
    }

    public MyTask get(int index) {
        return this.tasks.get(index);
    }
}
