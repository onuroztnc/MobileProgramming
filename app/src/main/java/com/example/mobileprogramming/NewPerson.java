package com.example.mobileprogramming;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import java.time.Period;

public class NewPerson extends AppCompatActivity {


    private Button save, clear;
    private EditText name, surname, phoneNumber, address, personalId;
    private TextView bDate;
    private ImageView picture;
    private Calendar currentCalender;
    private int year, month, day;
    private Bitmap bitmap = null;
    private String denemeee = " ";

    private ArrayList<Course> allCourse;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int GALLERY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person);
        setTitle("New Contact");
        initCourseList();
        init();
    }

    private void init(){

        name = (EditText) findViewById(R.id.Name);
        surname = (EditText) findViewById(R.id.surName);
        personalId = (EditText) findViewById(R.id.personID);
        bDate = (TextView) findViewById(R.id.bDate);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        address = (EditText) findViewById(R.id.address);

        picture = (ImageView) findViewById(R.id.personPicture);

        save = (Button) findViewById(R.id.save);
        clear = (Button) findViewById(R.id.clear);

        currentCalender = Calendar.getInstance();
        year = currentCalender.get(Calendar.YEAR);
        month = currentCalender.get(Calendar.MONTH) + 1;
        day = currentCalender.get(Calendar.DAY_OF_MONTH);

    }

    private void initCourseList(){
        allCourse = new ArrayList<>();
        allCourse.add(new Course("Mobile Programming", "M.Amaç Güvensan", "AA"));
    }

    public void clickText(View v){

        String empyt = "";

        if(v == name){
            name.setText(empyt);
        }
        else if(v == surname){
            surname.setText(empyt);
        }
        else if(v == phoneNumber){
            phoneNumber.setText(empyt);
        }
        else if(v == address){
            address.setText(empyt);
        }
        else if(v == personalId){
            personalId.setText(empyt);
        }
        else if(v == bDate){
            String date = String.valueOf(day) + " / " + String.valueOf(month) + " / " + String.valueOf(year);
            bDate.setText(date);
            showDatePicker();
        }
    }

    public void showDatePicker(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,

                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int years, int months, int dayOfMonth) {

                        months += 1;
                        bDate.setText(dayOfMonth + "/" + months + "/" + years);
                        year = years;
                        month = months;
                        day = dayOfMonth;
                    }

        }, year, month-1, day);

        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "OK", datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "CANCEL", datePickerDialog);
        datePickerDialog.show();
    }

    public void loadPicture(View v){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Action");
        builder.setItems(R.array.LoadPicture, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0)
                    captureImage();
                if(which == 1)
                    pickFromGallery();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void pickFromGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }
    public void captureImage(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            picture.setImageBitmap(bitmap);


        }

        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();

            try {
               bitmap = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                picture.setImageBitmap(bitmap);
            }
            catch (Exception e){
                System.out.println(e);
            }

        }


    }

    public boolean inputControl(EditText v, String defaultInput){
        String message = "Please Enter " + defaultInput + "!";
        if(v.getText().toString().equals(defaultInput) || v.getText().toString().equals("")){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean bDateControl(){

        String default_text = getString(R.string.bDate_text);
        String message =  "Please Check " + default_text + "!";
        LocalDate bdate = LocalDate.of(year, month, day);
        LocalDate today = LocalDate.now();

        if(bDate.getText().toString().equals(default_text) || bdate.isAfter(today) || bdate.equals(today)){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    public void saveInformation(View v){

        if(inputControl(name, getString(R.string.name_text)) &&
                inputControl(surname, getString(R.string.surname_text)) &&
                inputControl(personalId, getString(R.string.personalID_text)) &&
                inputControl(phoneNumber, getString(R.string.phone_text)) &&
                inputControl(address, getString(R.string.address_text)) &&
                bDateControl() ){

            String id, personName, personSurname, birthDate, phoneNum, personAddress;
            int age;

            id = personalId.getText().toString();
            personName = name.getText().toString();
            personSurname = surname.getText().toString();
            birthDate = bDate.getText().toString();
            phoneNum = phoneNumber.getText().toString();
            personAddress = address.getText().toString();

            LocalDate date = LocalDate.of(year, month, day);
            LocalDate now  = LocalDate.now();
            Period p = Period.between(date, now);
            age = p.getYears();

            Person_DB db = new Person_DB(getApplicationContext());
            Course_DB course_db = new Course_DB(getApplicationContext());
            String temp = "None";
            if (bitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] b = stream.toByteArray();
                    temp = Base64.encodeToString(b, Base64.DEFAULT);

            }

            long flag2 = course_db.addCourse(id, allCourse.get(0).getCourseName(), allCourse.get(0).getTeacherName(),allCourse.get(0).getAgno());
            Log.i("Flag2", String.valueOf(flag2));
            long flag = db.addPerson(temp, id, personName, personSurname, age, birthDate, phoneNum, personAddress);
            if(flag>-1) {
                Toast.makeText(this, "Personal Information Successfully Saved.", Toast.LENGTH_LONG).show();
                /*Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                */
                super.onBackPressed();
            }
            else
                Toast.makeText(this, "Identity Number is already in use.", Toast.LENGTH_LONG).show();




        }


    }

    public void clearInformation(View v){

        name.setText(getString(R.string.name_text));
        surname.setText(getString(R.string.surname_text));
        personalId.setText(getString(R.string.personalID_text));
        bDate.setText(getString(R.string.bDate_text));
        phoneNumber.setText(getString(R.string.phone_text));
        address.setText(getString(R.string.address_text));

    }


}
