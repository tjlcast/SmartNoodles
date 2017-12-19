package com.tjlcast.server.controller;

import com.tjlcast.server.actors.service.ActorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tangjialiang on 2017/12/8.
 */

@Slf4j
public class BaseController {

    @Autowired
    protected ActorService actorService ;
}
