package com.tjlcast.server.utils.http.demo;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by tangjialiang on 2017/12/11.
 */
public class HttpPostKVClientExample {

    OkHttpClient client = new OkHttpClient() ;

    public String post(String url) throws IOException {
        FormBody body = new FormBody.Builder()
                .add("your_param_1", "your_value_1")
                .add("your_param_2", "your_value_2")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static void main(String[] args) throws IOException {
        String url = "www.baidu.com" ;
        HttpPostKVClientExample httpPostKVClientExample = new HttpPostKVClientExample();
        String post = httpPostKVClientExample.post(url);

        System.out.println(post) ;
    }
}
