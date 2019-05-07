package com.example.mobileprogramming;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import android.app.DatePickerDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {



    private Context context = this;
    private Button addPerson, listPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.home_page);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Person_DB db = new Person_DB(getApplicationContext());


    }

    public void init(){

        addPerson = (Button)findViewById(R.id.newPerson);
        listPersons = (Button)findViewById(R.id.personList);

    }

    public void newPerson(View v){

        Intent intent = new Intent(this, NewPerson.class);
        startActivity(intent);

    }

    public void listOfPerson(View v){
        Intent intent = new Intent(this, PersonList.class);
        startActivity(intent);
    }



}




