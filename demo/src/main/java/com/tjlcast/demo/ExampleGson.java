package com.tjlcast.demo;

import com.google.gson.JsonObject;

/**
 * Created by tangjialiang on 2017/12/18.
 */
public class ExampleGson {

    public static void main(String[] args) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "tjl");
        jsonObject.addProperty("name", "bupt");

        System.out.println(jsonObject.toString()) ;
    }
}
