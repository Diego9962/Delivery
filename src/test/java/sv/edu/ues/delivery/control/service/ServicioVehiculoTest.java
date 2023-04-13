package sv.edu.ues.delivery.control.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import sv.edu.ues.delivery.control.dao.VehiculoDao;
import sv.edu.ues.delivery.entity.Vehiculo;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ServicioVehiculoTest {
    
    @Mock
    private Validator validadorMock;

    @Mock
    private VehiculoDao vehiculoDao;

    @InjectMocks
    private ServicioVehiculo servicioVehiculo;

    private Vehiculo vehiculo;

    private Vehiculo vehiculoValido;

    private Validator validador;

    @BeforeAll
    public void setup(){
        vehiculo = new Vehiculo();
        
        vehiculo.setId(1L);
        vehiculo.setPlaca(null); //no debe ser null por eso cumple la prueba
        vehiculo.setPropietario(null); //no debe ser null
        vehiculo.setTipoVehiculo(null); //no debe ser null
        vehiculo.setActivo(true);
        vehiculo.setComentario(null); //no debe ser null

        vehiculoValido = new Vehiculo();
        vehiculoValido.setId(2L);
        vehiculoValido.setPlaca("P123-659");
        vehiculoValido.setPropietario("Yo");
        vehiculoValido.setTipoVehiculo("Bicicleta");
        vehiculoValido.setActivo(true);
        vehiculoValido.setComentario("Algun comentario");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validador = validatorFactory.getValidator();
    }

    @Test
    public void testServicioListar(){

        Vehiculo vehiculoData = new Vehiculo();
        vehiculoData.setActivo(true);
        vehiculoData.setComentario("Buen vehiculo");
        vehiculoData.setId(2L);
        vehiculoData.setPlaca("021-320");
        vehiculoData.setPropietario("Toyota");
        vehiculoData.setTipoVehiculo("Premium");

        List<Vehiculo> listadoEsperado = Arrays.asList(vehiculoData);

        when(servicioVehiculo.listar()).thenReturn(listadoEsperado);

        List<Vehiculo> listadoObtenido = servicioVehiculo.listar();

        assertEquals(listadoEsperado,listadoObtenido);
        
    }

    @Test
        public void testServicioObtenerPorId(){
            Vehiculo vehiculoEsperado = new Vehiculo();
            vehiculoEsperado.setActivo(true);
            vehiculoEsperado.setComentario("mal vehiculo");
            vehiculoEsperado.setId(3L);
            vehiculoEsperado.setPlaca("000-123");
            vehiculoEsperado.setPropietario("Nissan");
            vehiculoEsperado.setTipoVehiculo("No premium");

            when(servicioVehiculo.obtenerPorId(3L)).thenReturn(Optional.ofNullable(vehiculoEsperado));
            Vehiculo vehiculoObtenido = servicioVehiculo.obtenerPorId(3L).get();

            assertEquals(vehiculoEsperado,vehiculoObtenido);
        }

    @Test
    public void testServicioInsertar(){
        Set<ConstraintViolation<Vehiculo>> errores = validador.validate(vehiculo);
        when(validadorMock.validate(vehiculo)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioVehiculo.insertar(vehiculo);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();
        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(vehiculoValido);
        when(validadorMock.validate(vehiculoValido)).thenReturn(errores);
        when(servicioVehiculo.insertar(vehiculoValido)).thenReturn(vehiculoValido);

        Vehiculo vehiculoObtenido = servicioVehiculo.insertar(vehiculoValido);

        assertEquals(0, errores.size());
        assertNotNull(vehiculoObtenido);
        assertEquals(vehiculoValido, vehiculoObtenido);
    } 
    
    @Test
    public void testServicioActualizar(){
        Set<ConstraintViolation<Vehiculo>> errores = validador.validate(vehiculo);
        when(validadorMock.validate(vehiculo)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioVehiculo.actualizar(vehiculo);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(vehiculoValido);
        when(validadorMock.validate(vehiculoValido)).thenReturn(errores);
        when(servicioVehiculo.actualizar(vehiculoValido)).thenReturn(vehiculoValido);

        Vehiculo vehiculoObtenido = servicioVehiculo.actualizar(vehiculoValido);

        assertEquals(0, errores.size());
        assertNotNull(vehiculoObtenido);
        assertEquals(vehiculoValido, vehiculoObtenido);
    }

    @Test
    public void testServicioEliminar(){
        Set<ConstraintViolation<Vehiculo>> errores = validador.validate(vehiculo);
        when(validadorMock.validate(vehiculo)).thenReturn(errores);

        ConstraintViolationException excepcion = assertThrows(ConstraintViolationException.class, () ->{
            servicioVehiculo.eliminar(vehiculo);
        });

        Set<ConstraintViolation<?>> erroresObtenidos = excepcion.getConstraintViolations();

        assertEquals(errores.size(), erroresObtenidos.size());

        errores = validador.validate(vehiculoValido);
        when(validadorMock.validate(vehiculoValido)).thenReturn(errores);
        when(servicioVehiculo.eliminar(vehiculoValido)).thenReturn(vehiculoValido);

        Vehiculo vehiculoObtenido = servicioVehiculo.eliminar(vehiculoValido);

        assertEquals(0, errores.size());
        assertNotNull(vehiculoObtenido);
        assertEquals(vehiculoValido, vehiculoObtenido);
    }
    
}
