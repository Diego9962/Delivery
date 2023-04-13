package sv.edu.ues.delivery.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 *
 * @author dominguez
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PagoTest {
    
   private Validator validator;
    
   @BeforeAll
   public void setUp(){
       
       ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
       validator = validatorFactory.getValidator();
       
   }
   
   @Test
   public void validarPago(){
       
       Pago pago = new Pago();
       
       pago.setId(1L);
       pago.setMonto(23.4f);
       pago.setReferencia(""); //verificar y validar que acepte un minimo de caracteres
       pago.setTipoPago("Efectivo");
       pago.setEstado("CANCELADO");
       
       Set<ConstraintViolation<Pago>> violations = validator.validate(pago);
       assertEquals(0, violations.size());
       assertEquals(23.4f, pago.getMonto());
       assertEquals("", pago.getReferencia());
       assertEquals("Efectivo", pago.getTipoPago());
       assertEquals("CANCELADO", pago.getEstado());
   }
   
}
