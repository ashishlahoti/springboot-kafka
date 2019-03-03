package com.abc.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.config.SslConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.abc.demo.service.CryptoService;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Autowired
	private ConsumerFactory<Integer, String> consumerFactory;
	
	@Autowired
	private CryptoService cryptoService;
	
	public Map<String, Object> consumerConfig(){
		Map<String, Object> kafkaAutoConfig = ((DefaultKafkaConsumerFactory<Integer, String>) consumerFactory).getConfigurationProperties();
		Map<String, Object> consumerConfig = new HashMap<>();
		consumerConfig.putAll(kafkaAutoConfig);
		consumerConfig.compute(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, (k,v) -> cryptoService.decrypt((String)v));
		consumerConfig.compute(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, (k,v) -> cryptoService.decrypt((String)v));
		consumerConfig.compute(SslConfigs.SSL_KEY_PASSWORD_CONFIG, (k,v) -> cryptoService.decrypt((String)v));
		return consumerConfig;
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(consumerConfig()));
		return factory;
	}
}
