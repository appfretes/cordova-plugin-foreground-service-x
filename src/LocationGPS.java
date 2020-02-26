package com.softniels.foregroundservicex;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationGPS extends Service implements LocationListener {

    private LocationManager locationManager = null;
    private int MIN_TIME_BW_UPDATES = 1000;
    private int MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final String TAG = "SoftnielsLogger";

    public void StopTrackLocation(Context context) {
        locationManager.removeUpdates(this);
    }

    public void StartTrackLocation(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        Boolean isGPSEnabled;
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "RECEBEU ATUALIZACAO DE LOCALIZAÇÃO");
        InsertLatitudeLongitude(location);
        //editTextHaversine.setText(CalcularHaversine(location).toString()); Calculo da cerca
    }

    private void InsertLatitudeLongitude(Location location) {
        Log.d(TAG, Double.toString(location.getLatitude()));
        Log.d(TAG, Double.toString(location.getLongitude()));
    }

    private Double CalcularHaversine(Location location) {
        double distancia = Haversine.haversine(-26.243626, -51.071994, location.getLatitude(), location.getLongitude());
        return distancia;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("onProviderEnabled provider", provider);
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
