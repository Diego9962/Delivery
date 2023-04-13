package sv.edu.ues.delivery.control.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

import sv.edu.ues.delivery.control.dao.DetalleOrdenDao;
import sv.edu.ues.delivery.entity.DetalleOrden;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioDetalleOrdenTest {

    @Mock
    private Validator validadorMock;

    @Mock
    private DetalleOrdenDao detalleOrdenDao;

    @InjectMocks
    private ServicioDetalleOrden servicioDetalleOrden;

    private DetalleOrden detalleOrdenError;

    private DetalleOrden detalleOrdenValida;

    private Validator validador;

    @BeforeAll
    public void setup() {
        detalleOrdenError = new DetalleOrden();

        detalleOrdenError.setIdOrden(1L);
        detalleOrdenError.setCantidad(0); // ete deberia ser mayor a 0 por lo tanto satisface la prueba
        detalleOrdenError.setCodigoProducto("123456");

        detalleOrdenValida = new DetalleOrden();
        detalleOrdenValida.setIdOrden(2L);
        detalleOrdenValida.setCantidad(200);
        detalleOrdenValida.setCodigoProducto("B96743Q");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar() {
        DetalleOrden underTest = new DetalleOrden();
        underTest.setCantidad(1);
        underTest.setCodigoProducto("123453");
        underTest.setIdOrden(2L);

        List<DetalleOrden> listadoEsperado = Arrays.asList(underTest);

        when(servicioDetalleOrden.listar()).thenReturn(listadoEsperado);

        List<DetalleOrden> listadoObtenido = servicioDetalleOrden.listar();

        assertEquals(listadoEsperado, listadoObtenido);

    }

    @Test
    public void testServicioObtenerPorId() {
        DetalleOrden detalleOrdenEsperado = new DetalleOrden();
        detalleOrdenEsperado.setCantidad(2);
        detalleOrdenEsperado.setCodigoProducto("12267");
        detalleOrdenEsperado.setIdOrden(3L);

        when(servicioDetalleOrden.obtenerPorId(3L)).thenReturn(Optional.ofNullable(detalleOrdenEsperado));
        DetalleOrden detalleOrdenObtenido = servicioDetalleOrden.obtenerPorId(3L).get();

        assertEquals(detalleOrdenEsperado, detalleOrdenObtenido);

    }

    @Test
    public void testServicioInsertar() {
        Set<ConstraintViolation<DetalleOrden>> errores = validador.validate(detalleOrdenError);
        when(validadorMock.validate(detalleOrdenError)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioDetalleOrden.insertar(detalleOrdenError);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(detalleOrdenValida);
        when(validadorMock.validate(detalleOrdenValida)).thenReturn(errores);
        when(servicioDetalleOrden.insertar(detalleOrdenValida)).thenReturn(detalleOrdenValida);

        assertEquals(0, errores.size());

        DetalleOrden detalleOrdenObtenida = servicioDetalleOrden.insertar(detalleOrdenValida);

        assertNotNull(detalleOrdenObtenida);
        assertEquals(detalleOrdenValida, detalleOrdenValida);
    }

    @Test
    public void testServicioActualizar() {
        Set<ConstraintViolation<DetalleOrden>> errores = validador.validate(detalleOrdenError);
        when(validadorMock.validate(detalleOrdenError)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioDetalleOrden.actualizar(detalleOrdenError);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(detalleOrdenValida);
        when(validadorMock.validate(detalleOrdenValida)).thenReturn(errores);
        when(servicioDetalleOrden.actualizar(detalleOrdenValida)).thenReturn(detalleOrdenValida);

        assertEquals(0, errores.size());

        DetalleOrden detalleOrdenObtenida = servicioDetalleOrden.actualizar(detalleOrdenValida);

        assertNotNull(detalleOrdenObtenida);
        assertEquals(detalleOrdenValida, detalleOrdenValida);
    }

    @Test
    public void testServicioEliminar() {
        Set<ConstraintViolation<DetalleOrden>> errores = validador.validate(detalleOrdenError);
        when(validadorMock.validate(detalleOrdenError)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioDetalleOrden.eliminar(detalleOrdenError);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(detalleOrdenValida);
        when(validadorMock.validate(detalleOrdenValida)).thenReturn(errores);
        when(servicioDetalleOrden.eliminar(detalleOrdenValida)).thenReturn(detalleOrdenValida);

        assertEquals(0, errores.size());

        DetalleOrden detalleOrdenObtenida = servicioDetalleOrden.eliminar(detalleOrdenValida);

        assertNotNull(detalleOrdenObtenida);
        assertEquals(detalleOrdenValida, detalleOrdenValida);
    }

}
