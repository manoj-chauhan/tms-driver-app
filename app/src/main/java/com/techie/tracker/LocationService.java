package com.techie.tracker;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class LocationService extends IntentService {

    private FusedLocationProviderClient fusedLocationClient;


    public LocationService() {
        super("Tracker");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        File[] drive = this.getApplicationContext().getExternalFilesDirs(null);
        System.out.println("SDCARDS: " + drive[0]);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        File locationFile = new File(drive[0].toString() + "locations.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(locationFile);

            AtomicReference<String> prevLoc = new AtomicReference<>("");
        while(true) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("####################No permission################33");
                return;
            }
            final FileWriter writerRef = writer;
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener( (OnSuccessListener<Location>) location -> {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            System.out.println("Current Location:::::::: " + location);
                            try {
                                String currentLoc = location.getLatitude() + ":" + location.getLongitude() + "\n";
//                                if(!currentLoc.equals(prevLoc.get())) {
                                    writerRef.write(currentLoc);
//                                }
                                prevLoc.set(currentLoc);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
