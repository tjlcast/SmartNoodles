package com.tjlcast.demo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tjlcast.server.utils.http.demo.OkHttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangjialiang on 2017/12/22.
 */
public class ThingBoardApi {

    private String host ;
    private int port ;
    private String token ;

    public ThingBoardApi(String host, int port) {
        this.host = host ;
        this.port = port ;
    }

    void token() {
        //curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"username":"tenant@thingsboard.org", "password":"tenant"}' 'http://THINGSBOARD_URL/api/auth/login'
        String[] args = {"Content-Type: application/json", "Accept: application/json"} ;
        Map<String, String> stringStringMap = headerConvertList2Map(args);

        try {
            String stringFromServer = OkHttpUtil.getStringFromServer(authUrl(), stringStringMap, "{\"username\":\"tenant@thingsboard.org\", \"password\":\"tenant\"}");
            JsonElement parsed = new JsonParser().parse(stringFromServer);
            String token = ((JsonObject) parsed).get("token").getAsString();
            this.token = token ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String thingsboardUrl() {
        String url = String.format("http://%s:%s", this.host, this.port) ;
        return url ;
    }

    protected String authUrl() {
        String url = String.format("%s/api/auth/login", thingsboardUrl()) ;
        return url ;
    }

    public static Map<String, String> headerConvertList2Map(String[] args) {
        Map<String, String> headers = new HashMap<>() ;
        for(String arg : args) {
            String[] split = arg.split(":");
            headers.put(split[0].trim(), split[1].trim()) ;
        }
        return headers ;
    }

    public static void main( String[] args )
    {
        ThingBoardApi thingBoardApi = new ThingBoardApi("10.108.217.227", 8080);
        thingBoardApi.token() ;
    }
}
