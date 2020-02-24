package com.softniels.foregroundservicex;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ForegroundPlugin extends CordovaPlugin {

    @Override
    public boolean execute(final String action, final JSONArray args, final CallbackContext command)
            throws JSONException {
        if (action.equals("start")) {
            startService();
        } else if (action.equals("stop")) {
            stopService();
        } else if (action.equals("insertEvent")) {
            insertEvent(args.getString(0), args.getString(1));
        }
        command.success();
        return true;
    }

    private void startService() {
        Activity activity = cordova.getActivity();
        Intent serviceIntent = new Intent(activity, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(activity, serviceIntent);
    }

    private void stopService() {
        // Activity activity = cordova.getActivity();
        // Intent serviceIntent = new Intent(ForegroundPlugin.this, ForegroundService.class);
        // stopService(serviceIntent);
    }

    private void insertEvent(String event, String value){
        SyncEvents sincronizador = new SyncEvents(context);
        sincronizador.insertEvent(event, value);
    }
}
