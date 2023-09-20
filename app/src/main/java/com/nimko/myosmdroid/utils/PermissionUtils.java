package com.nimko.myosmdroid.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    public final static int PERMISSIONS_REQUEST_CODE = 17;

    public static void checkPermission(Activity activity) {
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.INTERNET);
        if(Build.VERSION.SDK_INT > 31)
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        permissions.stream().filter(permission ->
                ContextCompat.checkSelfPermission(activity, permission) !=
                PackageManager.PERMISSION_GRANTED)
                .forEach(permission -> ActivityCompat.requestPermissions(
                activity, new String[]{permission}, PERMISSIONS_REQUEST_CODE));

    }
}
