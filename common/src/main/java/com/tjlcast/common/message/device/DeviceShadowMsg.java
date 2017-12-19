package com.tjlcast.common.message.device;

import com.google.gson.JsonObject;
import com.tjlcast.common.data.Device;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;

/**
 * Created by tangjialiang on 2017/12/19.
 */
public class DeviceShadowMsg implements ToDeviceActorNotificationMsg {

    private final Device device;
    private final JsonObject payLoad;
    private final DeferredResult<String> result; // Callable 和 Spring DeferredResult

    public DeviceShadowMsg(Device device, JsonObject json, DeferredResult<String> result) {
        this.device = device ;
        this.payLoad = json ;
        this.result = result ;
    }

    @Override
    public UUID getTenantId() {
        return device.getTenantId();
    }

    @Override
    public UUID getDeviceId() {
        return device.getId();
    }

    public JsonObject getPayLoad(){
        return payLoad;
    }

    public void setResult(String res){
        result.setResult(res);
    }
}
