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
import java.util.List;
import io.cordova.hellocordova.*;

public class ForegroundPlugin extends CordovaPlugin {

    private CallbackContext callback = null;
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
        context = this.cordova.getActivity().getApplicationContext();
        callback = callbackContext;
        if (ValidarPermissaoExecucao()) {
            if (action.equals("start")) {
                startService(args.getString(5), args.getString(6), args.getString(7), args.getString(8), args.getString(9), args.getString(10), args.getString(11), args.getString(12), args.getString(13));
            } else if (action.equals("stop")) {
                stopService();
            } else if (action.equals("verifyPermissions")) {
                verifyPermissions();
            } else if (action.equals("insertEvent")) {
                insertEvent(args.getString(0), args.getString(1), args.getString(2));
            } else if (action.equals("getEvents")) {
                getEvents(args.getInt(0));
            } else if (action.equals("getLocations")) {
                getLocations();
            } else if (action.equals("updateSyncLocations")) {
                updateSyncLocations(args.getInt(0));
            } else if (action.equals("deleteSyncLocations")) {
                deleteSyncLocations(args.getInt(0));
            } else if (action.equals("sendLocations")) {
                sendLocations(args.getInt(0), args.getString(1), args.getString(2));
            } else {
                callback.error("Invalid action: " + action);
            };
        } else {
            callback.error("O aplicativo não funcionará até que as permissões sejam liberadas.");
        };
        return true;
    }

    private void verifyPermissions(){
        Permission.checkAccessFineLocation(context, cordova.getActivity());
        Permission.checkAccessCoarseLocation(context, cordova.getActivity());
    }

    private boolean ValidarPermissaoExecucao() {
        return (Permission.checkAccessFineLocation(context, cordova.getActivity()) &&
                Permission.checkAccessCoarseLocation(context, cordova.getActivity())
        );
    }

    // Service
    private void startService(String id_frete, String latitude, String longitude, String tempo_captura, String tempo_envio, String distancia_captura, String raio, String url, String token) {
        Activity activity = cordova.getActivity();
        Intent serviceIntent = new Intent(activity, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        serviceIntent.putExtra("id_frete", id_frete);
        serviceIntent.putExtra("latitude", latitude);
        serviceIntent.putExtra("longitude", longitude);
        serviceIntent.putExtra("tempo_captura", tempo_captura);
        serviceIntent.putExtra("tempo_envio", tempo_envio);
        serviceIntent.putExtra("distancia_captura", distancia_captura);
        serviceIntent.putExtra("raio", raio);
        serviceIntent.putExtra("url", url);
        serviceIntent.putExtra("token", token);
        ContextCompat.startForegroundService(activity, serviceIntent);
    }

    private void stopService() {
        // Activity activity = cordova.getActivity();
        // Intent serviceIntent = new Intent(ForegroundPlugin.this, ForegroundService.class);
        // stopService(serviceIntent);
    }

    // Event
    private void insertEvent(String id_frete, String event, String value){
        try {
            EventRepository eventBD = new EventRepository(context);
            Event newEvent = new Event(Integer.parseInt(id_frete), event, value);
            eventBD.insert(newEvent);
            callback.success("Sucess in action: insertEvent");
        } catch (Exception e) {
            callback.error("Error in action: insertEvent: " + e);
        }
    }

    private void getEvents(Integer id_frete){
        try{
            EventRepository eventBD = new EventRepository(context);
            callback.success(eventBD.getAllString(id_frete));
        } catch (Exception e){
            callback.error("Error in action: getEvents: " + e);
        }
    }

    // Locations
    private void getLocations(){
        try{
            LocationRepository locationBD = new LocationRepository(context);
            callback.success("Sucess string: " + locationBD.getAllString());
        } catch (Exception e){
            callback.error("Error in action: getLocations: " + e);
        }
    }

    private void updateSyncLocations(Integer id_frete){
        try{
            LocationRepository locationBD = new LocationRepository(context);
            locationBD.updateSyncLocations(id_frete);
            callback.success("Success");
        } catch (Exception e){
            callback.error("Error in action: updateSyncLocations: " + e);
        }
    }

    private void deleteSyncLocations(Integer id_frete){
        try{
            LocationRepository locationBD = new LocationRepository(context);
            locationBD.deleteSyncLocations(id_frete);
            callback.success("Success");
        } catch (Exception e){
            callback.error("Error in action: deleteSyncLocations: " + e);
        }
    }

    private void sendLocations(Integer id_frete, String url, String token){
        try{
            SendLocation sendLocation = new SendLocation(context);
            sendLocation.post(id_frete, url, token);
            callback.success("Success in action: sendLocations");
        } catch (Exception e){
            callback.error("Error in action: sendLocations: " + e);
        }
    }
}