
package sv.edu.ues.delivery.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.math.BigDecimal;
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
public class DireccionTest {
    
    private Validator validator;
    
    @BeforeAll
    public void setUp(){
        
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        
    }
    
    @Test
    public void validarDireccion(){
        Direccion direccion = new Direccion();
        
        direccion.setId(1L);
        direccion.setDireccion("Carretera a Santa Ana");
        direccion.setLatitud(BigDecimal.TEN);
        direccion.setLongitud(BigDecimal.ONE);
    
        Set<ConstraintViolation<Direccion>> violations = validator.validate(direccion);
        
        assertEquals(0, violations.size());
        assertEquals(1L,direccion.getId());
        assertEquals("Carretera a Santa Ana", direccion.getDireccion());
        assertEquals(BigDecimal.TEN, direccion.getLatitud());
        assertEquals(BigDecimal.ONE, direccion.getLongitud());
        
    }
    
}
