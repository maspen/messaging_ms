package com.matt.ms.gateway;

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
	
	static final String GATEWAY_FANOUT_EXCHANGE = "gateway_fanout_exchange";
	static final String GATEWAY_QUEUE = "gateway_q";
	
	/** Sends messages to exchange to which (separate) Logger & Worker 
	 * services will be started to listen on queues logger_q & worker_q
	 * respectively. */
	static final String FANOUT_EXCHANGE = "fanout_exchange";
	
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
	
	// ***** gateway exchange and queue
	@Bean
	public FanoutExchange gatewayExchange() {
		return new FanoutExchange(GATEWAY_FANOUT_EXCHANGE);
	}
	
	@Bean
	public Queue gatewayQueue() {
		return new Queue(GATEWAY_QUEUE);
	}

	@Bean
	public Binding gatewayBinding() {
		return BindingBuilder.bind(gatewayQueue()).to(gatewayExchange());
	}

	// ***** 'fanout exchange' (NOTE: this just declares the exchange. Other
	// apps will declare the queues & bind them to this exchange)
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE);
	}
	
	// create listener
	@Bean
	public SimpleMessageListenerContainer gatewayListenerContainer(ConnectionFactory connectionFactory, 
			@Qualifier("gatewayQueueListenerAdapter") MessageListenerAdapter listenerAdapter) {
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //container.setQueues(trashRouteQueue(), webAppQueue()); can add multiple queues here
        container.setQueues(gatewayQueue());
        container.setMessageListener(listenerAdapter);
        return container;
	}
	
	@Bean
	public MessageListenerAdapter gatewayQueueListenerAdapter(GatewayQueueListener receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
	
	@Bean
	public GatewayQueueListener gatewayQueueListener() {
		return new GatewayQueueListener();
	}
	
	@Bean
	public FanoutExchangeSender fanoutExchangeSender() {
		return new FanoutExchangeSender();
	}
}
