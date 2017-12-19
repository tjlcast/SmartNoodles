package com.tjlcast.server.controller;

import akka.actor.ActorRef;
import com.tjlcast.common.data.Device;
import com.tjlcast.server.actors.service.ActorService;
import com.tjlcast.server.actors.service.DefaultActorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tangjialiang on 2017/12/8.
 *
 */


@RestController
@RequestMapping("/api")
public class DeviceController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(DeviceController.class) ;

    @Autowired
    DefaultActorService actorService;

    @RequestMapping("/deviceg")
    public Device getDeviceById() {
        // todo
        logger.info("getDeviceById"); ;

        ActorRef appActor = actorService.appActor;

        appActor.tell(ActorService.Msg.get, ActorRef.noSender());

        return null ;
    }

    @RequestMapping("/devices")
    public Device saveDevie() {
        // todo
        logger.info("saveDevie") ;
        return null ;
    }

    @RequestMapping("/deviced")
    public Device deleteDevice() {
        // todo
        logger.info("deleteDevice") ;
        return null ;
    }

    @RequestMapping("/devicei")
    public List<Device> getDevicesByIds() {
        // todo
        logger.info("getDevicesByIds") ;
        return null ;
    }

    @RequestMapping("/devicet")
    public List<Device> getDeviceTypes() {
        // todo
        logger.info("getDeviceTypes") ;
        return null ;
    }

}
