package com.lvbo.learn.spring.boot.mqtt.example;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PublishAyncSample {
    public static void main(String[] args) {

        String topic = "mqtt/test";
        int qos = 1;
        String broker = "tcp://127.0.0.1:1883";
        String userName = "root";
        String password = "root123";
        String clientId = "pubClient";
        // 内存存储
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            // 创建客户端
            MqttAsyncClient sampleClient = new MqttAsyncClient(broker, clientId, persistence);
            // 创建链接参数
            MqttConnectOptions connOpts = new MqttConnectOptions();
            // 在重新启动和重新连接时记住状态
            connOpts.setCleanSession(false);
            // 设置连接的用户名
            connOpts.setUserName(userName);
            connOpts.setPassword(password.toCharArray());
            // 建立连接
            IMqttToken iMqttToken = sampleClient.connect(connOpts);
            while (!iMqttToken.isComplete()) {
                try {
                    Thread.sleep(20L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i <= 100; i++) {
                // 创建消息
                String content = "hello 哈哈" + i;
                MqttMessage message = new MqttMessage(content.getBytes());
                // 设置消息的服务质量
                message.setQos(qos);
                // 发布消息
                IMqttDeliveryToken deliveryToken = sampleClient.publish(topic, message);
                while (!deliveryToken.isComplete()) {
                    System.out.println(new String(deliveryToken.getMessage().getPayload()));
                    try {
                        Thread.sleep(20L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 断开连接
            sampleClient.disconnect();
            // 关闭客户端
            sampleClient.close();
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}
