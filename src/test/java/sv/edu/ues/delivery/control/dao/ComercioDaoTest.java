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
import sv.edu.ues.delivery.entity.Comercio;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ComercioDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<Comercio> query;
    
    @Mock
    private Root<Comercio> root;

    @Mock
    private TypedQuery<Comercio> typedQuery;
    
    @InjectMocks
    private ComercioDao comercioDao;
    
    private Comercio comercio;
    
    @BeforeAll
    public void setup(){
        comercio = new Comercio();
        
        comercio.setId(1L);
        comercio.setNombre("Pizza Hut");
        comercio.setLogo("https://cdn.pixabay.com/photo/2012/05/29/00/43/car-49278_640.jpg");
        comercio.setDescripcion("venta de comida rapida pizza hut");
        comercio.setActivo(true);
    }
    
    //SHOWLIST
    @Test
    public void testListarComercio(){
        List<Comercio> resultadoEsperado = Arrays.asList(comercio);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Comercio.class)).thenReturn(query);
        when(query.from(Comercio.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Comercio> resultado = comercioDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }
    
    //READ
    @Test
    public void testObtenerComercioPorId(){
        Long id = 1L;
        when(em.find(Comercio.class, id)).thenReturn(comercio);
        Comercio comercioDevuelto = comercioDao.obtenerPorId(1L).get();
        assertEquals(comercio, comercioDevuelto);
    }
    
    //CREATE
    @Test
    public void testInsertarComercio(){
        Comercio comercioDevuelto = comercioDao.insertar(comercio);
        verify(em, times(1)).persist(comercio);
        assertEquals(comercio, comercioDevuelto);
    }
    
    //UPDATE
    @Test
    public void testActualizarComercio(){
        when(em.merge(comercio)).thenReturn(comercio);
        Comercio comercioDevuelto = comercioDao.actualizar(comercio);
        verify(em, times(1)).merge(comercio);
        assertEquals(comercio, comercioDevuelto);
    }
    
    //DELETE
    @Test
    public void testEliminarComercio(){
        comercioDao.eliminar(comercio);
        verify(em, times(1)).remove(em.contains(comercio)? comercio : em.merge(comercio));
    }
    
}
