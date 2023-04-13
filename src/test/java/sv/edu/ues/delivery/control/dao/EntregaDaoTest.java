package sv.edu.ues.delivery.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.delivery.entity.Entrega;
import sv.edu.ues.delivery.entity.EstadoEntrega;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EntregaDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<Entrega> query;
    
    @Mock
    private Root<Entrega> root;

    @Mock
    private TypedQuery<Entrega> typedQuery;
    
    @InjectMocks
    private EntregaDao entregaDao;
    
    private Entrega entrega;

    @BeforeAll
    public void setup(){
        entrega = new Entrega();
        
        entrega.setId(1L);
        entrega.setObservaciones("No hay observaciones");
        Timestamp fechaYHora = Timestamp.valueOf(LocalDateTime.now());
        entrega.setFechaCreacion(fechaYHora);
        entrega.setEstadoEntrega(EstadoEntrega.ENTREGADO);
        entrega.setFechaAlcanzado(fechaYHora);
    }

    //SHOWLIST
    @Test
    public void testListarEntrega(){
        List<Entrega> resultadoEsperado = Arrays.asList(entrega);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Entrega.class)).thenReturn(query);
        when(query.from(Entrega.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Entrega> resultado = entregaDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }

    //READ
    @Test
    public void testObtenerEntregaPorId(){
        Long id = 1L;
        when(em.find(Entrega.class, id)).thenReturn(entrega);
        Entrega entregaDevuelto = entregaDao.obtenerPorId(1L).get();
        assertEquals(entrega, entregaDevuelto);
    }

    //CREATE
    @Test
    public void testInsertarEntrega(){
        Entrega entregaDevuelto = entregaDao.insertar(entrega);
        verify(em, times(1)).persist(entrega);
        assertEquals(entrega, entregaDevuelto);
    }

    //UPDATE
    @Test
    public void testActualizarEntrega(){
        when(em.merge(entrega)).thenReturn(entrega);
        Entrega entregaDevuelto = entregaDao.actualizar(entrega);
        verify(em, times(1)).merge(entrega);
        assertEquals(entrega, entregaDevuelto);
    }

    //DELETE
    @Test
    public void testEliminarEntrega(){
        entregaDao.eliminar(entrega);
        verify(em, times(1)).remove(em.contains(entrega)? entrega : em.merge(entrega));
    }

}





































