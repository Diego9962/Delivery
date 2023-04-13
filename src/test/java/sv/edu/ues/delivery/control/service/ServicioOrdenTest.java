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
import sv.edu.ues.delivery.control.dao.OrdenDao;
import sv.edu.ues.delivery.entity.EstadoOrden;
//import sv.edu.ues.delivery.entity.EstadoOrden;
import sv.edu.ues.delivery.entity.Orden;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioOrdenTest {

    @Mock
    private Validator validadorMock;

    @Mock
    private OrdenDao ordenDao;

    @InjectMocks
    private ServicioOrden servicioOrden;

    private Orden orden;

    private Orden ordenValida;

    private Validator validador;

    @BeforeAll
    public void setup() {
        orden = new Orden();

        orden.setId(1L);
        orden.setObservaciones("No tiene observaciones de Orden");
        Date fecha = Date.valueOf("2020-01-12");
        orden.setFechaOrden(fecha);
        orden.setEstado(null); // EstadoOrden.ENVIADA este no debe ser null y por eso satisface la prueba

        ordenValida = new Orden();
        ordenValida.setId(2L);
        ordenValida.setObservaciones("Alguna observacion");
        ordenValida.setFechaOrden(fecha);
        ordenValida.setEstado(EstadoOrden.ENVIADA);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar() {
        Orden underTest = new Orden();
        underTest.setId(2L);
        underTest.setObservaciones("Tiene observaciones de Orden");
        Date fecha = Date.valueOf("2020-12-12");
        underTest.setFechaOrden(fecha);
        underTest.setEstado(EstadoOrden.CANCELADA);

        List<Orden> listadoEsperado = Arrays.asList(underTest);

        when(servicioOrden.listar()).thenReturn(listadoEsperado);

        List<Orden> listadoObtenido = servicioOrden.listar();

        assertEquals(listadoEsperado, listadoObtenido);

    }

    @Test
    public void testServicioObtenerPorId() {
        Orden ordenEsperado = new Orden();
        ordenEsperado.setId(3L);
        ordenEsperado.setObservaciones("no tiene observaciones de Orden");
        Date fecha = Date.valueOf("2020-12-14");
        ordenEsperado.setFechaOrden(fecha);
        ordenEsperado.setEstado(EstadoOrden.ENVIADA);

        when(servicioOrden.obtenerPorId(3L)).thenReturn(Optional.ofNullable(ordenEsperado));
        Orden ordenObtenido = servicioOrden.obtenerPorId(3L).get();

        assertEquals(ordenEsperado, ordenObtenido);
    }

    @Test
    public void testServicioInsertar() {
        Set<ConstraintViolation<Orden>> errores = validador.validate(orden);
        when(validadorMock.validate(orden)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioOrden.insertar(orden);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();
        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(ordenValida);
        when(validadorMock.validate(ordenValida)).thenReturn(errores);
        when(servicioOrden.insertar(ordenValida)).thenReturn(ordenValida);

        Orden ordenObtenida = servicioOrden.insertar(ordenValida);

        assertEquals(0, errores.size());
        assertNotNull(ordenObtenida);
        assertEquals(ordenValida, ordenObtenida);
    }

    @Test
    public void testServicioActualizar() {
        Set<ConstraintViolation<Orden>> errores = validador.validate(orden);
        when(validadorMock.validate(orden)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioOrden.actualizar(orden);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(ordenValida);
        when(validadorMock.validate(ordenValida)).thenReturn(errores);
        when(servicioOrden.actualizar(ordenValida)).thenReturn(ordenValida);

        Orden ordenObtenida = servicioOrden.actualizar(ordenValida);

        assertEquals(0, errores.size());
        assertNotNull(ordenObtenida);
        assertEquals(ordenValida, ordenObtenida);
    }

    @Test
    public void testServicioEliminar() {
        Set<ConstraintViolation<Orden>> errores = validador.validate(orden);
        when(validadorMock.validate(orden)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioOrden.eliminar(orden);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(ordenValida);
        when(validadorMock.validate(ordenValida)).thenReturn(errores);
        when(servicioOrden.eliminar(ordenValida)).thenReturn(ordenValida);

        Orden ordenObtenida = servicioOrden.eliminar(ordenValida);

        assertEquals(0, errores.size());
        assertNotNull(ordenObtenida);
        assertEquals(ordenValida, ordenObtenida);
    }

}
