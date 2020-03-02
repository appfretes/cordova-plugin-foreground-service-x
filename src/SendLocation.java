package com.softniels.foregroundservicex;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;

import com.google.gson.Gson;

public class SendLocation {
    private OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String post(Integer idFrete, String url, String token) throws IOException {
        // Buscar localizações e converter para json
        String jsonBody = "{}";
        // Criar corpo
        RequestBody body = RequestBody.create(JSON, jsonBody);
        //
        Request request = new Request.Builder()
                .header("Authentication", token)
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            // if (response.code() == 200){
            //     callback.success("Sucess in action teste: " + responseString);
            // } else {
            //     callback.error("Error in action: testeteste 123: " + responseString);
            // }

            Gson gson = new Gson();
            ResponseEntity responseEntity = gson.fromJson(value, ResponseEntity.class);
            Log.i("SoftnielsLogger", "responseEntity.message: " + responseEntity.getMessage());
            Log.i("SoftnielsLogger", "responseEntity.reasonstring: " + String.valueOf(responseEntity.getReasonsString()));
            Log.i("SoftnielsLogger", "responseEntity.statuscode: " + String.valueOf(responseEntity.getStatusCode()));
            //return responseEntity;
            return response.body().string();
        }
    }
}