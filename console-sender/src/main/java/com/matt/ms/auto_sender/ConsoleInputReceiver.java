package com.matt.ms.auto_sender;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ConsoleInputReceiver extends Thread {

	@Autowired
	ConsoleSender consoleSender;
	
	@Override
	public void run() {
		Scanner consoleScanner = new Scanner(System.in);
		String inputString;
		
		System.out.println("type in some text");
		System.out.println("enter 'stop' to quit");
	
		do {
			inputString = consoleScanner.nextLine();
			// NOTE: 'stop' will also be sent ... this can be a
			// trigger for downstream apps to indicate there is no more input
			consoleSender.send(inputString);
		} while(!inputString.equals("stop"));
		
		System.out.println("bye ...");
		
		consoleScanner.close();
	}
}
