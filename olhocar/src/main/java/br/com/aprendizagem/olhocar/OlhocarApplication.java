package br.com.aprendizagem.olhocar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.aprendizagem.principal.Principal;

@SpringBootApplication
public class OlhocarApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlhocarApplication.class, args);
		Principal main = new Principal();
		main.menu();
	}
	

}
