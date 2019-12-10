package com.example.androidgrouptask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class progressActivity extends AppCompatActivity {

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // getting currentUser for Query
        //Bundle bundle = getIntent().getExtras();
        //userID = bundle.get("userID").toString();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userID = currentFirebaseUser.getUid();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        final ListView myListView = findViewById(R.id.listView);

        // arraylist using routeTime class as an object
        final ArrayList<routeTimes> runningTimesList = new ArrayList<>();

        // database reference
        DatabaseReference progressRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference pointRef = progressRef.child("User").child(userID);

        // new instance of listAdapter class
        final listAdapterforProgress adapter = new listAdapterforProgress(this, R.layout.adapter_view_layout, runningTimesList);

        // sets adapter from listadapterforprogress class
        myListView.setAdapter(adapter);

        ValueEventListener routeValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for ( DataSnapshot routeDataSnapshot : dataSnapshot.getChildren() ) {

                    // new instance of the routeTimes class and assigning to snapshot to access getters
                    routeTimes route = routeDataSnapshot.getValue(routeTimes.class);

                    // creating new object and calling the getters into the constructor
                    runningTimesList.add(new routeTimes(route.getRoute(), route.getTime()));

                    Collections.reverse(runningTimesList);

                    // updates the adapter, and list
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
            // new listener
        pointRef.addListenerForSingleValueEvent(routeValueEventListener);
    }
}
