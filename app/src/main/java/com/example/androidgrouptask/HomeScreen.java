package com.example.androidgrouptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity {

    String login;
    String password;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Bundle bundle = getIntent().getExtras();
        userID = bundle.get("userID").toString();
    }

    public void showRouteScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), routeSelection.class);
        intent.putExtra("userID", userID);
        startActivity(intent);


        startActivity(new Intent(getApplicationContext(), routeSelection.class));
    }

    public void logout (View view){
        Intent i=new Intent(this, MainActivity.class);
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(),"You have been logged out",Toast.LENGTH_SHORT).show();
        startActivity(i);
        finish();

    }

    public void showProgress(View view) {

        Intent intent = new Intent(getApplicationContext(), progressActivity.class);
        startActivity(intent);
    }
}
