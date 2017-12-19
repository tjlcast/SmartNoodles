package com.tjlcast.common.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangjialiang on 2017/12/18.
 *
 */
public class DeviceShadow {
    private JsonObject payload ;

    private HashMap<String, JsonObject> attributes ;
    private HashMap<String, JsonObject> telemetries ;
    private HashMap<String, JsonObject> services ;

    public DeviceShadow() {
        payload = new JsonObject() ;
        payload.add("attributes", new JsonArray()) ;
        payload.add("telemetrices", new JsonArray()) ;
        payload.add("services", new JsonArray()) ;

        attributes = new HashMap<>();
        telemetries = new HashMap<>() ;
        services = new HashMap<>() ;
    }

    public DeviceShadow(JsonObject payload) {
        this.payload = payload ;

        this.attributes = new HashMap<>() ;
        this.telemetries = new HashMap<>() ;
        this.services = new HashMap<>() ;

        for(JsonElement element : payload.getAsJsonArray("attributes")) {
            this.attributes.put(((JsonObject)element).get("attributeName").getAsString(), (JsonObject) element) ;
        }

        for(JsonElement element : payload.getAsJsonArray("telemetrices")) {
            this.telemetries.put(((JsonObject)element).get("telemetryName").getAsString(), (JsonObject) element) ;
        }

        for(JsonElement element : payload.getAsJsonArray("services")) {
            this.services.put(((JsonObject)element).get("serviceName").getAsString(), (JsonObject) element) ;
        }
    }

    public static boolean isValidDeviceShadow(JsonObject json) {
        if(!(json instanceof JsonObject)) return false ;
        JsonObject payload = json.getAsJsonObject();
        if(payload==null) return false ;
        if(!(payload.get("attributes") instanceof JsonArray)) return false;
        if(!(payload.get("telemetries") instanceof JsonArray)) return false;
        if(!(payload.get("services") instanceof JsonArray)) return false;

        for(JsonElement element : payload.getAsJsonArray("attributes")) {
            if(!isValidAttribute(element)) return false ;
        }

        for(JsonElement element : payload.getAsJsonArray("telemetries")) {
            if(!isValidTelemetry(element)) return false ;
        }

        for(JsonElement element : payload.getAsJsonArray("services")) {
            if(!isValidService(element)) return false ;
        }

        return true ;
    }

    public static boolean isValidAttribute(JsonElement json) {
        if(json == null || !(json instanceof JsonObject)) return false ;
        JsonObject payload = json.getAsJsonObject();
        if(payload==null || !(payload.get("attributeName") instanceof JsonPrimitive) || !(payload.get("attributeValue") instanceof JsonPrimitive)) return false ;
        return true ;
    }

    public static boolean isValidTelemetry(JsonElement json) {
        if(json == null || !(json instanceof JsonObject)) return false ;
        JsonObject payload = json.getAsJsonObject();
        if(payload==null || !(payload.get("telemetryName") instanceof JsonPrimitive) || !(payload.get("telemetryValue") instanceof JsonPrimitive)) return false ;
        return true ;
    }

    public static boolean isValidService(JsonElement json) {
        if(json == null || !(json instanceof JsonObject)) return false ;
        JsonObject payload = json.getAsJsonObject();
        if(payload==null || !(payload.get("serviceName") instanceof JsonPrimitive) || !(payload.get("serviceDescription") instanceof JsonPrimitive)) return false ;
        return true ;
    }

    public JsonObject getPayload() {
        return this.payload ;
    }

    private void put(String key,String value){
        payload.addProperty(key, value);
    }

    public void updateAttribute(String attributeName,JsonObject attributeJson){
        if(!isValidAttribute(attributeJson)){
            return ;
        }
        if(attributes.containsKey(attributeName)){
            JsonObject old = attributes.get(attributeName);
            for(Map.Entry<String,JsonElement> entry:attributeJson.entrySet()){
                old.add(entry.getKey(),entry.getValue());
            }
        }else{
            payload.getAsJsonArray("attributes").add(attributeJson);
            attributes.put(attributeName,attributeJson);
        }
    }

    public void updateTelemetries(String telematryName,JsonObject telemetryJson){
        if(telemetries.containsKey(telematryName)){
            JsonObject old = telemetries.get(telematryName);
            for(Map.Entry<String,JsonElement> entry:telemetryJson.entrySet()){
                old.add(entry.getKey(),entry.getValue());
            }
        }else{
            payload.getAsJsonArray("telemetries").add(telemetryJson);
            telemetries.put(telematryName,telemetryJson);
        }
    }

}
