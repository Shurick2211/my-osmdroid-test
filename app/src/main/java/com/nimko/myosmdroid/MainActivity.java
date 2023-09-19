package com.nimko.myosmdroid;

import static android.os.FileObserver.ACCESS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nimko.myosmdroid.databinding.ActivityMainBinding;
import com.nimko.myosmdroid.utils.MapUtils;
import com.nimko.myosmdroid.utils.MapUtilsImpl;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private static final double DEGREE_RADIUS = 0.5;
    private ActivityMainBinding binding;
    private MapView map;
    private  MapUtils mapUtils;
    private final static int REQUEST_PERMISSIONS_REQUEST_CODE = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "overlays size: "+map.getOverlays().size());
                if(map.getOverlays().size() > 1) {
                    GeoPoint myLocation = mapUtils.getMyLocation();
                    mapUtils.deleteLastRout();
                    mapUtils.buildRout(myLocation, getRandomPoint(myLocation));
                } else {
                    Toast.makeText(MainActivity.this, R.string.wait, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent listIntent = new Intent(this, ListActivity.class);
        binding.listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CLICK","ListActivity");
                startActivity(listIntent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void checkPermission() {
        String [] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET
        };

        for (String permission:permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                        this, new String[]{permission},
                        ACCESS
                );
            }
        }
    }
    @SuppressLint("CheckResult")
    private void init(){
        checkPermission();
        map = binding.mapview;
        mapUtils = new MapUtilsImpl(map, this);
        GeoPoint myLocation = mapUtils.getMyLocation();
        mapUtils.addMarker(myLocation);
        mapUtils.buildRout(myLocation, getRandomPoint(myLocation));

    }

    private GeoPoint getRandomPoint(GeoPoint myPoint){
        return new GeoPoint(
                getRandomValue(myPoint.getLatitude(), DEGREE_RADIUS),
                getRandomValue(myPoint.getLongitude(), DEGREE_RADIUS));
    }

    private Double getRandomValue(double value, double radius){
        Random random = new Random();
        if (random.nextBoolean())
            value += random.nextGaussian() * radius;
        else
            value -= random.nextGaussian() * radius;
        return value;
    }
}
