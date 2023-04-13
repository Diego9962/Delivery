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
import sv.edu.ues.delivery.entity.Territorio;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TerritorioDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<Territorio> query;
    
    @Mock
    private Root<Territorio> root;

    @Mock
    private TypedQuery<Territorio> typedQuery;
    
    @InjectMocks
    private TerritorioDao territorioDao;
    
    private Territorio territorio;
    
    @BeforeAll
    public void setup(){
        territorio = new Territorio();
        
        territorio.setIdTerritorio(1L);
        territorio.setNombre("algun nombre");
        territorio.setTextoVisible("algun texto visible");
        territorio.setHijosObligatorios(5);
    }
    
    //SHOWLIST
    @Test
    public void testListarTerritorio(){
        List<Territorio> resultadoEsperado = Arrays.asList(territorio);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Territorio.class)).thenReturn(query);
        when(query.from(Territorio.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Territorio> resultado = territorioDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }
    
    //READ
    @Test
    public void testObtenerTerritorioPorId(){
        Long id = 1L;
        when(em.find(Territorio.class, id)).thenReturn(territorio);
        Territorio territorioDevuelto = territorioDao.obtenerPorId(1L).get();
        assertEquals(territorio, territorioDevuelto);
    }
    
    //CREATE
    @Test
    public void testInsertarTerritorio(){
        Territorio territorioDevuelto = territorioDao.insertar(territorio);
        verify(em, times(1)).persist(territorio);
        assertEquals(territorio, territorioDevuelto);
    }
    
    //UPDATE
    @Test
    public void testActualizarTerritorio(){
        when(em.merge(territorio)).thenReturn(territorio);
        Territorio territorioDevuelto = territorioDao.actualizar(territorio);
        verify(em, times(1)).merge(territorio);
        assertEquals(territorio, territorioDevuelto);
    }
    
    //DELETE
    @Test
    public void testEliminarTerritorio(){
        territorioDao.eliminar(territorio);
        verify(em, times(1)).remove(em.contains(territorio)? territorio : em.merge(territorio));
    }
    
}
