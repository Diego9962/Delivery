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
import sv.edu.ues.delivery.entity.Factura;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FacturaDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<Factura> query;
    
    @Mock
    private Root<Factura> root;

    @Mock
    private TypedQuery<Factura> typedQuery;
    
    @InjectMocks
    private FacturaDao facturaDao;
    
    private Factura factura;
    
    @BeforeAll
    public void setup(){
        factura = new Factura();
        
        factura.setId(1L);
        factura.setObservaciones("No se registran observaciones a la fecha");
        factura.setAnulada(false);
        //SI DA ALGUN ERROR VERIFICAR ESTO DE LA FECHA
        Date fecha = Date.valueOf("2022-7-1");
        factura.setFechaEmision(fecha);
    }
    
    //SHOWLIST
    @Test
    public void testListarFactura(){
        List<Factura> resultadoEsperado = Arrays.asList(factura);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Factura.class)).thenReturn(query);
        when(query.from(Factura.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Factura> resultado = facturaDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }
    
    //READ
    @Test
    public void testObtenerFacturaPorId(){
        Long id = 1L;
        when(em.find(Factura.class, id)).thenReturn(factura);
        Factura facturaDevuelta = facturaDao.obtenerPorId(1L).get();
        assertEquals(factura, facturaDevuelta);
    }
    
    //CREATE
    @Test
    public void testInsertarFactura(){
        Factura facturaDevuelta = facturaDao.insertar(factura);
        verify(em, times(1)).persist(factura);
        assertEquals(factura, facturaDevuelta);
    }
    
    //UPDATE
    @Test
    public void testActualizarFactura(){
        when(em.merge(factura)).thenReturn(factura);
        Factura facturaDevuelta = facturaDao.actualizar(factura);
        verify(em, times(1)).merge(factura);
        assertEquals(factura, facturaDevuelta);
    }
    
    //DELETE
    @Test
    public void testEliminarFactura(){
        facturaDao.eliminar(factura);
        verify(em, times(1)).remove(em.contains(factura)? factura : em.merge(factura));
    }
    
}
