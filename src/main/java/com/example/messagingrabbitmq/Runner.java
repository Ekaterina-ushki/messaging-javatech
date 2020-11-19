package com.example.messagingrabbitmq;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;


	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n\n\nEnter the message or wait for it");
		while(true) {
			String message = sc.nextLine();
			System.out.println(">>Sending message...");
			rabbitTemplate.convertAndSend(
					MessagingRabbitmqApplication.exchangeName,
					"#",
					message);
			receiver.getLatch().await(1000, TimeUnit.MILLISECONDS);
		}
	}
}
