package com.tjlcast.server.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Scheduler;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Created by tangjialiang on 2017/12/8.
 *
 * 由Spring进行反转控制，加载Actor的相关配置（通过构造函数）。
 *
 * 整个系统需要Akka世间中的以下概念体：
 * | ActorSystem -> actorSystem 整个actor世界
 * | ActorRef -> appActor 应用actor
 * | ActorRef -> sessionManagerActor 应用的对外actor
 */

@Component
public class ActorSystemContext {

    private static final String AKKA_CONF_FILE_NAME = "actor-system.conf" ;

    @Getter
    @Setter
    private ActorSystem actorSystem ;

    @Getter
    @Setter
    private ActorRef appActor ;

    @Getter
    @Setter
    private ActorRef sessionManagerActor ;

    @Getter
    @Setter
    private final Config config ;

    public ActorSystemContext() {
        this.config = ConfigFactory.parseResources(AKKA_CONF_FILE_NAME) ;
    }

    public Scheduler getScheduler() {
        return actorSystem.scheduler() ;
    }
}
