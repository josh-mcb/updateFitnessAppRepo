package com.example.androidgrouptask;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.text.format.DateFormat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import androidx.annotation.NonNull;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    final private int REQUEST_COARSE_ACCESS = 123;
    boolean permissionGranted = false;
    LocationManager lm;
    LocationListener locationListener;

    Button startButton;
    Button endButton;
    Button scanButton;
    String startTime;
    String endTime;
    String userID;
    Date date1;
    Date date2;
    int minutes;
    String route;

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Toast.makeText(getBaseContext(),
                        "Current Location: Lat: " + location.getLatitude() +
                                " Lng: " + location.getLongitude(), Toast.LENGTH_LONG).show();
                LatLng p = new LatLng(location.getLatitude(), location.getLongitude());
                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                List<Address> addresses = null;
                String add = "";
                try {
                    addresses = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address address = addresses.get(0);

                    if (addresses.size() > 0) {
                        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++)
                            add += address.getAddressLine(i) + "\n";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMap.addMarker(new MarkerOptions()
                        .position(p)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        .title(add));
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // gets the firebase UserID from the login, passed through with intents
        Bundle bundle = getIntent().getExtras();
        userID = bundle.get("UserID").toString();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        startButton = findViewById(R.id.startBtn);
        endButton = findViewById(R.id.endBtn);
        scanButton = findViewById(R.id.scanBtn);

        endButton.setVisibility(View.INVISIBLE);
    }

    public void startRun(View view) {
        startButton.setVisibility(View.INVISIBLE);
        endButton.setVisibility(View.VISIBLE);

        //Sets start time
        startTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Toast.makeText(getApplicationContext(), "Start time: " + startTime, Toast.LENGTH_SHORT).show();
    }

    public void endRun(View view) {
        endButton.setVisibility(View.INVISIBLE);

        //Sets end time
        endTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());


        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            date1 = format.parse(startTime);
            date2 = format.parse(endTime);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        long difference = date2.getTime() - date1.getTime();

        minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);

        if (minutes < 0) {
            minutes += 1440;
        }

        String totalTime = Integer.toString(minutes);

        totalTime += " mins";

        Toast.makeText(getApplicationContext(), "Run Time: " + totalTime, Toast.LENGTH_SHORT).show();

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference mRef =  database.getReference().child("User").child(userID).push();
        mRef.child("route").setValue(route);
        mRef.child("time").setValue(totalTime);

    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Bundle bundle = getIntent().getExtras();
        route = bundle.get("data").toString();

        final DatabaseReference routeRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference pointRef = routeRef.child(route);



        pointRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Double startLatitude = dataSnapshot.child("StartLat").getValue(Double.class);
                Double startLongitude = dataSnapshot.child("StartLong").getValue(Double.class);

                Double endLatitude = dataSnapshot.child("EndLat").getValue(Double.class);
                Double endLongitude = dataSnapshot.child("EndLong").getValue(Double.class);

                LatLng startPoint = new LatLng(startLatitude,startLongitude);
                LatLng endPoint = new LatLng(endLatitude,endLongitude);

                mMap.addMarker(new MarkerOptions().position(startPoint).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("Start Point"));
                mMap.addMarker(new MarkerOptions().position(endPoint).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("End Point"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint,13F));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // use the LocationManager class to obtain location data
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            },REQUEST_COARSE_ACCESS);
            return;
        } else {
            permissionGranted = true;
        }
        if (permissionGranted) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try{
                    List<Address> addresses = geoCoder.getFromLocation(point.latitude,point.longitude,1);
                    Address address = addresses.get(0);
                    String add = "";
                    if (addresses.size() > 0) {
                        for (int i=0; i <= address.getMaxAddressLineIndex(); i++)
                            add += address.getAddressLine(i) + "\n";
                        LatLng p = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(p).title("Touched Location"));
                    }
                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_COARSE_ACCESS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            !=PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
                } else {
                    permissionGranted = false;
                } break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    @Override
    public void onPause () {
        super.onPause();

        //remove location listener
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },REQUEST_COARSE_ACCESS);
            return;
        } else {
            permissionGranted = true;
        }
        if (permissionGranted) {
            lm.removeUpdates(locationListener);
        }
    }


}

