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
import sv.edu.ues.delivery.entity.TipoComercio;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TipoComercioDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<TipoComercio> query;
    
    @Mock
    private Root<TipoComercio> root;

    @Mock
    private TypedQuery<TipoComercio> typedQuery;
    
    @InjectMocks
    private TipoComercioDao tipoComercioDao;
    
    private TipoComercio tipoComercio;

    @BeforeAll
    public void setup(){
        tipoComercio = new TipoComercio();
        
       tipoComercio.setActivo(true);
       tipoComercio.setId(1L);
       tipoComercio.setNombre("Electronico");

    }

    //SHOWLIST
    @Test
    public void testListarTipoComercio(){
        List<TipoComercio> resultadoEsperado = Arrays.asList(tipoComercio);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(TipoComercio.class)).thenReturn(query);
        when(query.from(TipoComercio.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<TipoComercio> resultado = tipoComercioDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }

     //READ
     @Test
     public void testObtenerTipoComercioPorId(){
         Long id = 1L;
         when(em.find(TipoComercio.class, id)).thenReturn(tipoComercio);
         TipoComercio tipoComercioDevuelto = tipoComercioDao.obtenerPorId(1L).get();
         assertEquals(tipoComercio, tipoComercioDevuelto);
     }

     //CREATE
    @Test
    public void testInsertarTipoComercio(){
        TipoComercio tipoComercioDevuelto = tipoComercioDao.insertar(tipoComercio);
        verify(em, times(1)).persist(tipoComercio);
        assertEquals(tipoComercio, tipoComercioDevuelto);
    }

    //UPDATE
    @Test
    public void testActualizarTipoComercio(){
        when(em.merge(tipoComercio)).thenReturn(tipoComercio);
        TipoComercio tipoComercioDevuelto = tipoComercioDao.actualizar(tipoComercio);
        verify(em, times(1)).merge(tipoComercio);
        assertEquals(tipoComercio, tipoComercioDevuelto);
    }

     //DELETE
     @Test
     public void testEliminarTipoComercio(){
         tipoComercioDao.eliminar(tipoComercio);
         verify(em, times(1)).remove(em.contains(tipoComercio)? tipoComercio : em.merge(tipoComercio));
     }

}
