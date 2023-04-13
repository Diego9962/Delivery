package sv.edu.ues.delivery.control.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import sv.edu.ues.delivery.control.dao.FacturaDao;
import sv.edu.ues.delivery.entity.Factura;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioFacturaTest {

    @Mock
    private Validator validadorMock;

    @Mock
    private FacturaDao facturaDao;

    @InjectMocks
    private ServicioFactura servicioFactura;

    private Factura factura;

    private Factura facturaValida;

    private Validator validador;

    @BeforeAll
    public void setup() {
        factura = new Factura();

        factura.setId(1L);
        // Timestamp fechayHora = Timestamp.valueOf(LocalDateTime.now());
        factura.setFechaEmision(null); // Esta no debe ser null por lo tanto satisface la prueba
        factura.setAnulada(true);
        factura.setObservaciones("No hay observaciones hasta el momento");

        facturaValida = new Factura();
        facturaValida.setId(2L);
        facturaValida.setFechaEmision(Timestamp.valueOf(LocalDateTime.now()));
        facturaValida.setAnulada(false);
        facturaValida.setObservaciones("Alguna observacion o ninguna");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar() {
        Factura underTest = new Factura();
        underTest.setId(2L);
        Timestamp fechayHora = Timestamp.valueOf(LocalDateTime.now());
        underTest.setFechaEmision(fechayHora);
        underTest.setAnulada(false);
        underTest.setObservaciones("hasta el momento no");

        List<Factura> listadoEsperado = Arrays.asList(underTest);

        when(servicioFactura.listar()).thenReturn(listadoEsperado);

        List<Factura> listadoObtenido = servicioFactura.listar();

        assertEquals(listadoEsperado, listadoObtenido);
    }

    @Test
    public void testServicioObtenerPorId() {
        Factura facturaEsperado = new Factura();
        facturaEsperado.setAnulada(false);
        Timestamp fechayHora2 = Timestamp.valueOf(LocalDateTime.now());
        facturaEsperado.setFechaEmision(fechayHora2);
        facturaEsperado.setId(2L);
        facturaEsperado.setObservaciones("no hay observaciones");

        when(servicioFactura.obtenerPorId(2L)).thenReturn(Optional.ofNullable(facturaEsperado));
        Factura facturaObtenido = servicioFactura.obtenerPorId(2L).get();

        assertEquals(facturaEsperado, facturaObtenido);

    }

    @Test
    public void testServicioInsertar() {
        Set<ConstraintViolation<Factura>> errores = validador.validate(factura);
        when(validadorMock.validate(factura)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioFactura.insertar(factura);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();
        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(facturaValida);
        when(validadorMock.validate(facturaValida)).thenReturn(errores);
        when(servicioFactura.insertar(facturaValida)).thenReturn(facturaValida);

        Factura facturaObtenida = servicioFactura.insertar(facturaValida);

        assertEquals(0, errores.size());
        assertNotNull(facturaObtenida);
        assertEquals(facturaValida, facturaObtenida);
    }

    @Test
    public void testServicioActualizar() {
        Set<ConstraintViolation<Factura>> errores = validador.validate(factura);
        when(validadorMock.validate(factura)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioFactura.actualizar(factura);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(facturaValida);
        when(validadorMock.validate(facturaValida)).thenReturn(errores);
        when(servicioFactura.actualizar(facturaValida)).thenReturn(facturaValida);

        Factura facturaObtenida = servicioFactura.actualizar(facturaValida);

        assertEquals(0, errores.size());
        assertNotNull(facturaObtenida);
        assertEquals(facturaValida, facturaObtenida);
    }

    @Test
    public void testServicioEliminar() {
        Set<ConstraintViolation<Factura>> errores = validador.validate(factura);
        when(validadorMock.validate(factura)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioFactura.eliminar(factura);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(facturaValida);
        when(validadorMock.validate(facturaValida)).thenReturn(errores);
        when(servicioFactura.eliminar(facturaValida)).thenReturn(facturaValida);

        Factura facturaObtenida = servicioFactura.eliminar(facturaValida);

        assertEquals(0, errores.size());
        assertNotNull(facturaObtenida);
        assertEquals(facturaValida, facturaObtenida);
    }

}
