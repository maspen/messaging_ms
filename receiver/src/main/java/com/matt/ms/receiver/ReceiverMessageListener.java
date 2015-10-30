package com.matt.ms.receiver;

public class ReceiverMessageListener {
	
	public void receiveMessage(String message) {
		System.out.println("***** ReceiverMessageListener received message: " + message);
	}
}
