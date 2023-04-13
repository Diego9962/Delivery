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
public class VehiculoTest {
    
    private Validator validator;

    @BeforeAll
    public void setUp(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void validarVehiculo(){
        Vehiculo vehiculo = new Vehiculo();
        Long id = 1L;
        String propietario = "Juan José Martínez";
        String placa = "291-341";
        String tipoVehiculo = "Toyota Corolla";
        String comentario = "Este es un comentario";

        vehiculo.setId(id);
        vehiculo.setPropietario(propietario);
        vehiculo.setPlaca("291-341");
        vehiculo.setTipoVehiculo(tipoVehiculo);
        vehiculo.setComentario(comentario);
        vehiculo.setActivo(true);

        Set<ConstraintViolation<Vehiculo>> violations = validator.validate(vehiculo);
        assertEquals(0, violations.size());
        assertEquals(id, vehiculo.getId());
        assertEquals(propietario, vehiculo.getPropietario());
        assertEquals(placa, vehiculo.getPlaca());
        assertEquals(tipoVehiculo, vehiculo.getTipoVehiculo());
        assertEquals(comentario, vehiculo.getComentario());
        assertEquals(true, vehiculo.isActivo());
        
    }

}
