package br.com.sic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.sic.modelo"})
@EnableJpaRepositories(basePackages = {"br.com.sic.repositorio"})
@WebAppConfiguration
public class SicRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SicRestApplication.class, args);
	}
}
