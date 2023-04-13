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
import sv.edu.ues.delivery.control.dao.TipoComercioDao;
import sv.edu.ues.delivery.entity.TipoComercio;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioTipoComercioTest {
    
    @Mock
    private Validator validadorMock;

    @Mock
    private TipoComercioDao tipoComercioDao;

    @InjectMocks
    private ServicioTipoComercio servicioTipoComercio;

    private TipoComercio tipoComercio;

    private TipoComercio tipoComercioValido;

    private Validator validador;

    @BeforeAll
    public void setup(){
        tipoComercio = new TipoComercio();
       
        tipoComercio.setActivo(true);
        tipoComercio.setId(1L);
        tipoComercio.setComentarios("Productos de comida");
        tipoComercio.setNombre(null); //no debe ser null por eso cumple la prueba

        tipoComercioValido = new TipoComercio();
        tipoComercioValido.setId(2L);
        tipoComercioValido.setComentarios("Comentarios...");
        tipoComercioValido.setActivo(true);
        tipoComercioValido.setNombre("Algun nombre");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
        public void testServicioListar(){
            TipoComercio underTest = new TipoComercio();
            underTest.setActivo(true);
            underTest.setComentarios("Buen Comercio");
            underTest.setId(2L);
            underTest.setNombre("Hut");

            List<TipoComercio> listadoEsperado = Arrays.asList(underTest);

            when(servicioTipoComercio.listar()).thenReturn(listadoEsperado);

            List<TipoComercio> listadoObtenido = servicioTipoComercio.listar();

            assertEquals(listadoEsperado, listadoObtenido);

        }

    @Test
        public void testServicioObtenerPorId(){
            TipoComercio tipoComercioEsperado = new TipoComercio();
            tipoComercioEsperado.setActivo(true);
            tipoComercioEsperado.setComentarios("Mal Comercio");
            tipoComercioEsperado.setId(3L);
            tipoComercioEsperado.setNombre("litte");

            when(servicioTipoComercio.obtenerPorId(3L)).thenReturn(Optional.ofNullable(tipoComercioEsperado));
            TipoComercio tipoComercioObtenido = servicioTipoComercio.obtenerPorId(3L).get();

            assertEquals(tipoComercioEsperado, tipoComercioObtenido);


        }


    @Test
    public void testServicioInsertar(){
        Set<ConstraintViolation<TipoComercio>> errores = validador.validate(tipoComercio);
        when(validadorMock.validate(tipoComercio)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioTipoComercio.insertar(tipoComercio);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();
        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(tipoComercioValido);
        when(validadorMock.validate(tipoComercioValido)).thenReturn(errores);
        when(servicioTipoComercio.insertar(tipoComercioValido)).thenReturn(tipoComercioValido);

        TipoComercio tipoComercioObtenido = servicioTipoComercio.insertar(tipoComercioValido);

        assertEquals(0, errores.size());
        assertNotNull(tipoComercioObtenido);
        assertEquals(tipoComercioValido, tipoComercioObtenido);
    } 
    
    @Test
    public void testServicioActualizar(){
        Set<ConstraintViolation<TipoComercio>> errores = validador.validate(tipoComercio);
        when(validadorMock.validate(tipoComercio)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioTipoComercio.actualizar(tipoComercio);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(tipoComercioValido);
        when(validadorMock.validate(tipoComercioValido)).thenReturn(errores);
        when(servicioTipoComercio.actualizar(tipoComercioValido)).thenReturn(tipoComercioValido);

        TipoComercio tipoComercioObtenido = servicioTipoComercio.actualizar(tipoComercioValido);

        assertEquals(0, errores.size());
        assertNotNull(tipoComercioObtenido);
        assertEquals(tipoComercioValido, tipoComercioObtenido);
    }

    @Test
    public void testServicioEliminar(){
        Set<ConstraintViolation<TipoComercio>> errores = validador.validate(tipoComercio);
        when(validadorMock.validate(tipoComercio)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioTipoComercio.eliminar(tipoComercio);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(tipoComercioValido);
        when(validadorMock.validate(tipoComercioValido)).thenReturn(errores);
        when(servicioTipoComercio.eliminar(tipoComercioValido)).thenReturn(tipoComercioValido);

        TipoComercio tipoComercioObtenido = servicioTipoComercio.eliminar(tipoComercioValido);

        assertEquals(0, errores.size());
        assertNotNull(tipoComercioObtenido);
        assertEquals(tipoComercioValido, tipoComercioObtenido);
    }
    
}
