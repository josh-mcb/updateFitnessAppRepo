package com.example.androidgrouptask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class progressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        ListView myListView = findViewById(R.id.listView);

        routeTimes john = new routeTimes("4km City Run","60 mins");
        routeTimes steve = new routeTimes("4km City Run","55 mins");

        ArrayList<routeTimes> runningTimesList = new ArrayList<>();

        runningTimesList.add(john);
        runningTimesList.add(steve);


        listAdapterforProgress adapter = new listAdapterforProgress(this, R.layout.adapter_view_layout, runningTimesList);

        myListView.setAdapter(adapter);



    }
}
