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
    private int MIN_TIME_BW_UPDATES = 10000;
    private int MIN_DISTANCE_CHANGE_FOR_UPDATES = 50;
    private int ID_FRETE = 0;
    private String DESTINO_LATITUDE;
    private String DESTINO_LONGITUDE;
    private int TEMPO_CAPTURA;
    private int DISTANCIA_CAPTURA;
    private static final String TAG = "SoftnielsLogger";
    private Context context;
    private LocationRepository locationBD;

    public void StopTrackLocation(Context context) {
        this.context = context;
        locationManager.removeUpdates(this);
    }

    public void StartTrackLocation(Context context) {
        this.context = context;
        locationBD = new LocationRepository(this.context);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        Boolean isGPSEnabled;
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            TEMPO_CAPTURA,
                            DISTANCIA_CAPTURA, this);
                }
            }
        }
    }

    public void setFrete(String idFrete){
        try {
            ID_FRETE = Integer.parseInt((String) idFrete);
        } catch (NumberFormatException e) {
            ID_FRETE = ID_FRETE;
        }
    }

    public void setDestino(String latitude, String longitude){
        DESTINO_LATITUDE = latitude;
        DESTINO_LONGITUDE = longitude;
    }

    public void setConfigLocation(String tempo_captura, String distancia_captura){
        try {
            TEMPO_CAPTURA = Integer.parseInt((String) tempo_captura);
        } catch (NumberFormatException e) {
            TEMPO_CAPTURA = MIN_TIME_BW_UPDATES;
        }
        try {
            DISTANCIA_CAPTURA = Integer.parseInt((String) distancia_captura);
        } catch (NumberFormatException e) {
            DISTANCIA_CAPTURA = MIN_DISTANCE_CHANGE_FOR_UPDATES;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        InsertLatitudeLongitude(location);
        //Calcular cerca
        CalcularHaversine(location);
    }

    private void InsertLatitudeLongitude(Location location) {
        com.softniels.foregroundservicex.Location newLocation = new com.softniels.foregroundservicex.Location(
            ID_FRETE,
            Double.toString(location.getLatitude()),
            Double.toString(location.getLongitude()),
            Double.toString(location.getTime())
        );
        locationBD.insert(newLocation);
    }

    private CalcularHaversine(Location location) {
        Log.d(TAG, "VAI CALCULAR DISTANCIA");
        double distancia = Haversine.haversine(DESTINO_LATITUDE, DESTINO_LONGITUDE, location.getLatitude(), location.getLongitude());
        Log.d(TAG, "CALCULOU DISTANCIA");
        Log.d(TAG, Double.toString(distancia));
        Log.d(TAG, "CALCULOU DISTANCIA FIM");
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
