package sv.edu.ues.delivery.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EntregaTest {
    
    private Validator validator;
    
    @BeforeAll
    public void setUp(){
        ValidatorFactory validatorfactory = Validation.buildDefaultValidatorFactory();
        validator = validatorfactory.getValidator();
    }

    @Test
    public void validarEntrega(){
        Entrega entrega = new Entrega();
        Timestamp fechaYHora = Timestamp.valueOf(LocalDateTime.now());

        entrega.setId(1L);
        entrega.setObservaciones("No se encuentran observaciones");
        entrega.setFechaCreacion(fechaYHora);
        entrega.setEstadoEntrega(EstadoEntrega.ENTREGADO);
        entrega.setFechaAlcanzado(fechaYHora);
    
        Set<ConstraintViolation<Entrega>> violations = validator.validate(entrega);
        assertEquals(0, violations.size());
        assertEquals(1L, entrega.getId());
        assertEquals("No se encuentran observaciones", entrega.getObservaciones());
        assertEquals(fechaYHora, entrega.getFechaCreacion());
        assertEquals(EstadoEntrega.ENTREGADO, entrega.getEstadoEntrega());
        assertEquals(fechaYHora, entrega.getFechaAlcanzado());
        
    }

}
