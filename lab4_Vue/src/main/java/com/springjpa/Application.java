package com.springjpa;


import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;



@SpringBootApplication
public class Application extends org.springframework.boot.web.support.SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		go();
		SpringApplication.run(Application.class, args);
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
