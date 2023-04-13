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
import sv.edu.ues.delivery.control.dao.RepartidorDao;
import sv.edu.ues.delivery.entity.Repartidor;
import sv.edu.ues.delivery.entity.TipoLicencia;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioRepartidorTest {

    @Mock
    private Validator validadorMock;

    @Mock
    private RepartidorDao repartidorDao;

    @InjectMocks
    private ServicioRepartidor servicioRepartidor;

    private Repartidor repartidor;

    private Repartidor repartidorValido;

    private Validator validador;

    @BeforeAll
    public void setup() {
        repartidor = new Repartidor();

        repartidor.setId(1L);
        repartidor.setNombre(null); // no debe ser null : por eso cumple la prueba
        repartidor.setApellido(null); // no debe ser null
        repartidor.setSalario(365.35);
        Date fecha = Date.valueOf("2023-04-04");
        repartidor.setFechaNacimiento(fecha);
        repartidor.setObservacion(null); // no debe ser null
        repartidor.setActivo(false);
        repartidor.setTipoLicencia(TipoLicencia.CLASE_B);

        repartidorValido = new Repartidor();
        repartidorValido.setId(2L);
        repartidorValido.setNombre("Juan");
        repartidorValido.setApellido("Perez");
        repartidorValido.setSalario(600.0);
        repartidorValido.setFechaNacimiento(fecha);
        repartidorValido.setObservacion("No se baña");
        repartidorValido.setActivo(true);
        repartidorValido.setTipoLicencia(TipoLicencia.CLASE_C);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar() {
        Repartidor underTest = new Repartidor();
        underTest.setId(2L);
        underTest.setNombre("Romulo");
        underTest.setApellido("Pocasangre");
        underTest.setSalario(365.35);
        Date fecha = Date.valueOf("2023-04-04");
        underTest.setFechaNacimiento(fecha);
        underTest.setObservacion("despedido por vayunco");
        underTest.setActivo(false);
        underTest.setTipoLicencia(TipoLicencia.CLASE_B);

        List<Repartidor> listadoEsperado = Arrays.asList(underTest);

        when(servicioRepartidor.listar()).thenReturn(listadoEsperado);

        List<Repartidor> listadoObtenido = servicioRepartidor.listar();

        assertEquals(listadoEsperado, listadoObtenido);
    }

    @Test
    public void testServicioObtenerPorId() {
        Repartidor repartidorEsperado = new Repartidor();
        repartidorEsperado.setActivo(true);
        repartidorEsperado.setApellido("Reinosa");
        repartidorEsperado.setSalario(368.35);
        Date fecha2 = Date.valueOf("2023-04-04");
        repartidorEsperado.setFechaNacimiento(fecha2);
        repartidorEsperado.setId(3L);
        repartidorEsperado.setNombre("Mariano");
        repartidorEsperado.setObservacion("no hay observaciones");
        repartidorEsperado.setTipoLicencia(TipoLicencia.CLASE_B);

        when(servicioRepartidor.obtenerPorId(3L)).thenReturn(Optional.ofNullable(repartidorEsperado));
        Repartidor repartidorObtenido = servicioRepartidor.obtenerPorId(3L).get();

        assertEquals(repartidorEsperado, repartidorObtenido);
    }

    @Test
    public void testServicioInsertar() {
        Set<ConstraintViolation<Repartidor>> errores = validador.validate(repartidor);
        when(validadorMock.validate(repartidor)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioRepartidor.insertar(repartidor);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();
        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(repartidorValido);
        when(validadorMock.validate(repartidorValido)).thenReturn(errores);
        when(servicioRepartidor.insertar(repartidorValido)).thenReturn(repartidorValido);

        Repartidor repartidorObtenido = servicioRepartidor.insertar(repartidorValido);

        assertEquals(0, errores.size());
        assertNotNull(repartidorObtenido);
        assertEquals(repartidorValido, repartidorObtenido);
    }

    @Test
    public void testServicioActualizar() {
        Set<ConstraintViolation<Repartidor>> errores = validador.validate(repartidor);
        when(validadorMock.validate(repartidor)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioRepartidor.actualizar(repartidor);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(repartidorValido);
        when(validadorMock.validate(repartidorValido)).thenReturn(errores);
        when(servicioRepartidor.actualizar(repartidorValido)).thenReturn(repartidorValido);

        Repartidor repartidorObtenido = servicioRepartidor.actualizar(repartidorValido);

        assertEquals(0, errores.size());
        assertNotNull(repartidorObtenido);
        assertEquals(repartidorValido, repartidorObtenido);
    }

    @Test
    public void testServicioEliminar() {
        Set<ConstraintViolation<Repartidor>> errores = validador.validate(repartidor);
        when(validadorMock.validate(repartidor)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioRepartidor.eliminar(repartidor);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(repartidorValido);
        when(validadorMock.validate(repartidorValido)).thenReturn(errores);
        when(servicioRepartidor.eliminar(repartidorValido)).thenReturn(repartidorValido);

        Repartidor repartidorObtenido = servicioRepartidor.eliminar(repartidorValido);

        assertEquals(0, errores.size());
        assertNotNull(repartidorObtenido);
        assertEquals(repartidorValido, repartidorObtenido);
    }

}
