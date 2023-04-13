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
public class TipoComercioTest {
    private Validator validator;
    
    @BeforeAll
    public void setUp(){
        
        ValidatorFactory validatorfactory = Validation.buildDefaultValidatorFactory();
        validator = validatorfactory.getValidator();
    }
    
    @Test
    public void validarTipoComercio(){
        TipoComercio tipoComercio = new TipoComercio();
        
        tipoComercio.setId(1L);
        tipoComercio.setNombre("Comida");
        tipoComercio.setActivo(false);
        tipoComercio.setComentarios("Productos de comida");
        
        Set<ConstraintViolation<TipoComercio>> violations = validator.validate(tipoComercio);
        System.out.println("Este es el problema "+ violations + " hay tantos problemas " + violations.size());
        assertEquals(0, violations.size());
        
        assertEquals(1L, tipoComercio.getId());
        assertEquals("Comida", tipoComercio.getNombre());
        assertEquals(false, tipoComercio.isActivo());
        assertEquals("Productos de comida", tipoComercio.getComentarios());
    }
}
