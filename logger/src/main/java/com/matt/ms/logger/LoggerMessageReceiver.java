package com.matt.ms.logger;

import java.util.Date;

public class LoggerMessageReceiver {
	
	public void receiveMessage(String message) {
		System.out.println("LoggerMessageReceiver received message: " + new Date() + ": " + message);
		
		// TODO: logger 'logs'
	}
}
