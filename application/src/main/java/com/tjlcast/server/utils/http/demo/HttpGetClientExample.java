package com.tjlcast.server.utils.http.demo;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by tangjialiang on 2017/12/11.
 *
 * try() {} 实现了AutoCloseable接口的实例可以放在try(...)中
 在离开try块时将自动调用close()方法。
 */
public class HttpGetClientExample {

    OkHttpClient client = new OkHttpClient() ;

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string() ;
        }
    }

    public static void main(String[] args) throws Exception {
        HttpGetClientExample example = new HttpGetClientExample() ;
        String response = example.run("http://www.baidu.com") ;
        System.out.println(response) ;
    }
}
