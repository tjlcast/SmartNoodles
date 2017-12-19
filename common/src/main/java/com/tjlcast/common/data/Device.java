package com.tjlcast.common.data;

import lombok.Data;

import java.util.UUID;

/**
 * Created by tangjialiang on 2017/12/8.
 */
@Data
public class Device {
    private UUID id ; // system唯一id
    private UUID tenantId;
    private String manufacture;//厂商
    private String deviceType;//设备
    private String model;//型号
    private String parentDeviceId;//父设备id
}
