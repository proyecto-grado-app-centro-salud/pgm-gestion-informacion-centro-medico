package com.example.microservicio_informacion_centro_medico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
// @CrossOrigin(origins = "*", allowedHeaders = "*",methods =[RequestMethod.GET,])
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
public class MicroservicioInformacionCentroMedicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioInformacionCentroMedicoApplication.class, args);
	}

}
