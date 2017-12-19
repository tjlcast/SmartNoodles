package com.tjlcast.server.demo;

import com.google.gson.JsonObject;

/**
 * Created by tangjialiang on 2017/12/18.
 */
public class TestNull {
    public static void main(String[] args) {
        String name = null ;
        if(name instanceof String) {
            System.out.println("yes") ;
        } else {
            System.out.println("no");
        }

        JsonObject jsonObject = new JsonObject();
    }
}
