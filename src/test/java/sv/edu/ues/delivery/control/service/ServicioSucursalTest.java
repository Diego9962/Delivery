package sv.edu.ues.delivery.control.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import sv.edu.ues.delivery.control.dao.SucursalDao;
import sv.edu.ues.delivery.entity.Sucursal;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioSucursalTest {

    @Mock
    private Validator validadorMock;

    @Mock
    private SucursalDao sucursalDao;

    @InjectMocks
    private ServicioSucursal servicioSucursal;

    private Sucursal sucursal;

    private Sucursal sucursalValida;

    private Validator validador;

    @BeforeAll
    public void setup() {
        sucursal = new Sucursal();

        sucursal.setId(1L);
        sucursal.setTelefono("123495432"); // 1234-1234 debe cumplir el patron ^\\d{4}-\\d{4}$ y por eso pasa la prueba

        sucursalValida = new Sucursal();
        sucursalValida.setId(2L);
        sucursalValida.setNombre("Plaza America");
        sucursalValida.setTelefono("1234-1234");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar() {
        Sucursal underTest = new Sucursal();
        underTest.setId(2L);
        underTest.setTelefono("7010-1245");
        List<Sucursal> listadoEsperado = Arrays.asList(underTest);

        when(servicioSucursal.listar()).thenReturn(listadoEsperado);

        List<Sucursal> listadoObtenido = servicioSucursal.listar();

        assertEquals(listadoEsperado, listadoObtenido);

    }

    @Test
    public void testServicioObtenerPorId() {
        Sucursal sucursalEsperado = new Sucursal();
        sucursalEsperado.setId(3L);
        sucursalEsperado.setTelefono("4215-1231");

        when(servicioSucursal.obtenerPorId(3L)).thenReturn(Optional.ofNullable(sucursalEsperado));
        Sucursal sucursalObtenido = servicioSucursal.obtenerPorId(3L).get();

        assertEquals(sucursalEsperado, sucursalObtenido);

    }

    @Test
    public void testServicioInsertar() {
        Set<ConstraintViolation<Sucursal>> errores = validador.validate(sucursal);
        when(validadorMock.validate(sucursal)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioSucursal.insertar(sucursal);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();
        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(sucursalValida);
        when(validadorMock.validate(sucursalValida)).thenReturn(errores);
        when(servicioSucursal.insertar(sucursalValida)).thenReturn(sucursalValida);

        Sucursal sucursalObtenida = servicioSucursal.insertar(sucursalValida);

        assertEquals(0, errores.size());
        assertNotNull(sucursalObtenida);
        assertEquals(sucursalValida, sucursalObtenida);
    }

    @Test
    public void testServicioActualizar() {
        Set<ConstraintViolation<Sucursal>> errores = validador.validate(sucursal);
        when(validadorMock.validate(sucursal)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioSucursal.actualizar(sucursal);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(sucursalValida);
        when(validadorMock.validate(sucursalValida)).thenReturn(errores);
        when(servicioSucursal.actualizar(sucursalValida)).thenReturn(sucursalValida);

        Sucursal sucursalObtenida = servicioSucursal.actualizar(sucursalValida);

        assertEquals(0, errores.size());
        assertNotNull(sucursalObtenida);
        assertEquals(sucursalValida, sucursalObtenida);
    }

    @Test
    public void testServicioEliminar() {
        Set<ConstraintViolation<Sucursal>> errores = validador.validate(sucursal);
        when(validadorMock.validate(sucursal)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioSucursal.eliminar(sucursal);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(sucursalValida);
        when(validadorMock.validate(sucursalValida)).thenReturn(errores);
        when(servicioSucursal.eliminar(sucursalValida)).thenReturn(sucursalValida);

        Sucursal sucursalObtenida = servicioSucursal.eliminar(sucursalValida);

        assertEquals(0, errores.size());
        assertNotNull(sucursalObtenida);
        assertEquals(sucursalValida, sucursalObtenida);
    }

}
