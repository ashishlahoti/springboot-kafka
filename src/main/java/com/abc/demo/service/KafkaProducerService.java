package com.abc.demo.service;

public interface KafkaProducerService {

	public void send(String topic, String data);
}