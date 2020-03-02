package com.softniels.foregroundservicex;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

class LocationRepository {

    private LocationDao locationDao;
    private Gson gson;
    private static final String TAG = "SoftnielsLogger";

    LocationRepository(Context context) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(context);
        locationDao = db.locationDao();
        gson = new Gson();
    }

    List<Location> getAll(){
        return locationDao.getAll();
    }

    List<Location> getAllPending(Integer idFrete){
        return locationDao.getAllPending(idFrete);
    }

    // JSONArray getAllJSON(){
    //     JSONArray array = new JSONArray();
    //     List<Location> locationList = this.getAll();
    
    //     for (int i = 0; i <= locationList.size() - 1; i++) {
    //         try {
    //             JSONObject obj = new JSONObject();
    //             obj.put("id", String.valueOf(locationList.get(i).id()));
    //             obj.put("id_frete", String.valueOf(locationList.get(i).id_frete()));
    //             obj.put("latitude", locationList.get(i).latitude());
    //             obj.put("longitude", locationList.get(i).longitude());
    //             obj.put("data_captura", locationList.get(i).data_captura());
    //             obj.put("sincronizado", locationList.get(i).sincronizado());
    //             array.put(obj);
    //         } catch (JSONException e) {
    //             Log.d(TAG, "Erro: " + e);
    //         }
    //     };
    //     return array;
    // }

    String getAllString(){
        List<Location> locationList = this.getAll();
        String stringList = gson.toJson(locationList);
        return stringList;
    }

    String getAllPendingString(Integer idFrete){
        List<Location> locationList = this.getAllPending(idFrete);
        String stringList = gson.toJson(locationList);
        return stringList;
    }        

    void updateSyncLocations(Integer idFrete){
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            locationDao.updateSyncLocations(idFrete);
        });
    }

    void deleteSyncLocations(Integer idFrete){
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            locationDao.deleteSyncLocations(idFrete);
        });
    }

    void insert(Location location) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            Integer auxId = 1;
            Location auxLocation = locationDao.findLast();
            if (auxLocation != null){
                auxId = auxLocation.id() + 1;
            };
            location.setId(auxId);
            //location.setSincronizado("Não");
            locationDao.insert(location);
        });
    }
}