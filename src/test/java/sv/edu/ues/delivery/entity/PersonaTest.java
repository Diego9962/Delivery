package sv.edu.ues.delivery.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonaTest {

    private Validator validator;

    @BeforeAll
    public void setUp(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Test
    public void validarAnotaciones(){
        Persona persona = new Persona();
        Date fechaNacimiento = new Date();
        
        persona.setId(1L);
        persona.setNombre("Jairo");
        persona.setApellido("Flores");
        persona.setDireccion("this address if only for testing purpose");
        persona.setFechaNacimiento(fechaNacimiento);

        Set<ConstraintViolation<Persona>> violations = validator.validate(persona);

        assertEquals(0, violations.size());
        assertEquals(1L, persona.getId());
        assertEquals("Jairo", persona.getNombre());
        assertEquals("Flores", persona.getApellido());
        assertEquals("this address if only for testing purpose", persona.getDireccion());
        assertEquals(fechaNacimiento, persona.getFechaNacimiento());
    }
 }
