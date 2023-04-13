package sv.edu.ues.delivery.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TipoProductoTest {
    
    private Validator validator;
    
    @BeforeAll
    public void setUp(){
        ValidatorFactory validatorfactory = Validation.buildDefaultValidatorFactory();
        validator = validatorfactory.getValidator();
    }

    @Test
    public void validarTipoProducto(){
        TipoProducto tipoProducto = new TipoProducto();

        tipoProducto.setId(1L);
        tipoProducto.setNombre("Productos Alimenticios");
        tipoProducto.setComentarios("no hay comentarios");
        tipoProducto.setActivo(true);

        Set<ConstraintViolation<TipoProducto>> violations = validator.validate(tipoProducto);
        assertEquals(0, violations.size());
        
        assertEquals(1L, tipoProducto.getId());
        assertEquals("Productos Alimenticios", tipoProducto.getNombre());
        assertEquals("no hay comentarios", tipoProducto.getComentarios());
        assertEquals(true, tipoProducto.isActivo());
        
    }

}
