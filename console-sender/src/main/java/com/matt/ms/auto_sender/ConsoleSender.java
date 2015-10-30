package com.matt.ms.auto_sender;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class ConsoleSender {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private AtomicInteger messageCounter = new AtomicInteger();

	public void send(String message) {
		System.out.println("***** ConsoleSender sending message");
		this.rabbitTemplate.convertAndSend(Config.GATEWAY_EXCHANGE, null, 
				message + " id: " + messageCounter.incrementAndGet());
	}
//
//	@Override
//	public void run() {
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		String inputString;
//		
//		System.out.println("type in some text");
//		System.out.println("enter 'stop' to quit");
//		
//		do {
//			inputString = null;
//			try {
//				inputString = reader.readLine();
//			} catch (IOException e) {
//				System.err.println("exception reading console input");
//			}
//			send(inputString);
//		} while(!inputString.equals("stop"));
//	}
}
