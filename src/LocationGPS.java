package com.softniels.foregroundservicex;

import android.app.Service;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationGPS extends Service implements LocationListener {

    private LocationManager locationManager = null;
    private int MIN_TIME_BW_UPDATES = 10000;
    private int MIN_DISTANCE_CHANGE_FOR_UPDATES = 50;
    private int ID_FRETE = 0;
    private String DESTINO_LATITUDE;
    private String DESTINO_LONGITUDE;
    private String DESTINO_RAIO;
    private String URL;
    private String TOKEN;
    private int TEMPO_CAPTURA;
    private int TEMPO_ENVIO = 2;
    private int CONTADOR_ENVIO = 0;
    private int DISTANCIA_CAPTURA;
    private static final String TAG = "SoftnielsLogger";
    private Context context;
    private LocationRepository locationBD;
    private EventRepository eventBD;

    public void StopTrackLocation(Context context) {
        this.context = context;
        locationManager.removeUpdates(this);
    }

    public void StartTrackLocation(Context context) {
        this.context = context;
        locationBD = new LocationRepository(this.context);
        eventBD = new EventRepository(this.context);
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

    public void setDestino(String latitude, String longitude, String raio){
        DESTINO_LATITUDE = latitude;
        DESTINO_LONGITUDE = longitude;
        DESTINO_RAIO = raio;
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

    public void setConfigSync(String tempo_envio, String url, String token){
        try {
            TEMPO_ENVIO = Integer.parseInt((String) tempo_envio);
        } catch (NumberFormatException e) {
            TEMPO_ENVIO = 2;
        }
        URL = url;
        TOKEN = token;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        InsertLatitudeLongitude(location);
        CalcularHaversine(location);
        CONTADOR_ENVIO = CONTADOR_ENVIO + 1;
        if (TEMPO_ENVIO == CONTADOR_ENVIO){
            SendLocation();
            CONTADOR_ENVIO = 0;
        };
    }

    private void SendLocation(){
        try{
            SendLocation sendLocation = new SendLocation(context);
            sendLocation.post(ID_FRETE, URL, TOKEN);
        } catch (Exception e){
            Log.i(TAG, "ERRO AO ENVIAR: " + e);
        }        
    }

    private void InsertLatitudeLongitude(Location location) {
        com.softniels.foregroundservicex.Location newLocation = new com.softniels.foregroundservicex.Location(
            ID_FRETE,
            Double.toString(location.getLatitude()),
            Double.toString(location.getLongitude()),
            convertDate(location.getTime())
        );
        locationBD.insert(newLocation);
    }

    private void insertEvent(String evento) {
        Event newEvent = new Event(ID_FRETE, evento, "");
        eventBD.insert(newEvent);
    }    

    private String convertDate(Long milliseconds){
        Date d = new Date(milliseconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    private void CalcularHaversine(Location location) {
        double distancia = Haversine.haversine(Double.valueOf(DESTINO_LATITUDE), Double.valueOf(DESTINO_LONGITUDE), location.getLatitude(), location.getLongitude());
        double raio = Double.valueOf(DESTINO_RAIO);
        if (distancia <= raio) {
            acordarCelular();
            insertEvent("ENTROU_CERCA");
        };
    }

    private void acordarCelular(){
        try {
            //Intent intent = new Intent(myService, io.cordova.hellocordova.MainActivity.class);
            //String pkgName    = "io.cordova.hellocordova";
            //Intent intent = new Intent();
            //intent.setData(Uri.parse("package:" + pkgName));
            //intent.setAction(Intent.ACTION_VIEW);
            Intent intent = new Intent(this.context, io.cordova.hellocordova.MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            this.context.startActivity(intent);
        } catch (Exception e) {
            Log.d(TAG, "ERROR AO ACORDAR " + e);
        }
    };

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
