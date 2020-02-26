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
import android.util.Log;

public class ForegroundPlugin extends CordovaPlugin {

    private CallbackContext callback = null;
    private Context context = this.cordova.getActivity().getApplicationContext();
    private static final String TAG = "SoftnielsLogger";

    /**
     * Executes the request.
     *
     * @param action   The action to execute.
     * @param args     The exec() arguments.
     * @param callback The callback context used when
     *                 calling back into JavaScript.
     *
     * @return Returning false results in a "MethodNotFound" error.
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext)
    {                
        Log.i(TAG, "TESTE ACTION: " + action);

        callback = callbackContext;
        if (action.equals("start")) {
            startService();
        } else if (action.equals("stop")) {
            stopService();
        } else if (action.equals("insertEvent")) {
            insertEvent(args.getString(0), args.getString(1), args.getString(2));
        } else if (action.equals("getEvents")) {
            getEvents();
        } else {
            callback.error("Invalid action: " + action);
        };
        
        Log.i(TAG, "TESTE ACTION 2: " + action);
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

    private void insertEvent(String id, String event, String value){
        Log.i(TAG, "vai inserir");
        try {
            SyncEvents sincronizador = new SyncEvents(context);
            sincronizador.insertEvent(Integer.parseInt(id), event, value);
            callback.success("Sucess in action: insertEvent");
        } catch (Exception e) {
            callback.error("Error in action: insertEvent: " + e);
        }
    }

    private void getEvents(){
        Log.i(TAG, "vai buscar");
        try{
            SyncEvents sincronizador = new SyncEvents(context);
            callback.success(sincronizador.getEvents());
        } catch (Exception e){
            callback.error("Error in action: getEvents: " + e);
        }
    }
}
