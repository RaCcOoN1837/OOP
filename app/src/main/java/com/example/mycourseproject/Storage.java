package com.example.mycourseproject;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final Storage INST = new Storage();

    private final List<MyTask> items = new ArrayList<>();

    private Storage() {
    }

    public static Storage getStorage() {
        return INST;
    }

    public void add(MyTask myTask) {
        this.items.add(myTask);
    }

    public void remove(MyTask myTask) {
        this.items.remove(myTask);
    }

    public List<MyTask> getAll() {
        return this.items;
    }

    public int size() {
        return this.items.size();
    }

    public MyTask get(int index) {
        return this.items.get(index);
    }
}
