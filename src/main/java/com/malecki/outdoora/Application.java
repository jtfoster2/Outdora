package com.malecki.outdoora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
// There is a bug here where this has to be in java for java controllers to get picked up.
// Otherwise, only the .kt file controllers will be detected.
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(KotlinApplicationStarter.class, args);
	}
}