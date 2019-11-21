package com.example.androidgrouptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class routeSelection extends AppCompatActivity {

    Spinner dropdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dropdown = findViewById(R.id.spinner);

        Spinner dropdown = findViewById(R.id.spinner);

        String[] routes = new String[]{"4km City Run", "10km St Columbs Park", "42km Derry Marathon"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, routes);

        dropdown.setAdapter(adapter);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);
    }

    public void showMapActivity(View view) {


        startActivity(new Intent(getApplicationContext(), MapsActivity.class));

    }
}