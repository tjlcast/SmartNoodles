package com.tjlcast.server.actors.tenant;


import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.tjlcast.common.message.device.DeviceRecognitionMsg;
import com.tjlcast.common.message.device.ToDeviceActorNotificationMsg;
import com.tjlcast.server.actors.ActorSystemContext;
import com.tjlcast.server.actors.device.DeviceActor;
import com.tjlcast.server.actors.service.ContextAwareActor;
import com.tjlcast.server.actors.service.ContextBasedCreator;
import com.tjlcast.server.actors.service.DefaultActorService;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by tangjialiang on 2017/12/8.
 *
 */

public class TenantActor extends ContextAwareActor {

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this) ;

    private final UUID tenantId ;
    private final Map<UUID, ActorRef> deviceActors ;

    public TenantActor(ActorSystemContext context, UUID tenantId) {
        super(context) ;
        this.tenantId = tenantId;
        deviceActors = new HashMap<UUID, ActorRef>() ;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ToDeviceActorNotificationMsg) {
            onToDeviceActorMsg((ToDeviceActorNotificationMsg) message);
        } else if(message instanceof DeviceRecognitionMsg){
            getOrCreateDeviceActor(((DeviceRecognitionMsg) message).getDeviceId()).tell(message,ActorRef.noSender());
        }
    }

    private ActorRef getOrCreateDeviceActor(final UUID deviceId) {
        return deviceActors.computeIfAbsent(
                deviceId,
                k -> context().actorOf(Props.create(new DeviceActor.ActorCreator(systemContext, tenantId, deviceId)).withDispatcher(DefaultActorService.CORE_DISPATCHER_NAME),
                        deviceId.toString())
        ) ;
    }

    public static class ActorCreator extends ContextBasedCreator<TenantActor> {
        private static final long serialVersionUID = 1L ;

        private final UUID tenantId ;

        public ActorCreator(ActorSystemContext context, UUID tenantId) {
            super(context);
            this.tenantId = tenantId ;
        }

        @Override
        public TenantActor create() throws  Exception {
            return new TenantActor(context, tenantId) ;
        }
    }

    private void onToDeviceActorMsg(ToDeviceActorNotificationMsg msg) {
        getOrCreateDeviceActor(msg.getDeviceId()).tell(msg, ActorRef.noSender());
    }
}
