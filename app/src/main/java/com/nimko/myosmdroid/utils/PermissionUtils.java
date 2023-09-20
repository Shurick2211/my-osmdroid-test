package com.nimko.myosmdroid.utils;

import static android.os.FileObserver.ACCESS;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class PermissionUtils {


    public static void checkPermission(Activity activity) {
        String [] permissions = new String[]{
               // Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
                ,Manifest.permission.INTERNET
        };
        for (String permission:permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                        activity, new String[]{permission},
                        ACCESS
                );
            }
        }
    }
}
