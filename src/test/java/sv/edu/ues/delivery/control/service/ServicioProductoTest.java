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
import sv.edu.ues.delivery.control.dao.ProductoDao;
import sv.edu.ues.delivery.entity.Producto;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioProductoTest {

    @Mock
    private Validator validadorMock;

    @Mock
    private ProductoDao ProductoDao;

    @InjectMocks
    private ServicioProducto servicioProducto;

    private Producto producto;

    private Producto productoValido;

    private Validator validador;

    @BeforeAll
    public void setup() {
        producto = new Producto();

        producto.setCodigo("110110");
        producto.setNombre(null); // no debe ser null por eso cumple la prueba
        producto.setDescripcion(null); // no debe ser null
        producto.setActivo(true);
        producto.setPrecioCompra(22.5);
        producto.setPrecioVenta(30.0);
        producto.setCantidadExistente(-1); // no debe ser menor a cero

        productoValido = new Producto();
        productoValido.setCodigo("11023022");
        productoValido.setNombre("Queso");
        productoValido.setDescripcion("y la queso");
        productoValido.setActivo(true);
        productoValido.setPrecioCompra(100.0);
        productoValido.setPrecioVenta(200.0);
        productoValido.setCantidadExistente(100);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar() {
        Producto underTest = new Producto();
        underTest.setCodigo("1235443");
        underTest.setNombre("Chicha 1L");
        underTest.setDescripcion("realizada a base de maiz y piña");
        underTest.setActivo(true);
        underTest.setPrecioCompra(10.90);
        underTest.setPrecioVenta(15.30);
        underTest.setCantidadExistente(10000);

        List<Producto> listadoEsperado = Arrays.asList(underTest);

        when(servicioProducto.listar()).thenReturn(listadoEsperado);

        List<Producto> listadoObtenido = servicioProducto.listar();

        assertEquals(listadoEsperado, listadoObtenido);
    }

    @Test
    public void testServicioObtenerPorId() {
        Producto productoEsperado = new Producto();
        productoEsperado.setActivo(false);
        productoEsperado.setCantidadExistente(20);
        productoEsperado.setCodigo("121324");
        productoEsperado.setDescripcion("Oro parece plata-no es");
        productoEsperado.setNombre("platano");
        productoEsperado.setPrecioCompra(0.15);
        productoEsperado.setPrecioVenta(0.35);

        when(servicioProducto.obtenerPorId("121324")).thenReturn(Optional.ofNullable(productoEsperado));
        Producto productoObtenido = servicioProducto.obtenerPorId("121324").get();
        assertEquals(productoEsperado, productoObtenido);

    }

    @Test
    public void testServicioInsertar() {
        Set<ConstraintViolation<Producto>> errores = validador.validate(producto);
        when(validadorMock.validate(producto)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioProducto.insertar(producto);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();
        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(productoValido);
        when(validadorMock.validate(productoValido)).thenReturn(errores);
        when(servicioProducto.insertar(productoValido)).thenReturn(productoValido);

        Producto productoObtenido = servicioProducto.insertar(productoValido);

        assertEquals(0, errores.size());
        assertNotNull(productoObtenido);
        assertEquals(productoValido, productoObtenido);
    }

    @Test
    public void testServicioActualizar() {
        Set<ConstraintViolation<Producto>> errores = validador.validate(producto);
        when(validadorMock.validate(producto)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioProducto.actualizar(producto);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(productoValido);
        when(validadorMock.validate(productoValido)).thenReturn(errores);
        when(servicioProducto.actualizar(productoValido)).thenReturn(productoValido);

        Producto productoObtenido = servicioProducto.actualizar(productoValido);

        assertEquals(0, errores.size());
        assertNotNull(productoObtenido);
        assertEquals(productoValido, productoObtenido);
    }

    @Test
    public void testServicioEliminar() {
        Set<ConstraintViolation<Producto>> errores = validador.validate(producto);
        when(validadorMock.validate(producto)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () -> {
            servicioProducto.eliminar(producto);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(productoValido);
        when(validadorMock.validate(productoValido)).thenReturn(errores);
        when(servicioProducto.eliminar(productoValido)).thenReturn(productoValido);

        Producto productoObtenido = servicioProducto.eliminar(productoValido);

        assertEquals(0, errores.size());
        assertNotNull(productoObtenido);
        assertEquals(productoValido, productoObtenido);
    }

}
