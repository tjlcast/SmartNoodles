package com.tjlcast.server.actors.device;

import akka.event.LoggingAdapter;
import com.google.gson.JsonObject;
import com.tjlcast.common.data.DeviceShadow;
import com.tjlcast.common.message.device.DeviceRecognitionMsg;
import com.tjlcast.common.message.device.DeviceShadowMsg;
import com.tjlcast.server.actors.ActorSystemContext;
import com.tjlcast.server.actors.shared.AbstractContextAwareMsgProcessor;

import scala.concurrent.duration.Duration;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by tangjialiang on 2017/12/12.
 */
public class DeviceActorMessageProcessor extends AbstractContextAwareMsgProcessor {

    private final UUID deviceId;
    private final Map<UUID, UUID> sessions;
    private final Map<UUID, UUID> attributeSubscriptions;
    private final Map<UUID, UUID> rpcSubscriptions;

    private DeviceShadow deviceShadow;

    public DeviceActorMessageProcessor(ActorSystemContext systemContext, LoggingAdapter logger, UUID deviceId) {
        super(systemContext, logger);
        this.deviceId = deviceId;
        this.sessions = new HashMap<>();
        this.attributeSubscriptions = new HashMap<>();
        this.rpcSubscriptions = new HashMap<>();

        initAttributes();
    }

    private void initAttributes() {
//        Device device = systemContext.getDeviceService().findDeviceById(deviceId);
//        this.deviceName = device.getName();
//        this.deviceType = device.getType();
//        this.deviceAttributes = new DeviceAttributes(fetchAttributes(DataConstants.CLIENT_SCOPE),
//                fetchAttributes(DataConstants.SERVER_SCOPE), fetchAttributes(DataConstants.SHARED_SCOPE));
        // 初始化设备影子
        //TODO 初始化设备影子并开启心跳
        initDeviceShadow();
        systemContext.getScheduler().schedule(Duration.create(2000, TimeUnit.MILLISECONDS),
                Duration.create(2000, TimeUnit.MILLISECONDS), new Runnable() {
                    @Override
                    public void run() {
                        //TODO 发送心跳
                    }
                },systemContext.getActorSystem().dispatcher());

    }


    private void initDeviceShadow(){
//        Device device = systemContext.getDeviceService().findDeviceById(deviceId);
//        String manufacture = device.getManufacture();
//        String deviceType = device.getDeviceType();
//        String model = device.getModel();
//        if(StringUtil.checkNotNull(manufacture,deviceType,model)){
//            deviceShadow = HttpUtil.getDeviceShadowDoc(manufacture,deviceType,model);
//            deviceShadow.addProperty("deviceId",device.getId().toString());
//            //TODO send to service midware
//        }
    };

//    private void refreshAttributes(DeviceAttributesEventNotificationMsg msg) {
//        if (msg.isDeleted()) {
//            msg.getDeletedKeys().forEach(key -> deviceAttributes.remove(key));
//        } else {
//            deviceAttributes.update(msg.getScope(), msg.getValues());
//        }
//    }

    // todo 编写处理方法

    public void process(DeviceRecognitionMsg msg){
//        Device device = systemContext.getDeviceService().findDeviceById(deviceId);
//        String manufacture = msg.getManufacture();
//        String deviceType = msg.getDeviceType();
//        String model = msg.getModel();
//        if(StringUtil.checkNotNull(manufacture,deviceType,model)){
//            deviceShadow = HttpUtil.getDeviceShadowDoc(manufacture,deviceType,model);
//            deviceShadow.addProperty("deviceId",device.getId().toString());
//            //TODO send to service midware
//        }
    }

    public void processDeviceShadowMsg(DeviceShadowMsg msg){
        //TODO  deiceactor中处理数据http请求
        JsonObject payLoad = msg.getPayLoad();
        String methodName = payLoad.get("methodName").getAsString();
        if(methodName==null){
            msg.setResult("methodName is null");
        }else if(methodName.equals("get")){
            msg.setResult(deviceShadow.getPayload().toString());
        }else if(methodName.equals("updateAttribute")){
            JsonObject attribute = payLoad.get("attribute").getAsJsonObject();
            String attributeName = attribute.get("attributeName").getAsString();
            String attributeValue = attribute.get("attributeValue").getAsString();
//            KvEntry entry = new StringDataEntry(attributeName,attributeValue);
//            AttributeKvEntry attr = new BaseAttributeKvEntry(entry,System.currentTimeMillis());
//            List<AttributeKvEntry> ls = new ArrayList<AttributeKvEntry>();
//            ls.add(attr);
//            systemContext.getAttributesService().save(msg.getDeviceId(),"SERVER_SCOPE",ls);
            deviceShadow.updateAttribute(attribute.get("attributeName").getAsString(),attribute);
            msg.setResult(deviceShadow.getPayload().toString());
        }else{
            msg.setResult("Unrecognized methodName");
        }
    }
}
