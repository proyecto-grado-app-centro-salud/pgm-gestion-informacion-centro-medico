package com.example.microservicio_informacion_centro_medico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MicroservicioInformacionCentroMedicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioInformacionCentroMedicoApplication.class, args);
	}

}
