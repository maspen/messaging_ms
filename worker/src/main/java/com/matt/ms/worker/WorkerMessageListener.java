package com.matt.ms.worker;

import org.springframework.beans.factory.annotation.Autowired;

public class WorkerMessageListener {

	@Autowired
	ReceiverExchangeSender sender;
	
	public void receiveMessage(String message) {
		System.out.println("*** WorkerMessageListener received message: " + message);
		System.out.println("    passing to ReceiverExchangeSender ... ");
		sender.send(message);
	}
}
