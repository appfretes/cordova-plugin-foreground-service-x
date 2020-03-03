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
    }

    // public void insertEvent(Integer id, String event, String value) {
    //     EventRepository eventBD = new EventRepository(context);
    //     Event newEvent = new Event(0, event, value);
    //     eventBD.insert(newEvent);
    // };

    // public JSONArray getEvents() {
    //     EventRepository eventBD = new EventRepository(context);
    //     List<Event> eventList = eventBD.getAll();
    //     JSONArray array = new JSONArray();
        
    //     for (int i = 0; i <= eventList.size() - 1; i++) {
    //         try {
    //             JSONObject obj = new JSONObject();
    //             obj.put("id", String.valueOf(eventList.get(i).id()));
    //             obj.put("event", eventList.get(i).event());
    //             obj.put("value", eventList.get(i).value());
    //             array.put(obj);
    //         } catch (JSONException e) {
    //             Log.d(TAG, "Erro: " + e);
    //         }
    //     };

    //     return array;
    // };
}