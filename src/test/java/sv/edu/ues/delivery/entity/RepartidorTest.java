package sv.edu.ues.delivery.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Date;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sv.edu.ues.delivery.entity.TipoLicencia.CLASE_C;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 *
 * @author AL17035
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepartidorTest {
    
    private Validator validator;
    
    @BeforeAll
    public void setUp(){
        
        ValidatorFactory validatorfactory = Validation.buildDefaultValidatorFactory();
        validator = validatorfactory.getValidator();
    }
    
    @Test
    public void validarRepartidor(){
        
        Repartidor repartidor = new Repartidor();
        Date fechaNacimiento = new Date();
   
        
        repartidor.setId(1L);
        repartidor.setNombre("Juan");
        repartidor.setApellido("Perez");
        repartidor.setFechaNacimiento(fechaNacimiento);
        repartidor.setObservacion("No hay observaciones");
        repartidor.setActivo(true);
        repartidor.setSalario(365.34);
        repartidor.setTipoLicencia(CLASE_C);

        Set<ConstraintViolation<Repartidor>> violations = validator.validate(repartidor);
        System.out.println("Este es el problema "+ violations + " hay tantos problemas " + violations.size());
        assertEquals(1L, repartidor.getId());
        assertEquals("Juan", repartidor.getNombre());
        assertEquals("Perez", repartidor.getApellido());
        assertEquals(fechaNacimiento, repartidor.getFechaNacimiento());
        assertEquals("No hay observaciones", repartidor.getObservacion());
        assertEquals(true, repartidor.isActivo());
        assertEquals(365.34, repartidor.getSalario());
        assertEquals(CLASE_C, repartidor.getTipoLicencia());
    }
    
}
