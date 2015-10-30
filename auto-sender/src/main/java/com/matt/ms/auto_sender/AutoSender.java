package com.matt.ms.auto_sender;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class AutoSender {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private AtomicInteger messageCounter = new AtomicInteger();

	@Scheduled(fixedDelay = 1000L)
	public void send() {
		System.out.println("***** AutoSender sending message");
		this.rabbitTemplate.convertAndSend(Config.GATEWAY_EXCHANGE, null, 
				"hello from AutoSender, message id: " + messageCounter.incrementAndGet());
	}
}
