package com.softniels.foregroundservicex;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.FOREGROUND_SERVICE;

public class Permission {

    private static final String TAG = "SoftnielsLogger";

    public static Boolean checkAccessFineLocation(Context context, Activity appCompatActivity) {
        Boolean resultado = false;
        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                    context,
                    "Voce não tem permissão para ACCESS_FINE_LOCATION",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.i("ACCESS_FINE_LOCATION", "tudo certo para acssar ACCESS_FINE_LOCATION");
            resultado = true;
        }
        return resultado;
    }

    public static Boolean checkAccessCoarseLocation(Context context, Activity appCompatActivity) {
        Boolean resultado = false;
        if (ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                    context,
                    "Voce não tem permissão para ACCESS_COARSE_LOCATION",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.i("ACCESS_COARSE_LOCATION", "tudo certo para acssar ACCESS_COARSE_LOCATION");
            resultado = true;
        }
        return resultado;
    }
}
