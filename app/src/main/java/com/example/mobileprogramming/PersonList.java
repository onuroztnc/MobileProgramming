package com.example.mobileprogramming;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PersonList extends AppCompatActivity {

    ArrayList<Person> allPerson = new ArrayList<>();
    RecyclerView recyclerView;
    Context context = this;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Contact List");
        loadAllPerson();
        setContentView(R.layout.activity_person_list);
        recyclerView = findViewById(R.id.recycler_id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        //
        myAdapter = new MyAdapter(context, allPerson);
        recyclerView.setAdapter(myAdapter);

    }



    public void loadAllPerson(){
        Person_DB db = new Person_DB(getApplicationContext());
        ArrayList<HashMap<String,String>> people = db.getAllPerson();
        String img_src, name_surname, id;

        for(int i = 0 ; i < people.size() ; i++){
            img_src = people.get(i).get("ImageSrc");
            name_surname = people.get(i).get("Name") + " "+people.get(i).get("Surname");
            id = people.get(i).get("ID");
            allPerson.add(new Person(id, img_src, R.mipmap.call_img_padding,name_surname));
        }

        Collections.sort(allPerson, Collections.reverseOrder());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
}
