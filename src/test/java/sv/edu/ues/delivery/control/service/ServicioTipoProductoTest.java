package sv.edu.ues.delivery.control.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
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
import sv.edu.ues.delivery.control.dao.TipoProductoDao;
import sv.edu.ues.delivery.entity.TipoProducto;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioTipoProductoTest {
    
    @Mock
    private Validator validadorMock;

    @Mock
    private TipoProductoDao tipoProductoDao;

    @InjectMocks
    private ServicioTipoProducto servicioTipoProducto;

    private TipoProducto tipoProducto;

    private TipoProducto tipoProductoValido;

    private Validator validador;

    @BeforeAll
    public void setup(){
        tipoProducto = new TipoProducto();
        
        tipoProducto.setActivo(true);
        tipoProducto.setComentarios(null); //no debe ser null por eso pasa la prueba
        tipoProducto.setId(1L);
        tipoProducto.setNombre(null); //no debe ser null

        tipoProductoValido = new TipoProducto();
        tipoProductoValido.setId(2L);
        tipoProductoValido.setActivo(true);
        tipoProductoValido.setComentarios("Algun comentario");
        tipoProductoValido.setNombre("Gasolina");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
        public void testServicioListar(){
         TipoProducto underTest = new TipoProducto();
         underTest.setActivo(true);
         underTest.setComentarios("Buena marca");
         underTest.setId(2L);
         underTest.setNombre("tomate");

        List<TipoProducto> listadoEsperado = Arrays.asList(underTest);

        when(servicioTipoProducto.listar()).thenReturn(listadoEsperado);

        List<TipoProducto> listadoObtenido = servicioTipoProducto.listar();

        assertEquals(listadoEsperado, listadoObtenido);

        }

    @Test
        public void testServicioObtenerPorId(){
            TipoProducto tipoProductoEsperado = new TipoProducto();
            tipoProductoEsperado.setActivo(true);
            tipoProductoEsperado.setComentarios("Buena marca");
            tipoProductoEsperado.setId(3L);
            tipoProductoEsperado.setNombre("Queso");

            when(servicioTipoProducto.obtenerPorId(3L)).thenReturn(Optional.ofNullable(tipoProductoEsperado));
            TipoProducto tipoProductoObtenido = servicioTipoProducto.obtenerPorId(3L).get();

            assertEquals(tipoProductoEsperado,tipoProductoObtenido);


        }


    @Test
    public void testServicioInsertar(){
        Set<ConstraintViolation<TipoProducto>> errores = validador.validate(tipoProducto);
        when(validadorMock.validate(tipoProducto)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioTipoProducto.insertar(tipoProducto);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();
        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(tipoProductoValido);
        when(validadorMock.validate(tipoProductoValido)).thenReturn(errores);
        when(servicioTipoProducto.insertar(tipoProductoValido)).thenReturn(tipoProductoValido);

        TipoProducto tipoProductoObtenido = servicioTipoProducto.insertar(tipoProductoValido);

        assertEquals(0, errores.size());
        assertNotNull(tipoProductoObtenido);
        assertEquals(tipoProductoValido, tipoProductoObtenido);
    } 
    
    @Test
    public void testServicioActualizar(){
        Set<ConstraintViolation<TipoProducto>> errores = validador.validate(tipoProducto);
        when(validadorMock.validate(tipoProducto)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioTipoProducto.actualizar(tipoProducto);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(tipoProductoValido);
        when(validadorMock.validate(tipoProductoValido)).thenReturn(errores);
        when(servicioTipoProducto.actualizar(tipoProductoValido)).thenReturn(tipoProductoValido);

        TipoProducto tipoProductoObtenido = servicioTipoProducto.actualizar(tipoProductoValido);

        assertEquals(0, errores.size());
        assertNotNull(tipoProductoObtenido);
        assertEquals(tipoProductoValido, tipoProductoObtenido);
    }

    @Test
    public void testServicioEliminar(){
        Set<ConstraintViolation<TipoProducto>> errores = validador.validate(tipoProducto);
        when(validadorMock.validate(tipoProducto)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioTipoProducto.eliminar(tipoProducto);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(tipoProductoValido);
        when(validadorMock.validate(tipoProductoValido)).thenReturn(errores);
        when(servicioTipoProducto.eliminar(tipoProductoValido)).thenReturn(tipoProductoValido);

        TipoProducto tipoProductoObtenido = servicioTipoProducto.eliminar(tipoProductoValido);

        assertEquals(0, errores.size());
        assertNotNull(tipoProductoObtenido);
        assertEquals(tipoProductoValido, tipoProductoObtenido);
    }
    
}
