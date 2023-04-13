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
import sv.edu.ues.delivery.control.dao.PagoDao;
import sv.edu.ues.delivery.entity.Pago;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioPagoTest {
    
    @Mock
    private Validator validadorMock;

    @Mock
    private PagoDao pagoDao;

    @InjectMocks
    private ServicioPago servicioPago;

    private Pago pago;

    private Pago pagoValido;

    private Validator validador;

    @BeforeAll
    public void setup(){
        pago = new Pago();
       
        pago.setId(1L);
        pago.setMonto(23.4f);
        pago.setReferencia(null); //no debe ser null : por eso satisface
        pago.setTipoPago(null); //no debe ser null
        pago.setEstado(null); //no debe ser null

        pagoValido = new Pago();
        pagoValido.setId(2L);
        pagoValido.setMonto(50f);
        pagoValido.setReferencia("Alguna Referencia");
        pagoValido.setTipoPago("Contado");
        pagoValido.setEstado("Procesado");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
        public void testServicioListar(){
         Pago underTest = new Pago();
         underTest.setEstado("entregado");
         underTest.setId(2L);
         underTest.setMonto(123);
         underTest.setReferencia("no hay");
         underTest.setTipoPago("Efectivo");
         
        List<Pago> listadoEsperado = Arrays.asList(underTest);

        when(servicioPago.listar()).thenReturn(listadoEsperado);

        List<Pago> listadoObtenido = servicioPago.listar();

        assertEquals(listadoEsperado,listadoObtenido);
        }

    @Test
        public void testServicioObtenerPorId(){
            Pago pagoEsperado = new Pago();
            pagoEsperado.setEstado("entregado");
            pagoEsperado.setId(3L);
            pagoEsperado.setMonto(127);
            pagoEsperado.setReferencia("no hay");
            pagoEsperado.setTipoPago("Debito");

            when(servicioPago.obtenerPorId(3L)).thenReturn(Optional.ofNullable(pagoEsperado));
            Pago pagoObtenido = servicioPago.obtenerPorId(3L).get();

            assertEquals(pagoEsperado, pagoObtenido);
        }


    @Test
    public void testServicioInsertar(){
        Set<ConstraintViolation<Pago>> errores = validador.validate(pago);
        when(validadorMock.validate(pago)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioPago.insertar(pago);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();
        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(pagoValido);
        when(validadorMock.validate(pagoValido)).thenReturn(errores);
        when(servicioPago.insertar(pagoValido)).thenReturn(pagoValido);

        Pago pagoObtenido = servicioPago.insertar(pagoValido);

        assertEquals(0, errores.size());
        assertNotNull(pagoObtenido);
        assertEquals(pagoValido, pagoObtenido);
    } 
    
    @Test
    public void testServicioActualizar(){
        Set<ConstraintViolation<Pago>> errores = validador.validate(pago);
        when(validadorMock.validate(pago)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioPago.actualizar(pago);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(pagoValido);
        when(validadorMock.validate(pagoValido)).thenReturn(errores);
        when(servicioPago.actualizar(pagoValido)).thenReturn(pagoValido);

        Pago pagoObtenido = servicioPago.actualizar(pagoValido);

        assertEquals(0, errores.size());
        assertNotNull(pagoObtenido);
        assertEquals(pagoValido, pagoObtenido);
        
    }

    @Test
    public void testServicioEliminar(){
        Set<ConstraintViolation<Pago>> errores = validador.validate(pago);
        when(validadorMock.validate(pago)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioPago.eliminar(pago);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(pagoValido);
        when(validadorMock.validate(pagoValido)).thenReturn(errores);
        when(servicioPago.eliminar(pagoValido)).thenReturn(pagoValido);

        Pago pagoObtenido = servicioPago.eliminar(pagoValido);

        assertEquals(0, errores.size());
        assertNotNull(pagoObtenido);
        assertEquals(pagoValido, pagoObtenido);
    }
    
}
