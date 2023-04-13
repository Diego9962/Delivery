
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TerritorioTest {
    
    private Validator validator;
    
    @BeforeAll
    public void setUp(){
        ValidatorFactory validatorfactory = Validation.buildDefaultValidatorFactory();
        validator = validatorfactory.getValidator();
    }
    
    @Test
    public void validarTerritorio(){
        Territorio territorio = new Territorio();

        territorio.setIdTerritorio(1L);
        territorio.setNombre("El Salvador");
        territorio.setTextoVisible("sv");
        territorio.setHijosObligatorios(5);
        
        Set<ConstraintViolation<Territorio>> violations = validator.validate(territorio);
        assertEquals(0, violations.size());
        
        assertEquals(1L, territorio.getIdTerritorio());
        assertEquals("El Salvador", territorio.getNombre());
        assertEquals("sv", territorio.getTextoVisible());
        assertEquals(5, territorio.getHijosObligatorios());
        
    }
    
}
