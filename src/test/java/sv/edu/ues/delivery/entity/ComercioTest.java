
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
public class ComercioTest {
    
    private Validator validator;
    
    @BeforeAll
    public void setUp(){
        ValidatorFactory validatorfactory = Validation.buildDefaultValidatorFactory();
        validator = validatorfactory.getValidator();
    }
    
    @Test
    public void validarComercio(){
        Comercio comercio = new Comercio();

        comercio.setId(1L);
        comercio.setNombre("Pizza Hut");
        comercio.setDescripcion("Pizza Hut Santa Ana");
        comercio.setActivo(true);
        comercio.setLogo("https://cdn.pixabay.com/photo/2012/05/29/00/43/car-49278_640.jpg");
        
        Set<ConstraintViolation<Comercio>> violations = validator.validate(comercio);
        assertEquals(0, violations.size());
        assertEquals(1L, comercio.getId());
        assertEquals("Pizza Hut Santa Ana", comercio.getDescripcion());
        assertEquals(true, comercio.isActivo());
        assertEquals("https://cdn.pixabay.com/photo/2012/05/29/00/43/car-49278_640.jpg", comercio.getLogo());
        
        
    }
    
}
