package com.softniels.foregroundservicex;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.List;

class LocationRepository {

    private LocationDao locationDao;
    private static final String TAG = "SoftnielsLogger";

    LocationRepository(Context context) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(context);
        locationDao = db.locationDao();
    }

    List<Location> getAll(){
        return locationDao.getAll();
    }

    void updateSyncLocations(Integer idFrete){
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            locationDao.updateSyncLocations(idFrete);
        });
    }

    void insert(Location location) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            Integer auxId = 1;
            Location auxLocation = locationDao.findLast();
            if (auxLocation != null){
                auxId = auxLocation.getId() + 1;
            };
            location.setId(auxId);
            locationDao.insert(location);
        });
    }
}