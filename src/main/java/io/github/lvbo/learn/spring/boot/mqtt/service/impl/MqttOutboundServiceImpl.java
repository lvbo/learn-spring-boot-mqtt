package io.github.lvbo.learn.spring.boot.mqtt.service.impl;

import io.github.lvbo.learn.spring.boot.mqtt.service.MqttOutboundService;
import io.github.lvbo.learn.spring.boot.mqtt.service.MyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttOutboundServiceImpl implements MqttOutboundService {

    @Autowired
    private MyGateway myGateway;

    @Override
    public void send(String message) {
        myGateway.sendToMqtt("topic1", "hello world");
    }
}
