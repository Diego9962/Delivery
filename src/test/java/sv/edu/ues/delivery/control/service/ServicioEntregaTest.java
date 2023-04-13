package sv.edu.ues.delivery.control.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import sv.edu.ues.delivery.control.dao.EntregaDao;
import sv.edu.ues.delivery.entity.Entrega;
import sv.edu.ues.delivery.entity.EstadoEntrega;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioEntregaTest {

    @Mock
    private Validator validadorMock;

    @Mock
    private EntregaDao entregaDao;

    @InjectMocks
    private ServicioEntrega servicioEntrega;

    private Entrega entrega;

    private Entrega entregaValida;

    private Validator validador;

    @BeforeAll
    public void setup() {
        entrega = new Entrega();

        entrega.setId(1L);
        entrega.setObservaciones(null);
        Timestamp fechaYHora = Timestamp.valueOf(LocalDateTime.now());
        entrega.setFechaCreacion(fechaYHora);
        entrega.setEstadoEntrega(EstadoEntrega.EN_CAMINO);
        entrega.setFechaAlcanzado(null);

        entregaValida = new Entrega();
        entregaValida.setId(2L);
        entregaValida.setObservaciones("Alguna observacion");
        entregaValida.setFechaCreacion(Timestamp.valueOf(LocalDateTime.now()));
        entregaValida.setEstadoEntrega(EstadoEntrega.ENTREGADO);
        entregaValida.setFechaAlcanzado(Timestamp.valueOf(LocalDateTime.now()));

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar() {
        Entrega underTest = new Entrega();
        underTest.setEstadoEntrega(EstadoEntrega.EN_CAMINO);
        Timestamp fechaYHora2 = Timestamp.valueOf(LocalDateTime.now());
        underTest.setFechaAlcanzado(fechaYHora2);
        underTest.setFechaCreacion(fechaYHora2);
        underTest.setId(2L);
        underTest.setObservaciones("no hay observaciones");

        List<Entrega> listadoEsperado = Arrays.asList(underTest);

        when(servicioEntrega.listar()).thenReturn(listadoEsperado);

        List<Entrega> listadoObtenido = servicioEntrega.listar();

        assertEquals(listadoEsperado, listadoObtenido);

    }

    @Test
    public void testServicioObtenerPorId() {
        Entrega entregaEsperado = new Entrega();
        entregaEsperado.setEstadoEntrega(EstadoEntrega.EN_CAMINO);
        Timestamp fechaYHora2 = Timestamp.valueOf(LocalDateTime.now());
        entregaEsperado.setFechaAlcanzado(fechaYHora2);
        entregaEsperado.setFechaCreacion(fechaYHora2);
        entregaEsperado.setId(3L);
        entregaEsperado.setObservaciones("con observaciones");

        when(servicioEntrega.obtenerPorId(3L)).thenReturn(Optional.ofNullable(entregaEsperado));
        Entrega entregaObtenido = servicioEntrega.obtenerPorId(3L).get();

        assertEquals(entregaEsperado, entregaObtenido);
    }

    @Test
    public void testServicioInsertar() {
        Set<ConstraintViolation<Entrega>> errores = validador.validate(entrega);
        when(validadorMock.validate(entrega)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioEntrega.insertar(entrega);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(entregaValida);
        when(validadorMock.validate(entregaValida)).thenReturn(errores);
        when(servicioEntrega.insertar(entregaValida)).thenReturn(entregaValida);

        Entrega entregaObtenida = servicioEntrega.insertar(entregaValida);

        assertEquals(0, errores.size());
        assertNotNull(entregaObtenida);
        assertEquals(entregaValida, entregaObtenida);
    }

    @Test
    public void testServicioActualizar() {
        Set<ConstraintViolation<Entrega>> errores = validador.validate(entrega);
        when(validadorMock.validate(entrega)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioEntrega.actualizar(entrega);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(entregaValida);
        when(validadorMock.validate(entregaValida)).thenReturn(errores);
        when(servicioEntrega.actualizar(entregaValida)).thenReturn(entregaValida);

        Entrega entregaObtenida = servicioEntrega.actualizar(entregaValida);

        assertEquals(0, errores.size());
        assertNotNull(entregaObtenida);
        assertEquals(entregaValida, entregaObtenida);
    }

    @Test
    public void testServicioEliminar() {
        Set<ConstraintViolation<Entrega>> errores = validador.validate(entrega);
        when(validadorMock.validate(entrega)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioEntrega.eliminar(entrega);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(entregaValida);
        when(validadorMock.validate(entregaValida)).thenReturn(errores);
        when(servicioEntrega.eliminar(entregaValida)).thenReturn(entregaValida);

        Entrega entregaObtenida = servicioEntrega.eliminar(entregaValida);

        assertEquals(0, errores.size());
        assertNotNull(entregaObtenida);
        assertEquals(entregaValida, entregaObtenida);
    }

}
