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

    //private CallbackContext callback = null;
    //private Context context = this.cordova.getActivity().getApplicationContext();
    private static final String TAG = "SoftnielsLogger";
    private Context context = null;

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
    throws JSONException{
        Log.i(TAG, "TESTE ACTION: " + action);

        context = this.cordova.getActivity().getApplicationContext();

        //callback = callbackContext;
        if (action.equals("start")) {
            startService();
        } else if (action.equals("stop")) {
            stopService();
        } else if (action.equals("insertEvent")) {
            insertEvent(args.getString(0), args.getString(1), args.getString(2));
        } else if (action.equals("getEvents")) {
            getEvents();
        } else {
            //callback.error("Invalid action: " + action);
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
            Log.i(TAG, "vai inserir 1");
            SyncEvents sincronizador = new SyncEvents(context);
            Log.i(TAG, "vai inserir 2");
            sincronizador.insertEvent(0, event, value);
            Log.i(TAG, "vai inserir 3");
            //callback.success("Sucess in action: insertEvent");
        } catch (Exception e) {
            Log.i(TAG, "vai inserir 4");
            //callback.error("Error in action: insertEvent: " + e);
        }
    }

    private void getEvents(){
        Log.i(TAG, "vai buscar");
        try{
            SyncEvents sincronizador = new SyncEvents(context);
            sincronizador.getEvents();
            Log.i(TAG, "vai buscar sucesso");
            //callback.success(sincronizador.getEvents());
        } catch (Exception e){
            Log.i(TAG, "vai buscar erro");
            //callback.error("Error in action: getEvents: " + e);
        }
    }
}
