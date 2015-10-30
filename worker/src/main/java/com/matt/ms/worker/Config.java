package com.matt.ms.worker;

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
	
	static final String WORKER_QUEUE = "worker_q";
	static final String FANOUT_EXCHANGE = "fanout_exchange";
	
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
	
	// ***** receives messages from worker_q (bound to 'fanout exchange')
	@Bean
	public Queue workerQueue() {
		return new Queue(WORKER_QUEUE);
	}
	
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE);
	}
	
	@Bean
	public Binding fanoutBinding() {
		return BindingBuilder.bind(workerQueue()).to(fanoutExchange());
	}
	
	// ***** sends messages to receiver_exchange (other app(s) will bind to)
	@Bean
	public FanoutExchange receiverExchange() {
		return new FanoutExchange(RECEIVER_EXCHANGE);
	}
	
	// create listener
	@Bean
	public SimpleMessageListenerContainer gatewayListenerContainer(ConnectionFactory connectionFactory, 
			@Qualifier("workerQueueListenerAdapter") MessageListenerAdapter listenerAdapter) {
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(workerQueue()); // can add multiple queues here
        container.setMessageListener(listenerAdapter);
        return container;
	}
	
	@Bean
	public MessageListenerAdapter workerQueueListenerAdapter(WorkerMessageListener receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
	
	@Bean
	public WorkerMessageListener workerQueueListener() {
		return new WorkerMessageListener();
	}
	
	@Bean
	public ReceiverExchangeSender receiverExchangeSender() {
		return new ReceiverExchangeSender();
	}
}
