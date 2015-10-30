package com.matt.ms.gateway;

import org.springframework.beans.factory.annotation.Autowired;

public class GatewayQueueListener {
	
	@Autowired
	FanoutExchangeSender fanoutExchangeSender;

	public void receiveMessage(String message) {
		System.out.println("***** GatewayQueueListener received message: " + message);
		System.out.println("      passing to FanoutExchangeSender");
		fanoutExchangeSender.send(message);
	}
}
