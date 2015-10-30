package com.matt.ms.auto_sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		
		ConsoleInputReceiver consoleInputReceiver = (ConsoleInputReceiver) context.getBean(ConsoleInputReceiver.class);
		consoleInputReceiver.start();
	}
}
