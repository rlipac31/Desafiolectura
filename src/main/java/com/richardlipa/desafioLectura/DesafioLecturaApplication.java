package com.richardlipa.desafioLectura;

import com.richardlipa.desafioLectura.Principal.Principal;
import com.richardlipa.desafioLectura.Repository.LibroRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioLecturaApplication implements CommandLineRunner {

	// Inyecta la instancia de Principal (ya que ahora es un @Component)
	@Autowired
	private Principal principal;

	public static void main(String[] args) {
		SpringApplication.run(DesafioLecturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Principal principal = new Principal();
		principal.mostrarMenu();
	}
}



