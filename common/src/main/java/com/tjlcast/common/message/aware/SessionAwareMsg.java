package com.tjlcast.common.message.aware;

import java.util.UUID;

/**
 * Created by tangjialiang on 2017/12/19.
 */
public interface SessionAwareMsg {
    UUID getSessionId();
}
