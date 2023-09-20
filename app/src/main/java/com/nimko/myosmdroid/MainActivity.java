package com.nimko.myosmdroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.nimko.myosmdroid.databinding.ActivityMainBinding;
import com.nimko.myosmdroid.utils.MapUtils;
import com.nimko.myosmdroid.utils.MapUtilsImpl;
import com.nimko.myosmdroid.utils.MarkImg;
import com.nimko.myosmdroid.utils.PermissionUtils;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MapView map;
    private  MapUtils mapUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        binding.button.setOnClickListener(v -> {
            Log.d("BUTTON", "overlays size: "+map.getOverlays().size());
            if(map.getOverlays().size() > 1) {
                GeoPoint myLocation = mapUtils.getMyLocation();
                mapUtils.deleteLastRout();
                mapUtils.buildRout(myLocation, MapUtilsImpl.getRandomPoint(myLocation));
            } else {
                Toast.makeText(MainActivity.this, R.string.wait, Toast.LENGTH_SHORT).show();
            }
        });

        Intent listIntent = new Intent(this, ListActivity.class);
        binding.listButton.setOnClickListener(view -> {
            Log.d("CLICK","ListActivity");
            startActivity(listIntent);
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
        Log.d("PERMISSIONS", "Code:"+ requestCode +
                " Perm:" + Arrays.toString(permissions) +
                " grant:" + Arrays.toString(grantResults));
    }

    @SuppressLint("CheckResult")
    private void init(){
        PermissionUtils.checkPermission(this);
        map = binding.mapview;
        mapUtils = new MapUtilsImpl(map, this);
        GeoPoint myLocation = mapUtils.getMyLocation();
        mapUtils.addMarker(myLocation, MarkImg.I_AM);
        mapUtils.buildRout(myLocation, MapUtilsImpl.getRandomPoint(myLocation));
    }


}
