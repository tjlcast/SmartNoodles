package com.tjlcast.transport;

import com.tjlcast.common.message.aware.SessionAwareMsg;
import com.tjlcast.common.message.device.DeviceRecognitionMsg;

/**
 * Created by tangjialiang on 2017/12/19.
 */
public interface SessionMsgProcessor {

    public void onMsg(DeviceRecognitionMsg msg) ;

    void process(SessionAwareMsg msg) ;
}
