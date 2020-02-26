package  com.softniels.foregroundservicex;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.os.CountDownTimer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        Log.i(TAG, "INICIANDO TESTE BANCO DE DADOS 'WORD' ");
        WordRepository bd = new WordRepository(context);
        Word word = new Word("dsadsadsa");
        bd.insert(word);
        List<Word> wordList = bd.getAll();
        Log.d(TAG, "Rows Count: " + wordList.size());
        for (int i = 0; i <= wordList.size() - 1; i++) {
            Log.d(TAG, "wordList[0]: " + wordList.get(i).getWord());
        };
        Log.i(TAG, "FINALIZANDO TESTE BANCO DE DADOS 'WORD' ");

        Log.i(TAG, "INICIANDO TESTE BANCO DE DADOS 'EVENT' ");
        EventRepository eventBD = new EventRepository(context);
        Event event = new Event(0, "CHEGOU_AO_DESTINO", "");
        eventBD.insert(event);
        List<Event> eventList = eventBD.getAll();
        Log.d(TAG, "Rows Count: " + eventList.size());
        for (int i = 0; i <= eventList.size() - 1; i++) {
            Log.d(TAG, "eventList[0]: " + eventList.get(i).getEvent() + " - " + eventList.get(i).getValue());
        };
        Log.i(TAG, "FINALIZANDO TESTE BANCO DE DADOS 'WORD' ");
    }

    public void insertEvent(Integer id, String event, String value) {
        EventRepository eventBD = new EventRepository(context);
        Event newEvent = new Event(id, event, value);
        eventBD.insert(newEvent);
    };

    public JSONArray getEvents() {
        EventRepository eventBD = new EventRepository(context);
        List<Event> eventList = eventBD.getAll();
        JSONArray array = new JSONArray();

        Log.d(TAG, "Rows Count: " + eventList.size());
        for (int i = 0; i <= eventList.size() - 1; i++) {
            Log.d(TAG, "eventList[0]: " + eventList.get(i).getEvent() + " - " + eventList.get(i).getValue());

            try {
                JSONObject obj = new JSONObject();
                //obj.put("id", Integer.toString(eventList.get(i).getId()));
                obj.put("event", eventList.get(i).getEvent());
                obj.put("value", eventList.get(i).getValue());
                array.put(obj);
            } catch (JSONException e) {
                Log.d(TAG, "Teste: " + e);
            }
        };

        return array;
    };
}