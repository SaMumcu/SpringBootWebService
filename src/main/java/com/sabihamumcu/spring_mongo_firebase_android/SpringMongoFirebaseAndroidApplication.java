package com.sabihamumcu.spring_mongo_firebase_android;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringMongoFirebaseAndroidApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoFirebaseAndroidApplication.class, args);
	}

}
