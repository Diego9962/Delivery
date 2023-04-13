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
import sv.edu.ues.delivery.control.dao.TerritorioDao;
import sv.edu.ues.delivery.entity.Territorio;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioTerritorioTest {
    
    @Mock
    private Validator validadorMock;

    @Mock
    private TerritorioDao territorioDao;

    @InjectMocks
    private ServicioTerritorio servicioTerritorio;

    private Territorio territorio;

    private Territorio territorioValido;

    private Validator validador;

    @BeforeAll
    public void setup(){
        territorio = new Territorio();
        territorio.setIdTerritorio(1L);
        territorio.setNombre("Algun nombre");
        territorio.setTextoVisible("este texto");
        territorio.setHijosObligatorios(-2); // debe ser mayor o igual a cero : por eso cumple

        territorioValido = new Territorio();
        territorioValido.setIdTerritorio(2L);
        territorioValido.setNombre("Algun otro nombre");
        territorioValido.setTextoVisible("Pais");
        territorioValido.setHijosObligatorios(12);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar(){
            
        Territorio territorioList = new Territorio();
        territorioList.setIdTerritorio(2L);
        territorioList.setNombre("algun nombre");
        territorioList.setTextoVisible("el texto");
        territorioList.setHijosObligatorios(-4);

        List<Territorio> listadoEsperado = Arrays.asList(territorioList);

        when(servicioTerritorio.listar()).thenReturn(listadoEsperado);

        List<Territorio> listadoObtenido = servicioTerritorio.listar();
        assertEquals(listadoEsperado, listadoObtenido);
    }

    @Test
    public void testServicioObtenerPorId(){
        Territorio territorioEsperado = new Territorio();
        territorioEsperado.setIdTerritorio(3L);
        territorioEsperado.setNombre("algun nombre");
        territorioEsperado.setTextoVisible("el texto es visible");
        territorioEsperado.setHijosObligatorios(7);

        when(servicioTerritorio.obtenerPorId(3L)).thenReturn(Optional.ofNullable(territorioEsperado));
        Territorio territorioObtenido = servicioTerritorio.obtenerPorId(3L).get();

        assertEquals(territorioEsperado, territorioObtenido);
    }


    @Test
    public void testServicioInsertar(){
        Set<ConstraintViolation<Territorio>> errores = validador.validate(territorio);
        when(validadorMock.validate(territorio)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioTerritorio.insertar(territorio);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(territorioValido);
        when(validadorMock.validate(territorioValido)).thenReturn(errores);
        when(servicioTerritorio.insertar(territorioValido)).thenReturn(territorioValido);

        Territorio territorioObtenido = servicioTerritorio.insertar(territorioValido);

        assertEquals(0, errores.size());
        assertNotNull(territorioObtenido);
        assertEquals(territorioValido, territorioObtenido);
    } 
    
    @Test
    public void testServicioActualizar(){
        Set<ConstraintViolation<Territorio>> errores = validador.validate(territorio);
        when(validadorMock.validate(territorio)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioTerritorio.actualizar(territorio);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(territorioValido);
        when(validadorMock.validate(territorioValido)).thenReturn(errores);
        when(servicioTerritorio.actualizar(territorioValido)).thenReturn(territorioValido);

        Territorio territorioObtenido = servicioTerritorio.actualizar(territorioValido);

        assertEquals(0, errores.size());
        assertNotNull(territorioObtenido);
        assertEquals(territorioValido, territorioObtenido);
    }

    @Test
    public void testServicioEliminar(){
        Set<ConstraintViolation<Territorio>> errores = validador.validate(territorio);
        when(validadorMock.validate(territorio)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioTerritorio.eliminar(territorio);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(territorioValido);
        when(validadorMock.validate(territorioValido)).thenReturn(errores);
        when(servicioTerritorio.eliminar(territorioValido)).thenReturn(territorioValido);

        Territorio territorioObtenido = servicioTerritorio.eliminar(territorioValido);

        assertEquals(0, errores.size());
        assertNotNull(territorioObtenido);
        assertEquals(territorioValido, territorioObtenido);
    }
    
}
