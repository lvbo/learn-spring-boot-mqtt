package com.lvbo.learn.spring.boot.mqtt.example;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

/**
 * 建立mqtt连接
 *
 * @author hupx-007558
 * @version 1.0.0
 */
public class MqttAsynService {
	
	private static MqttAsyncClient  client = null;
	private static  MqttConnectOptions ops = null;

	public MqttAsynService() {
		initAsynMqttClient();
	}

	public static  void initAsynMqttClient(){
		try {
			String host = "tcp://127.0.0.1:1883";
			String clientId = UUID.randomUUID().toString();
			if(client == null){
				client = new MqttAsyncClient(host, clientId, new MemoryPersistence());
			}
			ops = getMqttConnectOptions();
			client.setCallback(new MyMqttCallback());
//			IMqttToken token = client.connect(ops, null, new MqttConnctActionLisener(client, ops));
            client.connect("some context", new MqttConnctActionLisener());
		} catch (MqttException e) {
		    e.printStackTrace();
		}
	}
	
	private static MqttConnectOptions getMqttConnectOptions() {
		MqttConnectOptions ops = new MqttConnectOptions();
		ops.setUserName("root");
		ops.setPassword("root123".toCharArray());
		ops.setCleanSession(true);
		ops.setAutomaticReconnect(false);
//		ops.setMaxInflight(30);
		return ops;
	}

	public static void publishMessage(String topic, String message){
		try {
			client.publish(topic, message.getBytes(), 1, false);
		} catch (MqttPersistenceException e) {
		    e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}


	static class MqttConnctActionLisener implements IMqttActionListener {


		public void onSuccess(IMqttToken asyncActionToken) {
			try {
				client.subscribe("mqtt/test", 1);
			} catch (MqttException e) {
			}
		}

		public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
			try {
				while(!client.isConnected()){
					client.connect(ops);
					Thread.sleep(5000);
				}
				client.subscribe("mqtt/test",1);
			} catch (MqttSecurityException e) {
			} catch (MqttException e) {
			}catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

	}


	public static void main(String[] args) throws InterruptedException {
		MqttAsynService mqttAsynService = new MqttAsynService();
		Thread.sleep(2000L);
		for (int i = 0; i < 100; i ++) {
			mqttAsynService.publishMessage("mqtt/test", "dd");
		}
	}
}
