package com.example.mobileprogramming;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseList extends AppCompatActivity {

    RecyclerView recyclerView_course;
    ArrayList<Course> courses = new ArrayList<>();
    Context context = this;
    String personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        setTitle("Course List");
        loadCourse();
        recyclerView_course = findViewById(R.id.recycler_course);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);

        recyclerView_course.setLayoutManager(linearLayoutManager);

        CourseAdapter courseAdapter = new CourseAdapter(context, courses);
        recyclerView_course.setAdapter(courseAdapter);

    }

    public void loadCourse(){
        Intent intent = getIntent();
        personId = intent.getStringExtra("PersonalId");
        Course_DB course_db = new Course_DB(context);

        ArrayList<HashMap<String, String>> courseList = course_db.getAllCourse(personId);
        Log.i("Buraya Geliyor","1");

        for(int i= 0; i < courseList.size(); i++){
            Log.i("Buraya Geliyor","2");
            String coursename = courseList.get(i).get("Course_Name");
            String teachername = courseList.get(i).get("Teachner_Name");
            String harnotu = courseList.get(i).get("Harf_Notu");
            courses.add(new Course(coursename, teachername, harnotu));
        }



    }


}
