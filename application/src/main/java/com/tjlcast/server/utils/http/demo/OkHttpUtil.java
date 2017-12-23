package com.tjlcast.server.utils.http.demo;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.tjlcast.server.utils.http.demo.HttpPostJSONClientExample.JSON;

/**
 * Created by tangjialiang on 2017/12/11.
 */

public class OkHttpUtil {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    /**
     * 同步方法
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute() ;
    }

    /**
     * 开启异步线程访问网络
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     * @param request
     */
    public static void enqueue(Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public static String getStringFromServer(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String string = response.body().string();
            return string ;
        } else {
            throw new IOException("Unexpected code " + response) ;
        }
    }

    public static String getStringFromServer(String url, Map<String, String> headers, String fileStr) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();

        Request.Builder requestBuilders = new Request.Builder()
                .url(url);
        for(Map.Entry<String, String> item : headers.entrySet()) {
            requestBuilders = requestBuilders.header(item.getKey(), item.getValue());
        }

        if (fileStr == null) {
            requestBuilders = requestBuilders.get();
        } else {
            RequestBody body = RequestBody.create(JSON, fileStr);
            requestBuilders = requestBuilders.post(body);
        }
        Request request = requestBuilders.build();

        Response response = execute(request);
        if (response.isSuccessful()) {
            String string = response.body().string();
            return string ;
        } else {
            throw new IOException("Unexpected code " + response) ;
        }
    }
}
