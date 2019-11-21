package com.example.androidgrouptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class routeSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);
    }

    public void showMapActivity(View view) {


        startActivity(new Intent(getApplicationContext(), MapsActivity.class));

    }
}
