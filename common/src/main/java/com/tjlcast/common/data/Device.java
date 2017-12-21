package com.tjlcast.common.data;

import lombok.Data;

import java.util.Random;
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

    public Device(UUID id, UUID tenantId, String manufacture, String deviceType, String model, String parentDeviceId) {
        this.id = id ;
        this.tenantId = tenantId ;
        this.manufacture = manufacture ;
        this.deviceType = deviceType ;
        this.model = model ;
        this.parentDeviceId = parentDeviceId ;
    }

    public static Device getDeviceRandom() {
        String[] manufactures = {"Baidu", "Ali", "Tencent", "Bupt"} ;
        String[] deviceTypes = {"phone", "computer", "car", "ipad"} ;
        String[] models = {"T800", "T900"} ;

        UUID id = UUID.randomUUID() ;
        UUID tenantId = UUID.randomUUID() ;
        String manufacture = manufactures[Math.abs(new Random().nextInt()) % manufactures.length] ;
        String deviceType = deviceTypes[Math.abs(new Random().nextInt()) % deviceTypes.length] ;
        String model = models[Math.abs(new Random().nextInt()) % models.length] ;
        String parentDeviceId = null ;

        return new Device(id, tenantId, manufacture, deviceType, model, parentDeviceId) ;
    }
}
