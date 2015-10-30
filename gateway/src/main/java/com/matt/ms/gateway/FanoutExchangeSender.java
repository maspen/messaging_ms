package com.matt.ms.gateway;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class FanoutExchangeSender {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
//	private AtomicInteger messageCounter = new AtomicInteger();

//	@Scheduled(fixedDelay = 1000L)
//	public void send() {
//		System.out.println("FanoutExchangeSender sending message to \"" + Config.FANOUT_EXCHANGE + "\" exchange");
//		this.rabbitTemplate.convertAndSend(Config.FANOUT_EXCHANGE, null, "hello from FanoutExchangeSender, message id: " + messageCounter.incrementAndGet());
//	}
	
//	@Scheduled(fixedDelay = 1000L)
	public void send(String message) {
		System.out.println("***** FanoutExchangeSender sending message to \"" + Config.FANOUT_EXCHANGE + "\" exchange");
		this.rabbitTemplate.convertAndSend(Config.FANOUT_EXCHANGE, null, "(via FanoutExchangeSender)" + message);
	}
}
