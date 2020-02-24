package  com.softniels.foregroundservicex;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.os.CountDownTimer;

import java.util.List;

public class SyncEvents {
  private static final String TAG = "SoftnielsLogger";
  private static int Contador = 1;
  private Context context;

    public SyncEvents(Context context) {
        this.context = context;
    }

    public void sincronizarEventos() {
        Log.i(TAG, "TESTE RAFAEL sincronizarEventos");
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.i(TAG, "SEGUNDOS RESTANTES: " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                Log.i(TAG, "FIM");
                Log.i(TAG, Integer.toString(Contador));
                Contador = Contador +1;
                if (Contador > 0){
                sincronizarEventos();
                }
            }
        }.start();
    }

    public void testeBD() {
        WordRepository bd = new WordRepository(context);
        Word word = new Word("dsadsadsa");
        bd.insert(word);
        List<Word> wordList = bd.getAll();
        Log.d(TAG, "Rows Count: " + wordList.size());
        for (int i = 0; i <= wordList.size() - 1; i++) {
            Log.d(TAG, "wordList[0]: " + wordList.get(i).getWord());
        };
        Log.i(TAG, "ENTROU testeBD 5 ");        
    }
}