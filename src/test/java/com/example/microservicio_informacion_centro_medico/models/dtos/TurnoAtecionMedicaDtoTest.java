// package com.example.microservicio_informacion_centro_medico.models.dtos;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.text.SimpleDateFormat;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.meanbean.test.BeanTester;

// import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
// import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
// import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;
// import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
// import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;
// import com.example.microservicio_informacion_centro_medico.model.dtos.TurnoAtencionMedicaDto;

// public class TurnoAtecionMedicaDtoTest {
    
//     private static TurnosAtencionMedicaEntity turnosAtencionMedicaEntity;
//     private static TurnoEntity turnoEntity;
//     private static UsuarioEntity medicoEntity;
//     private static ConsultorioEntity consultorioEntity;
//     private static EspecialidadEntity especialidadesEntity;
//     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

//     @BeforeAll
//     static void beforeAll() throws Exception {
//         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//         especialidadesEntity = new EspecialidadEntity();
//         especialidadesEntity.setIdEspecialidad(1);
//         especialidadesEntity.setNombre("Pediatría");

//         consultorioEntity = new ConsultorioEntity();
//         consultorioEntity.setIdConsultorio(1);
//         consultorioEntity.setNombre("Consultorio A");
//         consultorioEntity.setEspecialidad(especialidadesEntity);

//         turnoEntity = new TurnoEntity();
//         turnoEntity.setIdTurno(1);
//         turnoEntity.setNombre("Mañana");
//         turnoEntity.setHoraInicio(LocalTime.of(8, 0));
//         turnoEntity.setHoraFin(LocalTime.of(10, 0));

//         medicoEntity = new UsuarioEntity();
//         medicoEntity.setIdUsuario(1);
//         medicoEntity.setNombres("Juan");
//         medicoEntity.setApellidoPaterno("Pérez");
//         medicoEntity.setApellidoMaterno("González");

//         turnosAtencionMedicaEntity = new TurnosAtencionMedicaEntity();
//         turnosAtencionMedicaEntity.setConsultorio(consultorioEntity);
//         turnosAtencionMedicaEntity.setTurno(turnoEntity);
//         turnosAtencionMedicaEntity.setMedico(medicoEntity);
//         turnosAtencionMedicaEntity.setNumeroFichasDisponible(20);
//         turnosAtencionMedicaEntity.setFecha(sdf.parse("2024-10-01"));
//     }

//     @Test
//     void convertirTurnoAtencionMedicaEntityATurnoAtencionMedicaDto_turnoAtencionMedicaCorrecto_Correcto() {

//         TurnoAtencionMedicaDto turnoAtencionMedicaDto = new TurnoAtencionMedicaDto().convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(turnosAtencionMedicaEntity);

//         assertEquals(turnosAtencionMedicaEntity.getIdTurnoAtencionMedica(), turnoAtencionMedicaDto.getIdTurnoAtencionMedica());
//         assertEquals(turnosAtencionMedicaEntity.getNumeroFichasDisponible(), turnoAtencionMedicaDto.getNumeroFichasDisponible());
//         assertEquals(turnosAtencionMedicaEntity.getFecha(), turnoAtencionMedicaDto.getFecha());
//         assertEquals(turnosAtencionMedicaEntity.getConsultorio().getIdConsultorio(), turnoAtencionMedicaDto.getIdConsultorio());
//         assertEquals(turnosAtencionMedicaEntity.getConsultorio().getNombre(), turnoAtencionMedicaDto.getNombreConsultorio());
//         assertEquals(turnosAtencionMedicaEntity.getTurno().getIdTurno(), turnoAtencionMedicaDto.getIdTurno());
//         assertEquals(turnosAtencionMedicaEntity.getTurno().getNombre(), turnoAtencionMedicaDto.getNombreTurno());
//         assertEquals(turnosAtencionMedicaEntity.getTurno().getHoraInicio().format(formatter), turnoAtencionMedicaDto.getHoraInicio());
//         assertEquals(turnosAtencionMedicaEntity.getTurno().getHoraFin().format(formatter), turnoAtencionMedicaDto.getHoraFin());
//         assertEquals(turnosAtencionMedicaEntity.getMedico().getIdUsuario(), turnoAtencionMedicaDto.getIdMedico());
//         assertEquals(turnosAtencionMedicaEntity.getMedico().getNombres() + " " + turnosAtencionMedicaEntity.getMedico().getApellidoPaterno() + " " + turnosAtencionMedicaEntity.getMedico().getApellidoMaterno(), turnoAtencionMedicaDto.getNombreMedico());
//         assertEquals(turnosAtencionMedicaEntity.getConsultorio().getEspecialidad().getIdEspecialidad(), turnoAtencionMedicaDto.getIdEspecialidad());
//         assertEquals(turnosAtencionMedicaEntity.getConsultorio().getEspecialidad().getNombre(), turnoAtencionMedicaDto.getNombreEspecialidad());
//     }
//     @Test
//     void metodosBean_FuncionanMetodosBean_Correcto(){
//         BeanTester beanTester = new BeanTester();
//         beanTester.testBean(TurnoAtencionMedicaDto.class);
//     }
// }
