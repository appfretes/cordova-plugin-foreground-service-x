package com.softniels.foregroundservicex;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
            return response.body().string();
        }
    }
}