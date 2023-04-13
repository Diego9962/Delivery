package sv.edu.ues.delivery.control.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.sql.Date;
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
import sv.edu.ues.delivery.entity.EstadoOrden;
import sv.edu.ues.delivery.entity.Orden;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrdenDaoTest {
    
     @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<Orden> query;
    
    @Mock
    private Root<Orden> root;

    @Mock
    private TypedQuery<Orden> typedQuery;
    
    @InjectMocks
    private OrdenDao ordenDao;
    
    private Orden orden;
    
    @BeforeAll
    public void setup(){
        orden = new Orden();
        
        orden.setId(1L);
        orden.setObservaciones("No tiene observaciones de Orden");
        Date fecha = Date.valueOf("2020-01-12");
        orden.setFechaOrden(fecha);
        orden.setEstado(EstadoOrden.ENVIADA);
    }
    
    //SHOWLIST
    @Test
    public void testListarComercio(){
        List<Orden> resultadoEsperado = Arrays.asList(orden);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Orden.class)).thenReturn(query);
        when(query.from(Orden.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Orden> resultado = ordenDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }
    
    //READ
    @Test
    public void testObtenerComercioPorId(){
        Long id = 1L;
        when(em.find(Orden.class, id)).thenReturn(orden);
        Orden ordenDevuelta = ordenDao.obtenerPorId(1L).get();
        assertEquals(orden, ordenDevuelta);
    }
    
    //CREATE
    @Test
    public void testInsertarPersona(){
        Orden ordenDevuelta = ordenDao.insertar(orden);
        verify(em, times(1)).persist(orden);
        assertEquals(orden, ordenDevuelta);
    }
    
    //UPDATE
    @Test
    public void testActualizarComercio(){
        when(em.merge(orden)).thenReturn(orden);
        Orden ordenDevuelta = ordenDao.actualizar(orden);
        verify(em, times(1)).merge(orden);
        assertEquals(orden, ordenDevuelta);
    }
    
    //DELETE
    @Test
    public void testEliminarComercio(){
        ordenDao.eliminar(orden);
        verify(em, times(1)).remove(em.contains(orden)? orden : em.merge(orden));
    }
    
}
