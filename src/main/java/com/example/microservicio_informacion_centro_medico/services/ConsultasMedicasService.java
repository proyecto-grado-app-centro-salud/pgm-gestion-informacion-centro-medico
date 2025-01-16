package com.example.microservicio_informacion_centro_medico.services;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;

@Service
public class ConsultasMedicasService {
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    private WebClient webClient;
    Logger logger = LoggerFactory.getLogger(ConsultasMedicasService.class);

    @Value("${url.lb}")
    private String lb;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(lb + "/api/microservicio-fichas-medicas").build();
    }

    public void eliminarConsultasMedicasDeTurnoAtencionMedica(int idTurnoAtencionMedica) {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        logger.info("eliminarConsultasMedicasDeTurnoAtencionMedica");
        logger.info(lb);

        this.webClient.delete()
                .uri("/fichas-medicas/turnos-atencion-medica/"+idTurnoAtencionMedica)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .onStatus(
                    status -> !status.is2xxSuccessful(), 
                    clientResponse -> manejarError(clientResponse) 
                )
                .bodyToMono(Void.class)
                .block();

            // this.webClient.delete()
            //         .uri("/fichas-medicas/turnos-atencion-medica/" + idTurnoAtencionMedica)
            //         .header("Authorization", "Bearer " + token)
            //         .retrieve()
            //     .onStatus(
            //         status -> !status.is2xxSuccessful(),
            //         clientResponse -> manejarError(clientResponse)
            //     )
            //     .bodyToMono(Void.class)
            //     .onErrorResume(e -> {
            //         logger.error("Error en la solicitud al microservicio: ", e);
            //         return Mono.error(new Exception("Error al intentar eliminar la consulta médica"));
            //     })
            //     .doOnTerminate(() -> logger.info("Proceso terminado"))
            //     .subscribe(
            //         null,
            //         error -> {
            //             logger.error("Error en la petición inicial: ", error);
            //         }
            //     );
    }

    private Mono<? extends Throwable> manejarError(ClientResponse clientResponse) {
        int statusCode = clientResponse.statusCode().value();
       
        if (statusCode == 403) {
            return Mono.error(new NotFoundException("Datos enviados incorrectos"));
        } else if (statusCode == 500) {
            return Mono.error(new InternalServerErrorException("Error interno del servidor"));
        }
       
        return Mono.error(new Exception(""));
    }
}
