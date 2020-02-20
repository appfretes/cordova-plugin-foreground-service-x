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
        }
        command.success();
        return true;
    }

    private void startService() {
        Context context = getApplicationContext();
        Activity activity = cordova.getActivity();
        Intent serviceIntent = new Intent(activity, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(context, serviceIntent);
    }

    private void stopService() {
        Activity activity = cordova.getActivity();
        Intent serviceIntent = new Intent(activity, ForegroundService.class);
        stopService(serviceIntent);
    }
}
