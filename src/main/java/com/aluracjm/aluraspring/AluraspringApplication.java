package com.aluracjm.aluraspring;
import com.aluracjm.aluraspring.principal.Principal;
import com.aluracjm.aluraspring.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;


@SpringBootApplication
public class AluraspringApplication implements CommandLineRunner {

	@Autowired
	private SerieRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(AluraspringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.showMenu();
	}
}
