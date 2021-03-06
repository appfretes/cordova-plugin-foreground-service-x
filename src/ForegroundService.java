package com.softniels.foregroundservicex;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.app.PendingIntent;
import android.app.Service;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

public class ForegroundService extends Service {
    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private LocationGPS locationGPS = null; // GPS
    private static final String TAG = "SoftnielsLogger";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Context context = getApplicationContext();
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(ForegroundService.this, io.cordova.hellocordova.MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // notificationIntent.setAction("android.intent.action.MAIN");
        // notificationIntent.addCategory("android.intent.category.LAUNCHER");

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Application")
                .setContentText(input)
                //.setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
        startForeground(1, notification);
        
        // intent.getStringExtra("tempo_envio")
        // Fazer capturas em segundo plano e gravar
        locationGPS = new LocationGPS();
        locationGPS.setFrete(intent.getStringExtra("id_frete"));
        locationGPS.setDestino(intent.getStringExtra("latitude"), intent.getStringExtra("longitude"), intent.getStringExtra("raio"));
        locationGPS.setConfigLocation(intent.getStringExtra("tempo_captura"), intent.getStringExtra("distancia_captura"));
        locationGPS.setConfigSync(intent.getStringExtra("tempo_envio"), intent.getStringExtra("url"), intent.getStringExtra("token"));
        locationGPS.StartTrackLocation(getBaseContext());
        // Verificar se entrou na cerca
        // Enviar capturas
        // Enviar eventos
        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}