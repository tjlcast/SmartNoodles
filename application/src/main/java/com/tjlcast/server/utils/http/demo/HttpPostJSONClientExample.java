package com.tjlcast.server.utils.http.demo;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by tangjialiang on 2017/12/11.
 */
public class HttpPostJSONClientExample {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8") ;

    OkHttpClient client = new OkHttpClient() ;

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build() ;

        try (Response response = client.newCall(request).execute()) {
            return response.body().string() ;
        }
    }

    public static void main(String[] args) {

    }
}
