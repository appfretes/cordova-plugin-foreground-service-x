package com.softniels.foregroundservicex;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.Response;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Call;
import okhttp3.Callback;

import android.util.Log;
import com.google.gson.Gson;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SendLocation {
    private OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private Context context;
    private LocationRepository locationBD;

    SendLocation(Context context) {
        this.context = context;
        locationBD = new LocationRepository(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void post(Integer idFrete, String url, String token) throws IOException {
        // Buscar localizações e converter para string        
        String bodyString = locationBD.getAllPendingString(idFrete);
        // Criar corpo
        RequestBody body = RequestBody.create(JSON, bodyString);
        // Criar requisição
        Request request = new Request.Builder()
                .header("Authentication", token)
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                Log.i("SoftnielsLogger", "Erro ao realizar requisição: ");
                e.printStackTrace();
            }
            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Erro ao realizar requisição: " + response);
                    //responseCode = response.code();
                    //responseString = response.body().string();
                    locationBD.updateSyncLocations(idFrete);
                    // Headers responseHeaders = response.headers();
                    // for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    //     System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    // }
                }
            }
        });
        //     // Gson gson = new Gson();
        //     // ResponseEntity responseEntity = gson.fromJson(responseString, ResponseEntity.class);
        //     // Log.i("SoftnielsLogger", "responseEntity.message: " + responseEntity.getMessage());
        //     // Log.i("SoftnielsLogger", "responseEntity.reasonstring: " + String.valueOf(responseEntity.getReasonsString()));
        //     // Log.i("SoftnielsLogger", "responseEntity.statuscode: " + String.valueOf(responseEntity.getStatusCode()));
    }
}