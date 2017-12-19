package com.tjlcast.common.message.device;

import com.tjlcast.common.data.Device;
import com.tjlcast.common.message.aware.DeviceAwareMsg;
import com.tjlcast.common.message.aware.TenantAwareMsg;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by tangjialiang on 2017/12/19.
 */

@ToString
public class DeviceRecognitionMsg implements TenantAwareMsg, DeviceAwareMsg, Serializable{

    private final String manufacture;
    private final String deviceType;
    private final String model;
    private final Device device;

    public DeviceRecognitionMsg(String manufacture, String deviceType, String model,Device device) {
        this.manufacture = manufacture;
        this.deviceType = deviceType;
        this.model = model;
        this.device = device;
    }

    @Override
    public UUID getDeviceId() {
        return device.getId();
    }

    @Override
    public UUID getTenantId() {
        return device.getTenantId();
    }

    public String getManufacture() {
        return manufacture;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getModel() {
        return model;
    }
}
