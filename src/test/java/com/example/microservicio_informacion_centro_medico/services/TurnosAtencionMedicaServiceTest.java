// package com.example.microservicio_informacion_centro_medico.services;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyInt;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.when;

// import java.text.SimpleDateFormat;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;
// import java.util.Arrays;
// import java.util.Collections;a
// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
// import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
// import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;
// import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
// import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;
// import com.example.microservicio_informacion_centro_medico.model.dtos.TurnoAtencionMedicaDto;
// import com.example.microservicio_informacion_centro_medico.repository.ConsultoriosRepositoryJPA;
// import com.example.microservicio_informacion_centro_medico.repository.TurnosAtencionMedicaRepositoryJPA;
// import com.example.microservicio_informacion_centro_medico.repository.TurnosRepositoryJPA;
// import com.example.microservicio_informacion_centro_medico.repository.UsuarioRepositoryJPA;

// @ExtendWith(MockitoExtension.class)
// public class TurnosAtencionMedicaServiceTest {
//     @InjectMocks
//     private static TurnosAtencionMedicaService turnosAtencionMedicaService;

//     @Mock
//     private static TurnosAtencionMedicaRepositoryJPA turnosAtencionMedicaRepositoryJPA;

//     @Mock
//     private static TurnosRepositoryJPA turnosRepositoryJPA;

//     @Mock
//     private static ConsultoriosRepositoryJPA consultoriosRepositoryJPA;

//     @Mock
//     private static UsuarioRepositoryJPA usuarioRepositoryJPA;

//     private static TurnosAtencionMedicaEntity turnosAtencionMedicaEntity=new TurnosAtencionMedicaEntity();
//     private static TurnoAtencionMedicaDto turnoAtencionMedicaDto=new TurnoAtencionMedicaDto();
//     private static TurnoEntity turnoEntity=new TurnoEntity();
//     private static UsuarioEntity medicoEntity=new UsuarioEntity();
//     private static ConsultorioEntity consultorioEntity=new ConsultorioEntity();
//     private static EspecialidadEntity especialidadesEntity=new EspecialidadEntity();
    
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

//         turnoAtencionMedicaDto.setIdTurno(1);
//         turnoAtencionMedicaDto.setIdConsultorio(1);
//         turnoAtencionMedicaDto.setIdMedico(1);
//         turnoAtencionMedicaDto.setFecha(sdf.parse("2024-10-01"));
//         turnoAtencionMedicaDto.setNumeroFichasDisponible(20);
//     }
//     @Test
//     void crearHorarioAtencion_CreaTurno_Exitoso() {
//         when(turnosRepositoryJPA.findById(anyInt())).thenReturn(Optional.of(turnoEntity));
//         when(consultoriosRepositoryJPA.findByIdConsultorioAndDeletedAtIsNull(anyInt())).thenReturn(Optional.of(consultorioEntity));
//         when(usuarioRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(anyInt())).thenReturn(Optional.of(medicoEntity));
//         when(turnosAtencionMedicaRepositoryJPA.save(any())).thenReturn(turnosAtencionMedicaEntity);

//         TurnoAtencionMedicaDto resultado = turnosAtencionMedicaService.crearHorarioAtencion(new TurnoAtencionMedicaDto().convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(turnosAtencionMedicaEntity));

//         assertEquals(turnosAtencionMedicaEntity.getIdTurnoAtencionMedica(), resultado.getIdTurnoAtencionMedica());
//         assertEquals(turnosAtencionMedicaEntity.getNumeroFichasDisponible(), resultado.getNumeroFichasDisponible());
//     }

//     @Test
//     void obtenerHorariosAtencionDetalle_RetornaLista_Exitoso() {
//         when(turnosAtencionMedicaRepositoryJPA.findAll()).thenReturn(Arrays.asList(new TurnosAtencionMedicaEntity[]{turnosAtencionMedicaEntity,turnosAtencionMedicaEntity}));
//         List<TurnoAtencionMedicaDto> resultados = turnosAtencionMedicaService.obtenerHorariosAtencionDetalle();
//         assertEquals(2, resultados.size());
//         assertEquals(turnosAtencionMedicaEntity.getIdTurnoAtencionMedica(), resultados.get(0).getIdTurnoAtencionMedica());
//     }
//     @Test
//     void obtenerHorarioAtencionDetalle_RetornaTurno_Exitoso() {
//         when(turnosAtencionMedicaRepositoryJPA.findById(anyInt())).thenReturn(Optional.of(turnosAtencionMedicaEntity));
//         TurnoAtencionMedicaDto resultado = turnosAtencionMedicaService.obtenerHorarioAtencionDetalle(1);
//         assertEquals(turnosAtencionMedicaEntity.getIdTurnoAtencionMedica(), resultado.getIdTurnoAtencionMedica());
//     }
//     @Test
//     void eliminarHorarioAtencion_EliminaTurno_Exitoso() {
//         doNothing().when(turnosAtencionMedicaRepositoryJPA).deleteById(anyInt());
//         turnosAtencionMedicaService.eliminarHorarioAtencion(1);
//     }
// }
