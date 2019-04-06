package com.example.mobileprogramming;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    ArrayList<Person> allPerson = new ArrayList<>();
    LayoutInflater layoutInflater;




    public MyAdapter(Context context, ArrayList<Person> allPerson) {
        this.context = context;
        this.allPerson = allPerson;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name_surname.setText( allPerson.get(position).getPersonNameSurname());

        String encodedString = allPerson.get(position).getImgSrc();
        if(!encodedString.equals("None")){
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            holder.foto.setImageBitmap(bitmap);
        }
        else{
            holder.foto.setImageResource(R.mipmap.profil_icon);
        }
        /*
        //holder.callbtn.setImageResource();


        */

        holder.dersler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCourse(allPerson.get(position).getPersonId());
            }
        });

        holder.callbtn.setImageResource(allPerson.get(position).getCallBtnSrc());
        holder.linearLayout.setTag(holder);
        holder.callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(allPerson.get(position).getPersonId());
            }
        });

        holder.name_surname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail(allPerson.get(position).getPersonId());
            }
        });


    }


    public void showCourse(String personalId){

        Intent intent = new Intent(context, CourseList.class);
        intent.putExtra("PersonalId", personalId);
        context.startActivity(intent);

    }

    public void showDetail(String personalId){

        Intent intent = new Intent(context, showDetail.class);
        intent.putExtra("PersonalId", personalId);
        context.startActivity(intent);

    }

    public void call(String personalId){
        HashMap<String,String> person;
        Person_DB db = new Person_DB(context);
        person = db.getPerson(personalId);
        String tel_no = person.get("Phone_Num");

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + tel_no));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = LayoutInflater.from(context);
        View row = layoutInflater.inflate(R.layout.row_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(row);
        return vh;
    }

    @Override
    public int getItemCount() {
        return allPerson.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView foto, dersler;
        ImageButton callbtn;
        TextView name_surname;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dersler = itemView.findViewById(R.id.courseView);
            foto = itemView.findViewById(R.id.imageview);
            callbtn = itemView.findViewById(R.id.callButton);
            name_surname = itemView.findViewById(R.id.nameAndSurname);
            linearLayout = itemView.findViewById(R.id.linear);


        }
    }
}
