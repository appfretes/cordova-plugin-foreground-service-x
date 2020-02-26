package com.softniels.foregroundservicex;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.FOREGROUND_SERVICE;

public class Permission {

    public static Boolean checkAccessFineLocation(Context context, AppCompatActivity appCompatActivity) {
        Boolean resultado = false;
        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                    context,
                    "Voce não tem permissão para ACCESS_FINE_LOCATION",
                    Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                appCompatActivity.requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                        1);
            }
        } else {
            Log.i("ACCESS_FINE_LOCATION", "tudo certo para acssar ACCESS_FINE_LOCATION");
            resultado = true;
        }
        return resultado;
    }

    public static Boolean checkAccessCoarseLocation(Context context, AppCompatActivity appCompatActivity) {
        Boolean resultado = false;
        if (ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                    context,
                    "Voce não tem permissão para ACCESS_COARSE_LOCATION",
                    Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                appCompatActivity.requestPermissions(new String[]{ACCESS_COARSE_LOCATION},
                        1);
            }
        } else {
            Log.i("ACCESS_COARSE_LOCATION", "tudo certo para acssar ACCESS_COARSE_LOCATION");
            resultado = true;
        }
        return resultado;
    }

    public static Boolean checkForeGroundService(Context context, AppCompatActivity appCompatActivity) {
        Boolean resultado = false;
        if (ContextCompat.checkSelfPermission(context, FOREGROUND_SERVICE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                    context,
                    "Voce não tem permissão para FOREGROUND_SERVICE",
                    Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                appCompatActivity.requestPermissions(new String[]{FOREGROUND_SERVICE},
                        1);
            }
        } else {
            Log.i("ACCESS_COARSE_LOCATION", "tudo certo para acssar ACCESS_COARSE_LOCATION");
            resultado = true;
        }
        return resultado;
    }
}
