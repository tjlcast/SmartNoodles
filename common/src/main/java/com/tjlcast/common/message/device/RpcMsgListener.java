package com.tjlcast.common.message.device;

/**
 * Created by tangjialiang on 2017/12/19.
 */
public interface RpcMsgListener {
    void onMsg(ToDeviceActorNotificationMsg msg);
}
