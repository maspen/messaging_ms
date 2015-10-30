package com.matt.ms.worker;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class ReceiverExchangeSender {
	@Autowired
	private RabbitTemplate rabbitTemplate;

//	@Scheduled(fixedDelay = 1000L)
//	public void send() {
//		System.out.println("ReceiverExchangeSender sending message to \"" + Config.RECEIVER_EXCHANGE + "\" exchange");
//		this.rabbitTemplate.convertAndSend(Config.RECEIVER_EXCHANGE, null, "hello from ReceiverExchangeSender");
//	}
	
//	@Scheduled(fixedDelay = 1000L)
	public void send(String message) {
		System.out.println("ReceiverExchangeSender sending message to \"" + Config.RECEIVER_EXCHANGE + "\" exchange");
		this.rabbitTemplate.convertAndSend(Config.RECEIVER_EXCHANGE, null, "(via ReceiverExchangeSender)" + message);
	}
}
