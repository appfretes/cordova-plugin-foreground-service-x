package com.softniels.foregroundservicex;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.List;

class EventRepository {

    private EventDao eventDao;
  private static final String TAG = "SoftnielsLogger";

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    EventRepository(Context context) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(context);
        eventDao = db.eventDao();
    }

    List<Event> getAll(){
        return eventDao.getAll();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Event event) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            
Log.i(TAG, "VAI INSERIR 1");
            Integer auxId = 0;
Log.i(TAG, "VAI INSERIR 2");
            Event auxEvent = eventDao.findLast();
Log.i(TAG, "VAI INSERIR 3");
            auxId = auxEvent.getId() + 1;
Log.i(TAG, "VAI INSERIR 4");
            event.setId(auxId);
Log.i(TAG, "VAI INSERIR 5");
            eventDao.insert(event);
Log.i(TAG, "VAI INSERIR 6");
        });
    }
}