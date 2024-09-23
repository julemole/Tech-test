package com.litethinking.Inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.litethinking.Inventario.repository")
public class InventarioApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventarioApplication.class, args);
	}

}
