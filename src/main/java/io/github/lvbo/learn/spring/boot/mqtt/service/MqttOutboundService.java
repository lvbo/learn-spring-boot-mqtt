package io.github.lvbo.learn.spring.boot.mqtt.service;

public interface MqttOutboundService {

    void send(String message);
}
