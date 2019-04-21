package com.lvbo.learn.spring.boot.mqtt.service.impl;

import com.lvbo.learn.spring.boot.mqtt.service.MqttOutboundService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqttOutboundServiceTest {

    @Autowired
	private MqttOutboundService mqttOutboundService;

    @Test
    public void testSend() {
        mqttOutboundService.send("dd");
    }
}
