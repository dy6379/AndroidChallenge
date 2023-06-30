package com.busanit.androidchallenge;

public class Lesson {
    private String name; //강의명
    private String start_display; //시작날짜
    private String teachers; //강사명

    public Lesson(String name, String start_display, String teachers) {
        this.name = name;
        this.start_display = start_display;
        this.teachers = teachers;
    }

    public String getStart_display() {
        return start_display;
    }

    public void setStart_display(String start_display) {
        this.start_display = start_display;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
