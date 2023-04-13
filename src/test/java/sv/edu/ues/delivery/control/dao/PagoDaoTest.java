package sv.edu.ues.delivery.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
import sv.edu.ues.delivery.entity.Pago;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PagoDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<Pago> query;
    
    @Mock
    private Root<Pago> root;

    @Mock
    private TypedQuery<Pago> typedQuery;
    
    @InjectMocks
    private PagoDao pagoDao;
    
    private Pago pago;
    
    @BeforeAll
    public void setup(){
        pago = new Pago();
        
        pago.setId(1L);
        pago.setMonto(23.4f);
        pago.setReferencia("En dolares");
        pago.setTipoPago("En efectivo");
        pago.setEstado("cancelado");
    }
    
    //SHOWLIST
    @Test
    public void testListarPago(){
        List<Pago> resultadoEsperado = Arrays.asList(pago);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Pago.class)).thenReturn(query);
        when(query.from(Pago.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Pago> resultado = pagoDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }

    //READ
    @Test
    public void testObtenerPagoPorId(){
        Long id = 1L;
        when(em.find(Pago.class, id)).thenReturn(pago);
        Pago pagoDevuelto = pagoDao.obtenerPorId(1L).get();
        assertEquals(pago, pagoDevuelto);
    }

    //CREATE
    @Test
    public void testInsertarPago(){
        Pago pagoDevuelto = pagoDao.insertar(pago);
        verify(em, times(1)).persist(pago);
        assertEquals(pago, pagoDevuelto);
    }

    //UPDATE
    @Test
    public void testActualizarPago(){
        when(em.merge(pago)).thenReturn(pago);
        Pago pagoDevuelto = pagoDao.actualizar(pago);
        verify(em, times(1)).merge(pago);
        assertEquals(pago, pagoDevuelto);
    }

    //DELETE
    @Test
    public void testEliminarPago(){
        pagoDao.eliminar(pago);
        verify(em, times(1)).remove(em.contains(pago)? pago : em.merge(pago));
    }

}
