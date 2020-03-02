package com.softniels.foregroundservicex;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.List;
import com.google.gson.Gson;

class EventRepository {

    private EventDao eventDao;
    private Gson gson;
    private static final String TAG = "SoftnielsLogger";

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    EventRepository(Context context) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(context);
        eventDao = db.eventDao();
        gson = new Gson();
    }

    List<Event> getAll(){
        return eventDao.getAll();
    }

    List<Event> getAll(Integer id_frete){
        return eventDao.getAll(id_frete);
    }

    String getAllString(Integer id_frete){
        List<Event> eventList = this.getAll(id_frete);
        String stringList = gson.toJson(eventList);
        return stringList;
    }    

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Event event) {
        Log.d(TAG, "INSERIR EVENTO 1 EVENTO 1");
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            Log.d(TAG, "INSERIR EVENTO 1 EVENTO 2");
            Integer auxId = 1;
            Event auxEvent = eventDao.findLast();
            Log.d(TAG, "INSERIR EVENTO 1 EVENTO 3");
            if (auxEvent != null){
                Log.d(TAG, "INSERIR EVENTO 1 EVENTO 4");
                auxId = auxEvent.id() + 1;
                Log.d(TAG, "INSERIR EVENTO 1 EVENTO 5");
            };
            Log.d(TAG, "INSERIR EVENTO 1 EVENTO 6");
            event.setId(auxId);
            Log.d(TAG, "INSERIR EVENTO 1 EVENTO 7");
            eventDao.insert(event);
            Log.d(TAG, "INSERIR EVENTO 1 EVENTO 8");
        });
        Log.d(TAG, "INSERIR EVENTO 1 EVENTO 9");
    }
}