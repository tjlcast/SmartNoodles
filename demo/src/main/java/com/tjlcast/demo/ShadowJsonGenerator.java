package com.tjlcast.demo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tjlcast.common.data.Device;
import scala.util.parsing.json.JSON;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by tangjialiang on 2017/12/20.
 */
public class ShadowJsonGenerator {



    public static void main(String[] args) {
        ShadowJsonGenerator jsonGenerator = ShadowJsonGenerator.getJsonGenerator();
        JsonObject jsonObject = jsonGenerator.deviceShadowRandom(Device.getDeviceRandom());

        //String asString = ((JsonElement)jsonObject).getAsString() ;
        System.out.println(jsonObject) ;

        String json = "{\"attributes\":[{\"manufacture\":\"Tencent\"},{\"deviceType\":\"car\"},{\"deviceType\":\"T900\"}],\"telemetrices\":[{\"temperature\":\"9\"},{\"humidity\":\"-4\"},{\"pm2.5\":\"1\"}],\"services\":[{\"serviceName\":\"home\",\"protocol\":\"http\",\"url\":\"http://www.baidu.com\"}]}" ;
        JsonObject asJsonObject = new JsonParser().parse(json).getAsJsonObject();

    }

    public static ShadowJsonGenerator getJsonGenerator() {
        return new ShadowJsonGenerator() ;
    }

    public JsonObject deviceShadowRandom(Device device) {
        JsonObject root = new JsonObject();

        String manufacture = device.getManufacture();
        String deviceType = device.getDeviceType();
        String model = device.getModel();

        JsonArray attributes = new JsonArray();
        root.add("attributes", attributes);

        JsonArray telemetrices = new JsonArray() ;
        root.add("telemetrices", telemetrices);

        JsonArray services = new JsonArray() ;
        root.add("services", services);

        // add attributes
        for(JsonElement je : this.generateAttributeRandom(device)) {
            attributes.add(je);
        }
        // add telemetrices
        for(JsonElement je : this.generateTelemetriceRandom()) {
            telemetrices.add(je) ;
        }
        // add services
        for(JsonElement je : this.generateServiceRandom()) {
            services.add(je) ;
        }

        return root;
    }

    private List<JsonElement> generateAttributeRandom(Device device) {
        LinkedList<JsonElement> list = new LinkedList<>() ;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("manufacture", device.getManufacture());
        list.addLast(jsonObject);

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("deviceType", device.getDeviceType());
        list.addLast(jsonObject1);

        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("deviceType", device.getModel());
        list.addLast(jsonObject2);

        return list ;
    }

    private List<JsonElement> generateTelemetriceRandom() {
        String[] attributes = {"temperature", "humidity", "pm2.5"} ;
        LinkedList<JsonElement> list = new LinkedList<>() ;
        for(String attr : attributes) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(attr, (new Random().nextInt()%12) + "");
            list.add(jsonObject) ;
        }
        return list ;
    }

    private List<JsonElement> generateServiceRandom() {
        LinkedList<JsonElement> list = new LinkedList<>() ;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("serviceName", "home");
        jsonObject.addProperty("protocol", "http");
        jsonObject.addProperty("url", "http://www.baidu.com");
        list.addLast(jsonObject);

        return list ;
    }
}
