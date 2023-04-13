
package sv.edu.ues.delivery.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class FacturaTest {
    
    private Validator validator;
    
    @BeforeAll
    public void setUp(){
        
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

    }

    @Test
    public void validarFactura(){
        
        Factura factura = new Factura();
        
        factura.setId(1L);
        Timestamp fechayHora = Timestamp.valueOf(LocalDateTime.now());
        factura.setFechaEmision(fechayHora);
        factura.setAnulada(true);
        factura.setObservaciones("No hay observaciones hasta el momento");
        
        Set<ConstraintViolation<Factura>> violations = validator.validate(factura);
        assertEquals(0, violations.size());
        
        assertEquals(1L, factura.getId());
        assertEquals(fechayHora, factura.getFechaEmision());
        assertEquals(true, factura.isAnulada());
        assertEquals("No hay observaciones hasta el momento", factura.getObservaciones());
                        
    }
    
}
