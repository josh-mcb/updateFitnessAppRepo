package com.example.androidgrouptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class routeSelection extends AppCompatActivity implements OnItemSelectedListener {

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Bundle bundle = getIntent().getExtras();
        //userID = bundle.get("userID").toString();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        userID = currentFirebaseUser.getUid();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);

        //Spinner
        final Spinner dropdown = (Spinner) findViewById(R.id.spinner);
        Button showRoute = (Button)findViewById(R.id.showRouteButton);

        //Spinner click listener
        dropdown.setOnItemSelectedListener(this);

        String[] routes = new String[]{"Select Route", "4km City Run", "8km St Columbs Park", "Quay Run"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, routes);

        dropdown.setAdapter(adapter);

        String route = dropdown.getSelectedItem().toString();

        //Button on click
        showRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(dropdown.getSelectedItem()) != "Select Route") {
                    Intent intent = new Intent(routeSelection.this, MapsActivity.class);
                    intent.putExtra("data", String.valueOf(dropdown.getSelectedItem()));
                    intent.putExtra("UserID", userID);
                    onPause();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
