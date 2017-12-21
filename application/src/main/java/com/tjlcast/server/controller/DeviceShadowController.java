package com.tjlcast.server.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tjlcast.common.data.Device;
import com.tjlcast.common.data.DeviceService;
import com.tjlcast.common.message.device.DeviceShadowMsg;
import com.tjlcast.server.actors.service.ActorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * Created by tangjialiang on 2017/12/19.
 */


@RestController
@RequestMapping("/api/shadow")
@Slf4j
public class DeviceShadowController {

    @Autowired
    protected ActorService actorService;

    @RequestMapping(value = "/testDevice", method = RequestMethod.POST)
    public DeferredResult<String> shadow(@RequestBody String jsonStr){
        DeferredResult<String> result = new DeferredResult<>();

        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonStr).getAsJsonObject();
        Device device = Device.getDeviceRandom();

        DeviceShadowMsg msg = new DeviceShadowMsg(device, json,result);

        actorService.onMsg(msg);
        return result;
    }

    @RequestMapping(value = "/callable", method = RequestMethod.GET, produces = "text/html")
    public Callable<String> executeSlowTask() {
        log.info("Request received");
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                return "hello";
            }
        } ;
        log.info("Servlet thread released");

        return callable;
    }
}
