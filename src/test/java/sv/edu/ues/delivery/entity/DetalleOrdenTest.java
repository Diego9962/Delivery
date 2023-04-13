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
public class DetalleOrdenTest {

    private Validator validator;
    
    @BeforeAll
    public void setUp(){
        ValidatorFactory validatorfactory = Validation.buildDefaultValidatorFactory();
        validator = validatorfactory.getValidator();
    }

    @Test
    public void validarDetalleOrden(){
        DetalleOrden detalleOrden = new DetalleOrden();

        detalleOrden.setIdOrden(1L);
        detalleOrden.setCodigoProducto("123456");
        detalleOrden.setCantidad(3);

        Set<ConstraintViolation<DetalleOrden>> violations = validator.validate(detalleOrden);
        assertEquals(0, violations.size());
        
        assertEquals(1L, detalleOrden.getIdOrden());
        assertEquals("123456", detalleOrden.getCodigoProducto());
        assertEquals(3, detalleOrden.getCantidad());
        
    }
    

}
