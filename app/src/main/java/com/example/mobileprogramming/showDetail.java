package com.example.mobileprogramming;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;

public class showDetail extends AppCompatActivity {

    private EditText name, surname, phoneNum, address;
    private Button edit, cancel;
    private TextView age, bDate, personalId;
    private String personId;
    private HashMap<String,String> person;
    private ImageView image;
    private String encodedString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        setTitle("Edit Contact");
        Intent intent = getIntent();
        personId = intent.getStringExtra("PersonalId");
        Person_DB db = new Person_DB(getApplicationContext());
        person = db.getPerson(personId);
        init();

    }

    private void init(){
        name = (EditText) findViewById(R.id.showDetailName);
        surname = (EditText) findViewById(R.id.showDetailSurname);
        phoneNum = (EditText) findViewById(R.id.showDetailPhoneNumber);
        address = (EditText) findViewById(R.id.ShowDetailAddress);
        personalId = (TextView) findViewById(R.id.showDetailpersonID);
        bDate = (TextView) findViewById(R.id.showDetailBDate);
        age = (TextView) findViewById(R.id.age);
        image = (ImageView) findViewById(R.id.showDetailPic);
        edit = (Button) findViewById(R.id.edit);
        cancel = (Button) findViewById(R.id.delete);

        name.setText(person.get("Name"));
        surname.setText(person.get("Surname"));
        phoneNum.setText(person.get("Phone_Num"));
        address.setText(person.get("Address"));
        personalId.setText(person.get("ID"));
        bDate.setText(person.get("BDate"));
        age.setText(person.get("Age"));


        encodedString = person.get("ImageSrc");
        if(encodedString.equals("None")){
            image.setImageResource(R.mipmap.profil_icon);
        }
        else{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            image.setImageBitmap(bitmap);
        }



    }


    public boolean inputControl(EditText v, String defaultInput){
        String message = "Please Enter " + defaultInput + "!";
        if(v.getText().toString().equals("")){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void editInformation(View v){

        if(inputControl(name, getString(R.string.name_text)) &&
                inputControl(surname, getString(R.string.surname_text)) &&
                inputControl(phoneNum, getString(R.string.phone_text)) &&
                inputControl(address, getString(R.string.address_text))
                 ){

            Toast.makeText(this, "Personal Information Has Been Changed Successfully.", Toast.LENGTH_LONG).show();

            String id, personName, personSurname, phoneNumber, birthDate, personAddress;
            int  person_age;

            person_age = Integer.valueOf(age.getText().toString());
            id = personalId.getText().toString();
            personName = name.getText().toString();
            personSurname = surname.getText().toString();
            phoneNumber = phoneNum.getText().toString();
            personAddress = address.getText().toString();
            birthDate = bDate.getText().toString();
            Person_DB db = new Person_DB(getApplicationContext());



            db.updatePerson(id, personName, personSurname,person_age, phoneNumber, personAddress, birthDate, encodedString);




            Intent intent = new Intent(this, PersonList.class);
            startActivity(intent);

        }


    }

    public void deletePerson(View v){

        Person_DB db = new Person_DB(getApplicationContext());
        db.deletePerson(personId);

        Intent intent = new Intent(this, PersonList.class);
        startActivity(intent);

    }




}
