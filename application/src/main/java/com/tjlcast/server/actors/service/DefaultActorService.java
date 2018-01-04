package com.tjlcast.server.actors.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import com.tjlcast.common.message.aware.SessionAwareMsg;
import com.tjlcast.common.message.device.DeviceRecognitionMsg;
import com.tjlcast.common.message.device.ToDeviceActorNotificationMsg;
import com.tjlcast.server.actors.ActorSystemContext;
import com.tjlcast.server.actors.app.AppActor;
import com.tjlcast.server.actors.session.SessionManagerActor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by tangjialiang on 2017/12/8.
 *
 * the default world of actor.
 *
 * 由Spring装载启动，之后开始对Actor世界（环境）进行配置:
 * 1、AppActor
 * 2、SessionManagerActor
 * 完成后放入ActorSystemContext.
 */

@Service
@Slf4j
public class DefaultActorService implements ActorService {

    private static final String ACTOR_SYSTEM_NAME = "akka" ; // default is Akka

    public static final String APP_DISPATCHER_NAME = "app-dispatcher";
    public static final String CORE_DISPATCHER_NAME = "core-dispatcher";
    public static final String SESSION_DISPATCHER_NAME = "session-dispatcher" ;

    @Autowired
    private ActorSystemContext actorContext ;

    private ActorSystem system ;

    public ActorRef appActor ;

    private ActorRef sessionManagerActor ;

    @PostConstruct
    public void initActorSystem() {
        /**
         * 该处为Actor World的初始入口
         */
        log.info("initializing Actor System.{}", actorContext) ;
        system = ActorSystem.create(ACTOR_SYSTEM_NAME, actorContext.getConfig()) ;
        actorContext.setActorSystem(system);

        // prepare for app actor.
        log.info("initializing Actor appActor.") ;
        appActor = system.actorOf(Props.create(new AppActor.ActorCreator(actorContext)).withDispatcher(APP_DISPATCHER_NAME), "appActor") ;
        actorContext.setAppActor(appActor);

        // prepare for sessionManagerActor actors.
        log.info("initializing Actor sessionManagerActor.") ;
        sessionManagerActor = system.actorOf(Props.create(new SessionManagerActor.ActorCreator(actorContext)).withDispatcher(CORE_DISPATCHER_NAME), "sessionManagerActor") ;
        actorContext.setSessionManagerActor(sessionManagerActor);
    }

    @PreDestroy
    public void stopActorSystem() {
        Future<Terminated> status = system.terminate();
        try {
            Terminated result = Await.result(status, Duration.Inf());
        } catch (Exception e) {
            log.error("Fail to terminate actor system.", e) ;
        }
    }

    @Override
    public void onMsg(DeviceRecognitionMsg msg) {
        log.trace("Processing broadcast rpc msg: {}", msg);
        appActor.tell(msg, ActorRef.noSender());
    }

    @Override
    public void process(SessionAwareMsg msg) {
        log.debug("Processing session aware msg: {}", msg);
        // todo
        // sessionManagerActor.tell(msg, ActorRef.noSender());
    }

    @Override
    public void onMsg(ToDeviceActorNotificationMsg msg) {
        log.trace("Processing notification rpc msg: {}", msg);
        appActor.tell(msg, ActorRef.noSender());
    }
}
