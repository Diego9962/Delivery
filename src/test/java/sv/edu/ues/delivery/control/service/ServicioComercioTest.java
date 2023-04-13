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
import sv.edu.ues.delivery.control.dao.ComercioDao;
import sv.edu.ues.delivery.entity.Comercio;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioComercioTest {
    
    @Mock
    private Validator validadorMock;

    @Mock
    private ComercioDao comercioDao;

    @InjectMocks
    private ServicioComercio servicioComercio;

    private Comercio comercioErroneo;

    private Comercio comercioValido;

    private Validator validador;

    @BeforeAll
    public void setup(){
        comercioErroneo = new Comercio();
        comercioErroneo.setId(1L);
        comercioErroneo.setNombre(null);
        comercioErroneo.setDescripcion("Alguna descipcion");
        comercioErroneo.setActivo(false);
        comercioErroneo.setLogo("Algun Logo");

        comercioValido = new Comercio();
        comercioValido.setId(2L);
        comercioValido.setNombre("Algun nombre");
        comercioValido.setDescripcion("Alguna descripciones");
        comercioValido.setActivo(true);
        comercioValido.setLogo("Algun logo bonito");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar(){
            
        Comercio comercioList = new Comercio();
        comercioList.setActivo(true);
        comercioList.setDescripcion("Este es un comercio situado en Santa Ana");
        comercioList.setId(1L);
        comercioList.setLogo("Algun Logo");
        comercioList.setNombre("Chapultepec");

        List<Comercio> listadoEsperado = Arrays.asList(comercioList);

        when(servicioComercio.listar()).thenReturn(listadoEsperado);

        List<Comercio> listadoObtenido = servicioComercio.listar();

        assertEquals(listadoEsperado, listadoObtenido);
    }

    @Test
    public void testServicioObtenerPorId(){
        Comercio comercioEsperado = new Comercio();
        comercioEsperado.setActivo(true);
        comercioEsperado.setDescripcion("Este es un comercio situado en San Salvador");
        comercioEsperado.setId(1L);
        comercioEsperado.setLogo("Algun Logo");
        comercioEsperado.setNombre("La Taberna");

        when(servicioComercio.obtenerPorId(1L)).thenReturn(Optional.ofNullable(comercioEsperado));
        Comercio comercioObtenido = servicioComercio.obtenerPorId(1L).get();

        assertEquals(comercioEsperado, comercioObtenido);
    }


    @Test
    public void testServicioInsertar(){
        Set<ConstraintViolation<Comercio>> errores = validador.validate(comercioErroneo);
        when(validadorMock.validate(comercioErroneo)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioComercio.insertar(comercioErroneo);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(comercioValido);
        when(validadorMock.validate(comercioValido)).thenReturn(errores);
        when(servicioComercio.insertar(comercioValido)).thenReturn(comercioValido);

        Comercio comercioObtenido = servicioComercio.insertar(comercioValido);

        assertEquals(0, errores.size());
        assertNotNull(comercioObtenido);
        assertEquals(comercioValido, comercioObtenido);

    } 
    
    @Test
    public void testServicioActualizar(){
        Set<ConstraintViolation<Comercio>> errores = validador.validate(comercioErroneo);
        when(validadorMock.validate(comercioErroneo)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioComercio.actualizar(comercioErroneo);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(comercioValido);
        when(validadorMock.validate(comercioValido)).thenReturn(errores);
        when(servicioComercio.actualizar(comercioValido)).thenReturn(comercioValido);

        Comercio comercioObtenido = servicioComercio.actualizar(comercioValido);

        assertEquals(0, errores.size());
        assertNotNull(comercioObtenido);
        assertEquals(comercioValido, comercioObtenido);
    }

    @Test
    public void testServicioEliminar(){
        Set<ConstraintViolation<Comercio>> errores = validador.validate(comercioErroneo);
        when(validadorMock.validate(comercioErroneo)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioComercio.eliminar(comercioErroneo);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(comercioValido);
        when(validadorMock.validate(comercioValido)).thenReturn(errores);
        when(servicioComercio.eliminar(comercioValido)).thenReturn(comercioValido);

        Comercio comercioObtenido = servicioComercio.eliminar(comercioValido);

        assertEquals(0, errores.size());
        assertNotNull(comercioObtenido);
        assertEquals(comercioValido, comercioObtenido);
    }
    
}
