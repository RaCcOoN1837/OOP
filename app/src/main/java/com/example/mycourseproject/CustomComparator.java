package com.example.mycourseproject;

import java.util.Comparator;

public class CustomComparator implements Comparator<MyTask> {

    @Override
    public int compare(MyTask o1, MyTask o2) {

        if (o1.getId() > o2.getId()) {
            return 1;
        } else if (o1.getId() < o2.getId()) {
            return -1;
        } else {
            return 0;
        }
    }
}