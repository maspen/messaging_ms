package com.matt.ms.auto_sender;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class Config {
	static final String HOST = "localhost";
	
	static final String GATEWAY_EXCHANGE = "gateway_fanout_exchange";
	
	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory(HOST);
	}
	
	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate() {
		return new RabbitTemplate(connectionFactory());
	}
	
	@Bean
	public FanoutExchange gatewayExchange() {
		return new FanoutExchange(GATEWAY_EXCHANGE);
	}
	
	@Bean
	public AutoSender autoSender() {
		return new AutoSender();
	}
}
