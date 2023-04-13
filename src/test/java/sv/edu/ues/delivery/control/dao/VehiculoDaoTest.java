package sv.edu.ues.delivery.control.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.delivery.entity.Vehiculo;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class VehiculoDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<Vehiculo> query;
    
    @Mock
    private Root<Vehiculo> root;

    @Mock
    private TypedQuery<Vehiculo> typedQuery;
    
    @InjectMocks
    private VehiculoDao vehiculoDao;
    
    private Vehiculo vehiculo;

    @BeforeAll
    public void setup(){
        vehiculo = new Vehiculo();
        
        vehiculo.setId(1L);
        vehiculo.setPlaca("P291-341");
        vehiculo.setPropietario("Oscar Alexander Escalante");
        vehiculo.setTipoVehiculo("Toyota");
        vehiculo.setActivo(true);
        vehiculo.setComentario("Este es el comentario");
    }
        //SHOWLIST
        @Test
        public void testListarVehiculo(){
        List<Vehiculo> resultadoEsperado = Arrays.asList(vehiculo);
     
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Vehiculo.class)).thenReturn(query);
        when(query.from(Vehiculo.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Vehiculo> resultado = vehiculoDao.listar();
        assertEquals(resultadoEsperado, resultado);
 }
 
    //READ
    @Test
    public void testObtenerVehiculoPorId(){
        Long id = 1L;
        when(em.find(Vehiculo.class, id)).thenReturn(vehiculo);
        Vehiculo vehiculoDevuelto = vehiculoDao.obtenerPorId(1L).get();
        assertEquals(vehiculo, vehiculoDevuelto);
 }

    //CREATE
    @Test
    public void testInsertarVehiculo(){
        Vehiculo vehiculoDevuelto = vehiculoDao.insertar(vehiculo);
        verify(em, times(1)).persist(vehiculo);
        assertEquals(vehiculo, vehiculoDevuelto);

}

     //UPDATE
    @Test
    public void testActualizarVehiculo(){
        when(em.merge(vehiculo)).thenReturn(vehiculo);
        Vehiculo vehiculoDevuelto = vehiculoDao.actualizar(vehiculo);
        verify(em, times(1)).merge(vehiculo);
        assertEquals(vehiculo, vehiculoDevuelto);
 }

  //DELETE
    @Test
    public void testEliminarVehiculo(){
      vehiculoDao.eliminar(vehiculo);
      verify(em, times(1)).remove(em.contains(vehiculo)? vehiculo : em.merge(vehiculo));
  }
  

}
