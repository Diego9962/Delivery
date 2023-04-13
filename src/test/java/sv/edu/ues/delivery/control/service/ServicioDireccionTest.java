package sv.edu.ues.delivery.control.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import sv.edu.ues.delivery.control.dao.DireccionDao;
import sv.edu.ues.delivery.entity.Direccion;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioDireccionTest {

    @Mock
    private Validator validadorMock;

    @Mock
    private DireccionDao direccionDao;

    @InjectMocks
    private ServicioDireccion servicioDireccion;

    private Direccion direccionError;

    private Direccion direccionValida;

    private Validator validador;

    @BeforeAll
    public void setup() {
        direccionError = new Direccion();
        direccionError.setId(1L);
        direccionError.setDireccion(null); // este deberia ser no null por eso satisface la prueba
        direccionError.setLatitud(BigDecimal.TEN);
        direccionError.setLongitud(BigDecimal.ONE);

        direccionValida = new Direccion();
        direccionValida.setId(2L);
        direccionValida.setDireccion("Alguna direccion muy muy lejana");
        direccionValida.setLatitud(BigDecimal.TEN);
        direccionValida.setLongitud(BigDecimal.ONE);
        direccionValida.setReferencias("Alguna referencia");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar() {
        Direccion underTest = new Direccion();
        underTest.setId(2L);
        underTest.setDireccion("La piedra pacha");
        underTest.setLatitud(BigDecimal.ONE);
        underTest.setLongitud(BigDecimal.ZERO);

        List<Direccion> listadoEsperado = Arrays.asList(underTest);

        when(servicioDireccion.listar()).thenReturn(listadoEsperado);

        List<Direccion> listadoObtenido = servicioDireccion.listar();

        assertEquals(listadoEsperado, listadoObtenido);

    }

    @Test
    public void testServicioObtenerPorId() {
        Direccion direccionEsperado = new Direccion();
        direccionEsperado.setId(3L);
        direccionEsperado.setDireccion("El capulin");
        direccionEsperado.setLatitud(BigDecimal.ONE);
        direccionEsperado.setLongitud(BigDecimal.TEN);

        when(servicioDireccion.obtenerPorId(3L)).thenReturn(Optional.ofNullable(direccionEsperado));
        Direccion direccionObtenido = servicioDireccion.obtenerPorId(3L).get();

        assertEquals(direccionEsperado, direccionObtenido);
    }

    @Test
    public void testServicioInsertar() {
        Set<ConstraintViolation<Direccion>> errores = validador.validate(direccionError);
        when(validadorMock.validate(direccionError)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioDireccion.insertar(direccionError);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();
        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(direccionValida);
        when(validadorMock.validate(direccionValida)).thenReturn(errores);
        when(servicioDireccion.insertar(direccionValida)).thenReturn(direccionValida);

        assertEquals(0, errores.size());

        Direccion direccionObtenida = servicioDireccion.insertar(direccionValida);

        assertNotNull(direccionObtenida);
        assertEquals(direccionValida, direccionObtenida);
    }

    @Test
    public void testServicioActualizar() {
        Set<ConstraintViolation<Direccion>> errores = validador.validate(direccionError);
        when(validadorMock.validate(direccionError)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioDireccion.actualizar(direccionError);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(direccionValida);
        when(validadorMock.validate(direccionValida)).thenReturn(errores);
        when(servicioDireccion.actualizar(direccionValida)).thenReturn(direccionValida);

        assertEquals(0, errores.size());

        Direccion direccionObtenida = servicioDireccion.actualizar(direccionValida);

        assertNotNull(direccionObtenida);
        assertEquals(direccionValida, direccionObtenida);
    }

    @Test
    public void testServicioEliminar() {
        Set<ConstraintViolation<Direccion>> errores = validador.validate(direccionError);
        when(validadorMock.validate(direccionError)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioDireccion.eliminar(direccionError);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(direccionValida);
        when(validadorMock.validate(direccionValida)).thenReturn(errores);
        when(servicioDireccion.eliminar(direccionValida)).thenReturn(direccionValida);

        assertEquals(0, errores.size());

        Direccion direccionObtenida = servicioDireccion.eliminar(direccionValida);

        assertNotNull(direccionObtenida);
        assertEquals(direccionValida, direccionObtenida);
    }

}
