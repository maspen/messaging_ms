package com.matt.ms.receiver;

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
	
	static final String RECEIVER_QUEUE = "receiver_q";
	static final String RECEIVER_EXCHANGE = "receiver_exchange";
	
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
	
	// ***** bind 'receiver_q' to 'receiver_exchange'
	@Bean
	public Queue receiverQueue() {
		return new Queue(RECEIVER_QUEUE);
	}
	
	public FanoutExchange receiverExchange() {
		return new FanoutExchange(RECEIVER_EXCHANGE);
	}
	
	@Bean
	public Binding fanoutBinding() {
		return BindingBuilder.bind(receiverQueue()).to(receiverExchange());
	}
	
	// create listener
	@Bean
	public SimpleMessageListenerContainer gatewayListenerContainer(ConnectionFactory connectionFactory, 
			@Qualifier("receiverQueueListenerAdapter") MessageListenerAdapter listenerAdapter) {
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(receiverQueue()); // can add multiple queues here
        container.setMessageListener(listenerAdapter);
        return container;
	}
	
	@Bean
	public MessageListenerAdapter receiverQueueListenerAdapter(ReceiverMessageListener receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
	
	@Bean
	public ReceiverMessageListener receiverQueueListener() {
		return new ReceiverMessageListener();
	}
}
