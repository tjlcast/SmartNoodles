package com.tjlcast.server.actors.session;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.tjlcast.server.actors.ActorSystemContext;
import com.tjlcast.server.actors.service.ContextAwareActor;
import com.tjlcast.server.actors.service.ContextBasedCreator;
import com.tjlcast.server.actors.service.DefaultActorService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangjialiang on 2017/12/8.
 *
 *  SessionManagerActor in the world of actor.
 */

public class SessionManagerActor extends ContextAwareActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this) ;

    private Map<String, ActorRef> sessionActors ;

    public SessionManagerActor(ActorSystemContext context) {
        super(context) ;
        sessionActors = new HashMap<String, ActorRef>() ;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        // todo
    }

    private ActorRef getOrCreateSessionActor(String sessionId) {
        ActorRef sessionActor = sessionActors.get(sessionId) ;
        if (sessionActor == null) {
            log.debug("[{}] Creating session actor.", sessionId);
            sessionActor = context().actorOf(
                    Props.create(new SessionActor.ActorCreator(systemContext, sessionId)).withDispatcher(DefaultActorService.SESSION_DISPATCHER_NAME),
                    sessionId) ;
            sessionActors.put(sessionId, sessionActor) ;
            log.debug("[{}] Created session actor.", sessionId);
        }
        return sessionActor ;
    }

    // for creating the sessionManagerActor
    public static class ActorCreator extends ContextBasedCreator<SessionManagerActor> {

        public ActorCreator(ActorSystemContext context) {
            super(context);
        }

        @Override
        public SessionManagerActor create() throws Exception {
            return new SessionManagerActor(context);
        }
    }
}
