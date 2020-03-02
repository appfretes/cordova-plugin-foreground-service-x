package com.softniels.foregroundservicex;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
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
    public String post(Integer idFrete, String url, String token) throws IOException {
        String responseString;
        Integer responseCode;
        String jsonBody;

        // Buscar localizações e converter para string        
        jsonBody = locationBD.getAllPendingString(idFrete);
        Log.i("SoftnielsLogger", "RequestBody jsonBody: " + jsonBody);
        // Criar corpo
        RequestBody body = RequestBody.create(JSON, jsonBody);
        // Criar requisição
        Request request = new Request.Builder()
                .header("Authentication", token)
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            responseCode = response.code();
            responseString = response.body().string();
            // Gson gson = new Gson();
            // ResponseEntity responseEntity = gson.fromJson(responseString, ResponseEntity.class);
            // Log.i("SoftnielsLogger", "responseEntity.message: " + responseEntity.getMessage());
            // Log.i("SoftnielsLogger", "responseEntity.reasonstring: " + String.valueOf(responseEntity.getReasonsString()));
            // Log.i("SoftnielsLogger", "responseEntity.statuscode: " + String.valueOf(responseEntity.getStatusCode()));
            if (responseCode != 200){
                IllegalArgumentException erro = new IllegalArgumentException("Erro ao realizar requisição: " + responseString);
                throw erro;
            };
            
            locationBD.updateSyncLocations(idFrete);

            return responseString;
        }
    }
}