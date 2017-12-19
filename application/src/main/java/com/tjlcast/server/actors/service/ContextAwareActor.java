package com.tjlcast.server.actors.service;

import akka.actor.UntypedActor;
import com.tjlcast.server.actors.ActorSystemContext;

/**
 * Created by tangjialiang on 2017/12/8.
 *
 * 一个拥有ActorSystemContext的Actor接口
 */

public abstract class ContextAwareActor extends UntypedActor {

    protected ActorSystemContext systemContext ;

    public ContextAwareActor(ActorSystemContext systemContext) {
        super() ;
        this.systemContext = systemContext ;
    }
}
