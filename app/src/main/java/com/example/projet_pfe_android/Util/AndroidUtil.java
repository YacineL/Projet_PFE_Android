package com.example.projet_pfe_android.Util;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;
import static org.apache.commons.lang3.Validate.notNull;

public class AndroidUtil {

    public static final int SCANNER_REQUEST_CODE = REQUEST_CODE;
    public static final int IMAGE_CAPTURE_REQUEST_CODE = 0x0000d001;
    public static final int RESOURCE_PICK_REQUEST_CODE = 0x0000d002;

    public static void verifyPermissionsForScanner(AppCompatActivity activity) {
        notNull(activity, "[AndroidUtil][verifyPermissionsForScanner] is null");
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        if (
                ContextCompat.checkSelfPermission(activity.getApplicationContext(), permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(activity.getApplicationContext(), permissions[1]) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(activity.getApplicationContext(), permissions[2]) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(activity, permissions, SCANNER_REQUEST_CODE);
        }

    }
}
