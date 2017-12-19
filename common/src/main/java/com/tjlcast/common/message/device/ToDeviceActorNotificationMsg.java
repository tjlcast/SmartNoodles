package com.tjlcast.common.message.device;


import com.tjlcast.common.message.aware.DeviceAwareMsg;
import com.tjlcast.common.message.aware.TenantAwareMsg;

import java.io.Serializable;

/**
 * @author Andrew Shvayka
 */
public interface ToDeviceActorNotificationMsg extends TenantAwareMsg, DeviceAwareMsg, Serializable {

}
