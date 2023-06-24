package com.cda.freely;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing     /**signale à spring l'auto-generation de la bdd*/
@ComponentScan({ "com.cda.freely.*" })
public class FreelyApplication {

	public static void main(String[] args) {
		/**
		 * Chargement des variables d'env au démarrage de l'application.
		 *la méthode entries() permet d'obtenir un ensemble d'entrées de la configuration.
		 * la méthode System.setProperty() pour définir chaque entrée comme une propriété système.
		 */
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(FreelyApplication.class, args);
	}

	/**
	 * @PostConstruct lance la méthode après l'initialisation de spring.
	 * @init() cette méthode définit le fuseau horaire à UTC afin que l'application
	 * s'adapte à l'heure du système.
	 * */
	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
