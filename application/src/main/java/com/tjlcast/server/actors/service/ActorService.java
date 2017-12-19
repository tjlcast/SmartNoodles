package com.tjlcast.server.actors.service;

import com.tjlcast.common.message.device.RpcMsgListener;
import com.tjlcast.transport.SessionMsgProcessor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by tangjialiang on 2017/12/8.
 *
 * 每个service的Actor消息接口，用于定义该service的actor的消息
 */

public interface ActorService extends SessionMsgProcessor, RpcMsgListener {

    public static enum Msg {
        get, post
    }

    public static class HttpResponse implements Serializable {
        @Setter
        @Getter
        public String response ;

        public HttpResponse(String response) {
            this.response = response ;
        }
    }
}
