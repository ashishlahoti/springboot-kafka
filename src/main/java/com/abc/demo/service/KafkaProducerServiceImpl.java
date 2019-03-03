package com.abc.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public void send(String topic, String data) {
		kafkaTemplate.send(topic, data);
	}
}
