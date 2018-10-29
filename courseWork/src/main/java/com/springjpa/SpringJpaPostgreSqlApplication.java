package com.springjpa;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.springjpa.RabbitMQ.Receiver;
import com.springjpa.controller.*;
import com.springjpa.models.*;
import com.springjpa.telegramBot.TestBot;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;


@EnableScheduling
@SpringBootApplication
@EnableJms
public class SpringJpaPostgreSqlApplication extends org.springframework.boot.web.support.SpringBootServletInitializer  {


	public final static String queueName = "spring-boot";

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("spring-boot-exchange");
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
											 MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringJpaPostgreSqlApplication.class);
	}

	@Bean
	public JmsListenerContainerFactory<?> myFactory(javax.jms.ConnectionFactory connectionFactory,
													DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	public static ConfigurableApplicationContext context;

	@Autowired
	static UserController userController;


	public static void main(String[] args) {


		go();


		context = SpringApplication.run(SpringJpaPostgreSqlApplication.class, args);


		ApiContextInitializer.init();

		TelegramBotsApi botsApi = new TelegramBotsApi();

		try {
			botsApi.registerBot(new TestBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}



	}


	public static void go() {
		String user = "";
		String password = "";
		String host = "se.ifmo.ru";
		int port = 2222;
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, port); //создаем сессию
			int lport = 2222;
			String rhost = "192.168.10.99";
			int rport = 5432;
			session.setPassword(password); //выставляем пароль
			session.setConfig("StrictHostKeyChecking", "no"); //выставляем конфиги подключения
			System.out.println("Establishing Connection...");
			session.connect();
			session.setPortForwardingL(lport, rhost, rport); //форвардим порты
		} catch (Exception e) {
			System.err.println("Connection to Database is not established.");
			System.err.println("Can not connect to \'helios.cs.ifmo.ru\'.");
			e.printStackTrace();
			System.exit(0);
		}

	}



}
