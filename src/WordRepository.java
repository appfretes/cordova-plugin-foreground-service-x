package com.softniels.foregroundservicex;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.room.Room;
import androidx.room.RoomDatabase;
// import androidx.lifecycle.LiveData;

import java.util.List;

class WordRepository {

    private WordDao mWordDao;
    private List<Word> mAllWords;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    WordRepository(Context application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        //mAllWords = mWordDao.getAlphabetizedWords();
    }

    List<Word> getAllWords(){
        return mWordDao.getAlphabetizedWords();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
}