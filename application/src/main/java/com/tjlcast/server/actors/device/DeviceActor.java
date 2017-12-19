package com.tjlcast.server.actors.device;

import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.tjlcast.common.message.device.DeviceRecognitionMsg;
import com.tjlcast.server.actors.ActorSystemContext;
import com.tjlcast.server.actors.service.ContextAwareActor;
import com.tjlcast.server.actors.service.ContextBasedCreator;

import java.util.UUID;

/**
 * Created by tangjialiang on 2017/12/8.
 */
public class DeviceActor extends ContextAwareActor {

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this) ;

    private final UUID tenantId ;
    private final UUID deviceId ;
    private final DeviceActorMessageProcessor processor;

    private DeviceActor(ActorSystemContext context, UUID tenantId, UUID deviceId) {
        super(context) ;
        this.tenantId = tenantId ;
        this.deviceId = deviceId ;
        this.processor = new DeviceActorMessageProcessor(systemContext, logger, deviceId);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof DeviceRecognitionMsg){
            processor.process((DeviceRecognitionMsg)message);
        }
    }

    public static class ActorCreator extends ContextBasedCreator<DeviceActor> {
        private static final long serialVersionUID = 1L ;

        private final UUID tenantId ;
        private final UUID deviceId ;

        public ActorCreator(ActorSystemContext context, UUID tenantId, UUID deviceId) {
            super(context) ;
            this.tenantId = tenantId ;
            this.deviceId = deviceId ;
        }

        @Override
        public DeviceActor create() throws Exception {
            return new DeviceActor(context, tenantId, deviceId) ;
        }
    }
}
