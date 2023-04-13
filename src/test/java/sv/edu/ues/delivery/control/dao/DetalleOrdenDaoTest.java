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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.delivery.entity.DetalleOrden;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DetalleOrdenDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<DetalleOrden> query;
    
    @Mock
    private Root<DetalleOrden> root;

    @Mock
    private TypedQuery<DetalleOrden> typedQuery;
    
    @InjectMocks
    private DetalleOrdenDao detalleOrdenDao;
    
    private DetalleOrden detalleOrden;
    
    @BeforeAll
    public void setup(){
        detalleOrden = new DetalleOrden();
        
        detalleOrden.setIdOrden(1L);
        detalleOrden.setCantidad(3);
        detalleOrden.setCodigoProducto("1234567");
    }
    
    //SHOWLIST
    @Test
    public void testListarDetalleOrden(){
        List<DetalleOrden> resultadoEsperado = Arrays.asList(detalleOrden);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(DetalleOrden.class)).thenReturn(query);
        when(query.from(DetalleOrden.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<DetalleOrden> resultado = detalleOrdenDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }
    
    //READ
    @Test
    public void testObtenerDetalleOrdenPorId(){
        Long idOrden = 1L;
        when(em.find(DetalleOrden.class, idOrden)).thenReturn(detalleOrden);
        DetalleOrden detalleOrdenDevuelto = detalleOrdenDao.obtenerPorId(1L).get();
        assertEquals(detalleOrden, detalleOrdenDevuelto);
    }
    
    //CREATE
    @Test
    public void testInsertarDetalleOrden(){
        DetalleOrden detalleOrdenDevuelto = detalleOrdenDao.insertar(detalleOrden);
        verify(em, times(1)).persist(detalleOrden);
        assertEquals(detalleOrden, detalleOrdenDevuelto);
    }
    
    //UPDATE
    @Test
    public void testActualizarDetalleOrden(){
        when(em.merge(detalleOrden)).thenReturn(detalleOrden);
        DetalleOrden detalleOrdenDevuelto = detalleOrdenDao.actualizar(detalleOrden);
        verify(em, times(1)).merge(detalleOrden);
        assertEquals(detalleOrden, detalleOrdenDevuelto);
    }
    
    //DELETE
    @Test
    public void testEliminarDetalleOrden(){
        detalleOrdenDao.eliminar(detalleOrden);
        verify(em, times(1)).remove(em.contains(detalleOrden)? detalleOrden : em.merge(detalleOrden));
    }
    
}
