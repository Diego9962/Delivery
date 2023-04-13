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
 * @author AL17035
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SucursalTest {
    
    private Validator validator;
    
    @BeforeAll
    public void setUp(){
        
        ValidatorFactory validatorfactory = Validation.buildDefaultValidatorFactory();
        validator = validatorfactory.getValidator();
    }
   
    @Test
    public void validarSucursal(){
        Sucursal sucursal = new Sucursal();
        
        sucursal.setId(1L);
        sucursal.setTelefono("7000-0000");
        sucursal.setNombre("La Rotonda");
        
        Set<ConstraintViolation<Sucursal>> violations = validator.validate(sucursal);
        System.out.println("este es el error " +  violations + " y " + violations.size());
        assertEquals(0, violations.size());
       
        assertEquals(1L, sucursal.getId());
        assertEquals("7000-0000", sucursal.getTelefono());
        assertEquals("La Rotonda", sucursal.getNombre());
    }
    
}
