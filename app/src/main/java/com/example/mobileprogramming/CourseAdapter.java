package com.example.mobileprogramming;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter< CourseAdapter.ViewHolder> {

    ArrayList<Course> courses = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public CourseAdapter(Context context, ArrayList<Course> courses) {
        this.courses = courses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.course_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.teacherName.setText(courses.get(i).getTeacherName());
        viewHolder.courseName.setText(courses.get(i).getCourseName());
        viewHolder.agno.setText(courses.get(i).getAgno());
        viewHolder.linearLayout.setTag(viewHolder);

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView courseName, teacherName, agno;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            teacherName = (TextView) itemView.findViewById(R.id.teacher);
            agno = (TextView) itemView.findViewById(R.id.agno);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_course);
        }
    }


}
