package com.codingblocks.locationtrail;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LocationTrailActivity extends AppCompatActivity {
    ArrayList<LatLng> trail = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_trail);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

                LocationRequest request = new LocationRequest().setInterval(2000).setFastestInterval(2000)
                        .setSmallestDisplacement(1f)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                fusedLocationProviderClient.requestLocationUpdates(request,new LocationCallback(){

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        for(Location location: locationResult.getLocations()){
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            trail.add(latLng);

                        }
                    }
                },Looper.myLooper());
            }
        } else
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 999);
    }

}
