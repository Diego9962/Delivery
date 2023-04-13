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
import sv.edu.ues.delivery.entity.TipoProducto;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TipoProductoDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<TipoProducto> query;
    
    @Mock
    private Root<TipoProducto> root;

    @Mock
    private TypedQuery<TipoProducto> typedQuery;
    
    @InjectMocks
    private TipoProductoDao tipoProductoDao;
    
    private TipoProducto tipoProducto;

    @BeforeAll
    public void setup(){
        tipoProducto = new TipoProducto();
        
        tipoProducto.setActivo(true);
        tipoProducto.setComentarios("esto es un comentario");
        tipoProducto.setId(1L);
        tipoProducto.setNombre("Comida");
    }

    //SHOWLIST
    @Test
    public void testListarTipoProducto(){
        List<TipoProducto> resultadoEsperado = Arrays.asList(tipoProducto);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(TipoProducto.class)).thenReturn(query);
        when(query.from(TipoProducto.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<TipoProducto> resultado = tipoProductoDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }

        //READ
    @Test
    public void testObtenerTipoProductoPorId(){
        Long id = 1L;
        when(em.find(TipoProducto.class, id)).thenReturn(tipoProducto);
        TipoProducto tipoProductoDevuelto = tipoProductoDao.obtenerPorId(1L).get();
        assertEquals(tipoProducto, tipoProductoDevuelto);
    }

        //CREATE
    @Test
    public void testInsertarTipoProducto(){
        TipoProducto tipoProductoDevuelto = tipoProductoDao.insertar(tipoProducto);
        verify(em, times(1)).persist(tipoProducto);
        assertEquals(tipoProducto, tipoProductoDevuelto);
    }

     //UPDATE
     @Test
     public void testActualizarTipoProducto(){
         when(em.merge(tipoProducto)).thenReturn(tipoProducto);
         TipoProducto tipoProductoDevuelto = tipoProductoDao.actualizar(tipoProducto);
         verify(em, times(1)).merge(tipoProducto);
         assertEquals(tipoProducto, tipoProductoDevuelto);
     }

      //DELETE
    @Test
    public void testEliminarTipoProducto(){
        tipoProductoDao.eliminar(tipoProducto);
        verify(em, times(1)).remove(em.contains(tipoProducto)? tipoProducto : em.merge(tipoProducto));
    }

}
