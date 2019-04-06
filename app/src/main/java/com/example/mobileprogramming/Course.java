package com.example.mobileprogramming;

public class Course {



    private String courseName, teacherName, agno;

    public Course(String courseName, String teacherName, String agno) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.agno = agno;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getAgno() {
        return agno;
    }

    public void setAgno(String agno) {
        this.agno = agno;
    }
}
