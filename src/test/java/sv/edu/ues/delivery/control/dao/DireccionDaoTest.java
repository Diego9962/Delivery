package sv.edu.ues.delivery.control.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.delivery.entity.Direccion;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class DireccionDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<Direccion> query;
    
    @Mock
    private Root<Direccion> root;

    @Mock
    private TypedQuery<Direccion> typedQuery;
    
    @InjectMocks
    private DireccionDao direccionDao;
    
    private Direccion direccion;
    
    @BeforeAll
    public void setup(){
        direccion = new Direccion();
        
        direccion.setId(1L);
        direccion.setDireccion("Local 25 Metrocentro Santa Ana");
        direccion.setLatitud(BigDecimal.TEN);
        direccion.setLongitud(BigDecimal.ONE);

    }
    
    //SHOWLIST
    @Test
    public void testListarComercio(){
        List<Direccion> resultadoEsperado = Arrays.asList(direccion);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Direccion.class)).thenReturn(query);
        when(query.from(Direccion.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Direccion> resultado = direccionDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }
    
    //READ
    @Test
    public void testObtenerComercioPorId(){
        Long id = 1L;
        when(em.find(Direccion.class, id)).thenReturn(direccion);
        Direccion direccionDevuelta = direccionDao.obtenerPorId(1L).get();
        assertEquals(direccion, direccionDevuelta);
    }
    
    //CREATE
    @Test
    public void testInsertarPersona(){
        Direccion direccionDevuelta = direccionDao.insertar(direccion);
        verify(em, times(1)).persist(direccion);
        assertEquals(direccion, direccionDevuelta);
    }
    
    //UPDATE
    @Test
    public void testActualizarComercio(){
        when(em.merge(direccion)).thenReturn(direccion);
        Direccion direccionDevuelta = direccionDao.actualizar(direccion);
        verify(em, times(1)).merge(direccion);
        assertEquals(direccion, direccionDevuelta);
    }
    
    //DELETE
    @Test
    public void testEliminarComercio(){
        direccionDao.eliminar(direccion);
        verify(em, times(1)).remove(em.contains(direccion)? direccion : em.merge(direccion));
    }
    
}
