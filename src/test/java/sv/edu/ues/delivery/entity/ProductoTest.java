
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
public class ProductoTest {
    
    private Validator validator;
    
    @BeforeAll
    public void setUp(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        //System.out.println("esta es la de validacion " + validatorFactory);
    }
    
    @Test
    public void validarProducto(){
        Producto producto = new Producto();
        
        producto.setCodigo("110110");
        producto.setNombre("Pizza");
        producto.setDescripcion("Piza con peperoni");
        producto.setActivo(true);
        producto.setPrecioCompra(22.5);
        producto.setPrecioVenta(30.0);
        producto.setCantidadExistente(50);
        
        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);
        assertEquals(0, violations.size());
        assertEquals("110110", producto.getCodigo());
        assertEquals("Pizza", producto.getNombre());
        assertEquals("Piza con peperoni", producto.getDescripcion());
        assertEquals(true, producto.isActivo());
        assertEquals(22.5, producto.getPrecioCompra());
        assertEquals(30.0, producto.getPrecioVenta());
        assertEquals(50, producto.getCantidadExistente());
    }
}
