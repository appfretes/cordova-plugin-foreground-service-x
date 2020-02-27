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
                startService(args.getString(4), args.getString(5), args.getString(6), args.getString(7));
            } else if (action.equals("stop")) {
                stopService();
            } else if (action.equals("verifyPermissions")) {
                verifyPermissions();
            } else if (action.equals("insertEvent")) {
                insertEvent(args.getString(0), args.getString(1), args.getString(2));
            } else if (action.equals("getEvents")) {
                getEvents();
            } else if (action.equals("getLocations")) {
                getLocations();
            } else if (action.equals("updateSyncLocations")) {
                updateSyncLocations(args.getInteger(0));
            } else {
                callback.error("Invalid action: " + action);
            };
        } else {
            callback.error("O aplicativo não funcionará até que as permissões sejam liberadas.");
        };
        return true;
    }

    private void startService(String latitude, String longitude, Integer tempo_captura, Integer distancia_captura) {
        Activity activity = cordova.getActivity();
        Intent serviceIntent = new Intent(activity, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        serviceIntent.putExtra("latitude", latitude);
        serviceIntent.putExtra("longitude", longitude);
        serviceIntent.putExtra("tempo_captura", tempo_captura);
        serviceIntent.putExtra("distancia_captura", distancia_captura);
        ContextCompat.startForegroundService(activity, serviceIntent);
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

    private void stopService() {
        // Activity activity = cordova.getActivity();
        // Intent serviceIntent = new Intent(ForegroundPlugin.this, ForegroundService.class);
        // stopService(serviceIntent);
    }

    private void insertEvent(String id, String event, String value){
        try {
            SyncEvents sincronizador = new SyncEvents(context);
            sincronizador.insertEvent(Integer.parseInt(id), event, value);
            callback.success("Sucess in action: insertEvent");
        } catch (Exception e) {
            callback.error("Error in action: insertEvent: " + e);
        }
    }

    private void getEvents(){
        try{
            SyncEvents sincronizador = new SyncEvents(context);
            callback.success(sincronizador.getEvents());
        } catch (Exception e){
            callback.error("Error in action: getEvents: " + e);
        }
    }

    private void getLocations(){
        try{
            LocationRepository locationBD = new LocationRepository(context);
            List<Location> locationList = locationBD.getAll();
            JSONArray array = new JSONArray();
        
            for (int i = 0; i <= locationList.size() - 1; i++) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("id", String.valueOf(locationList.get(i).getId()));
                    obj.put("latitude", locationList.get(i).getLatitude());
                    obj.put("longitude", locationList.get(i).getLongitude());
                    obj.put("dataTransacao", locationList.get(i).getDataTransacao());
                    array.put(obj);
                } catch (JSONException e) {
                    Log.d(TAG, "Erro: " + e);
                }
            };

            callback.success(array);
        } catch (Exception e){
            callback.error("Error in action: getLocations: " + e);
        }
    }

    private void updateSyncLocations(Integer idFrete){
        try{
            LocationRepository locationBD = new LocationRepository(context);
            locationBD.updateSyncLocations(idFrete);
            callback.success("Success");
        } catch (Exception e){
            callback.error("Error in action: getLocations: " + e);
        }
    }    
}
