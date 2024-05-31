package com.aluracjm.aluraspring;
import com.aluracjm.aluraspring.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AluraspringApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AluraspringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.showMenu();

	}
}
