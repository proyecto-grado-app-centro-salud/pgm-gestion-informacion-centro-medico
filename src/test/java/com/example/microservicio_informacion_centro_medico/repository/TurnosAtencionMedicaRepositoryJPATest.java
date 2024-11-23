// package com.example.microservicio_informacion_centro_medico.repository;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotNull;

// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;
// import java.util.Optional;

// import org.junit.Test;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.TestInstance;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
// import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
// import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;
// import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
// import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;

// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
// @DataJpaTest
// public class TurnosAtencionMedicaRepositoryJPATest {
//     @Autowired
//     private TurnosAtencionMedicaRepositoryJPA turnosAtencionMedicaRepositoryJPA;
//     @Autowired
//     private UsuarioRepositoryJPA usuarioRepositoryJPA;
//     @Autowired
//     static private TurnosRepositoryJPA turnosRepositoryJPA;
//     @Autowired
//     private ConsultoriosRepositoryJPA consultoriosRepositoryJPA;
//     @Autowired
//     private EspecialidadesRepositoryJPA especialidadesRepositoryJPA;

    
//     static TurnosAtencionMedicaEntity turnosAtencionMedicaEntity=new TurnosAtencionMedicaEntity();
//     static TurnoEntity turnoEntity=new TurnoEntity();
//     static UsuarioEntity medicoEntity=new UsuarioEntity();
//     static ConsultorioEntity consultorioEntity=new ConsultorioEntity();
//     static EspecialidadEntity especialidadesEntity=new EspecialidadEntity();

//     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

//     @BeforeAll
//     static void beforeAll() throws ParseException {
//         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//         especialidadesEntity.setNombre("Pediatría");

//         consultorioEntity.setNombre("Consultorio A");
//         consultorioEntity.setEspecialidad(especialidadesEntity);

//         turnoEntity.setNombre("Mañana");
//         turnoEntity.setHoraInicio(LocalTime.of(8, 0));
//         turnoEntity.setHoraFin(LocalTime.of(10, 0));

//         medicoEntity.setNombres("Juan");
//         medicoEntity.setApellidoPaterno("Pérez");
//         medicoEntity.setApellidoMaterno("González");

//         turnosAtencionMedicaEntity.setConsultorio(consultorioEntity);
//         turnosAtencionMedicaEntity.setTurno(turnoEntity);
//         turnosAtencionMedicaEntity.setMedico(medicoEntity);
//         turnosAtencionMedicaEntity.setNumeroFichasDisponible(20);
//         turnosAtencionMedicaEntity.setFecha(sdf.parse("2024-10-01"));
//     }
//     @Test
//     public void save_guardarTurno_retornaTurnoGuardado() {
//         turnosRepositoryJPA.save(turnoEntity);
//         especialidadesRepositoryJPA.save(especialidadesEntity);
//         consultoriosRepositoryJPA.save(consultorioEntity);
//         usuarioRepositoryJPA.save(medicoEntity);
//         TurnosAtencionMedicaEntity savedTurno = turnosAtencionMedicaRepositoryJPA.save(turnosAtencionMedicaEntity);
//         assertNotNull(savedTurno);
//         assertEquals(savedTurno.getIdTurnoAtencionMedica(),turnosAtencionMedicaEntity.getIdTurnoAtencionMedica());
//     }
//     @Test
//     public void findAll_retornaListaDeTurnos_Correcto() {
//         turnosAtencionMedicaRepositoryJPA.save(turnosAtencionMedicaEntity);
//         var turnos = turnosAtencionMedicaRepositoryJPA.findAll();
//         assertThat(turnos).isNotEmpty();
//         assertThat(turnos.size()).isEqualTo(1);
//     }
//     @Test
//     public void findById_retornaTurnoPorId_ExisteTurno() {
//         TurnosAtencionMedicaEntity turnoGuardado = turnosAtencionMedicaRepositoryJPA.save(turnosAtencionMedicaEntity);
//         Optional<TurnosAtencionMedicaEntity> turnoEncontrado = turnosAtencionMedicaRepositoryJPA.findById(turnoGuardado.getIdTurnoAtencionMedica());
//         assertThat(turnoEncontrado.get().getIdTurnoAtencionMedica()).isEqualTo(turnoGuardado.getIdTurnoAtencionMedica());
//     }
// }

