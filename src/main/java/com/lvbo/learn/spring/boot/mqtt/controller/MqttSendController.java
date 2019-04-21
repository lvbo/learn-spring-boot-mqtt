package com.lvbo.learn.spring.boot.mqtt.controller;

import com.lvbo.learn.spring.boot.mqtt.service.MqttOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttSendController {

    @Autowired
    private MqttOutboundService mqttOutboundService;

    @PostMapping(value = "/message")
    public void sendMesage() {
        mqttOutboundService.send("helloo");
    }
}
