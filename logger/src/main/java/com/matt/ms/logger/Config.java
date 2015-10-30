package com.matt.ms.logger;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class Config {
	static final String HOST = "localhost";
	
	static final String FANOUT_EXCHANGE = "fanout_exchange";
	static final String LOGGER_QUEUE = "logger_q";
	
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
	
	// ***** fanout exchange and logger queue
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE);
	}
	
	@Bean
	public Queue loggerQueue() {
		return new Queue(LOGGER_QUEUE);
	}
	
	@Bean
	public Binding fanoutBinding() {
		return BindingBuilder.bind(loggerQueue()).to(fanoutExchange());
	}
	
	@Bean
	public SimpleMessageListenerContainer gatewayListenerContainer(ConnectionFactory connectionFactory, @Qualifier("loggerQueueListenerAdapter") MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(loggerQueue());
        container.setMessageListener(listenerAdapter);
        return container;
	}
	
	@Bean
	public MessageListenerAdapter loggerQueueListenerAdapter(LoggerMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
	
	@Bean
	public LoggerMessageReceiver loggerMessageReceiver() {
		return new LoggerMessageReceiver();
	}
}
