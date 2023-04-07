package com.cda.freely;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class FreelyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreelyApplication.class, args);
	}

	/**
	 * @PostConstruct lance la méthode après l'initialisation de spring.
	 * @init() cette méthode définit le fuseau horaire à UTC afin que l'application 
	 * s'adapte à l'heure du système
	 * */
	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
