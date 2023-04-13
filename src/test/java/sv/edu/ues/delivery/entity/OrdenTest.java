package sv.edu.ues.delivery.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Date;
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
public class OrdenTest {
    
    private Validator validator;

    @BeforeAll
    public void setUp(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Test
    public void validarOrden(){
        Orden orden = new Orden();
        Date fechaOrden = new Date();
        orden.setId(1L);
        orden.setFechaOrden(fechaOrden);
        orden.setObservaciones("Esta es una observación para orden");
        orden.setEstado(EstadoOrden.RESUELTA);
        

        Set<ConstraintViolation<Orden>> violations = validator.validate(orden);

        assertEquals(0, violations.size());
        assertEquals(1L, orden.getId());
        assertEquals(fechaOrden, orden.getFechaOrden());
        assertEquals("Esta es una observación para orden", orden.getObservaciones());
        assertEquals(EstadoOrden.RESUELTA, orden.getEstado());
    }

}
