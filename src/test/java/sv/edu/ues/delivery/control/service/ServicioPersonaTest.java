package sv.edu.ues.delivery.control.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import sv.edu.ues.delivery.control.dao.PersonaDao;
import sv.edu.ues.delivery.entity.Persona;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioPersonaTest {

    @Mock
    private Validator validadorMock;

    @Mock
    private PersonaDao personaDao;

    @InjectMocks
    private ServicioPersona ServicioPersona;

    private Persona persona;

    private Persona personaValida;

    private Validator validador;

    @BeforeAll
    public void setup() {
        persona = new Persona();

        persona.setId(1L);
        persona.setNombre(null); // no debe ser null por eso cumple la prueba
        persona.setApellido(null); // no debe ser null
        persona.setDireccion(null); // no debe ser null
        Date fecha = Date.valueOf("2020-01-12");
        persona.setFechaNacimiento(fecha);

        personaValida = new Persona();
        personaValida.setId(2L);
        personaValida.setNombre("Algun Nombre");
        personaValida.setApellido("Algun apellido");
        personaValida.setDireccion("Alguna direccion");
        personaValida.setFechaNacimiento(fecha);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar() {
        Persona underTest = new Persona();
        underTest.setId(2L);
        underTest.setNombre("Juan");
        underTest.setApellido("Perez");
        underTest.setDireccion("la colina");
        Date fecha = Date.valueOf("2020-03-12");
        underTest.setFechaNacimiento(fecha);

        List<Persona> listadoEsperado = Arrays.asList(underTest);

        when(ServicioPersona.listar()).thenReturn(listadoEsperado);

        List<Persona> listadoObtenido = ServicioPersona.listar();

        assertEquals(listadoEsperado, listadoObtenido);
    }

    @Test
    public void testServicioObtenerPorId() {
        Persona personaEsperado = new Persona();
        personaEsperado.setApellido("Chanico");
        personaEsperado.setDireccion("la pepitoria");
        Date fecha = Date.valueOf("2020-03-12");
        personaEsperado.setFechaNacimiento(fecha);
        personaEsperado.setId(3L);
        personaEsperado.setNombre("Ruben");

        when(ServicioPersona.obtenerPorId(3L)).thenReturn(Optional.ofNullable(personaEsperado));
        Persona personaObtenido = ServicioPersona.obtenerPorId(3L).get();

        assertEquals(personaEsperado, personaObtenido);
    }

    @Test
    public void testServicioInsertar() {
        Set<ConstraintViolation<Persona>> errores = validador.validate(persona);
        when(validadorMock.validate(persona)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            ServicioPersona.insertar(persona);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();
        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(personaValida);
        when(validadorMock.validate(personaValida)).thenReturn(errores);
        when(ServicioPersona.insertar(personaValida)).thenReturn(personaValida);

        Persona personaObtenida = ServicioPersona.insertar(personaValida);

        assertEquals(0, errores.size());
        assertNotNull(personaObtenida);
        assertEquals(personaValida, personaObtenida);
    }

    @Test
    public void testServicioActualizar() {
        Set<ConstraintViolation<Persona>> errores = validador.validate(persona);
        when(validadorMock.validate(persona)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            ServicioPersona.actualizar(persona);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(personaValida);
        when(validadorMock.validate(personaValida)).thenReturn(errores);
        when(ServicioPersona.actualizar(personaValida)).thenReturn(personaValida);

        Persona personaObtenida = ServicioPersona.actualizar(personaValida);

        assertEquals(0, errores.size());
        assertNotNull(personaObtenida);
        assertEquals(personaValida, personaObtenida);
    }

    @Test
    public void testServicioEliminar() {
        Set<ConstraintViolation<Persona>> errores = validador.validate(persona);
        when(validadorMock.validate(persona)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            ServicioPersona.eliminar(persona);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(personaValida);
        when(validadorMock.validate(personaValida)).thenReturn(errores);
        when(ServicioPersona.eliminar(personaValida)).thenReturn(personaValida);

        Persona personaObtenida = ServicioPersona.eliminar(personaValida);

        assertEquals(0, errores.size());
        assertNotNull(personaObtenida);
        assertEquals(personaValida, personaObtenida);
    }

}
