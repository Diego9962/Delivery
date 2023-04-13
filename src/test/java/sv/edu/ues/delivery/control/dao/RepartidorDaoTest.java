
package sv.edu.ues.delivery.control.dao;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.sql.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.delivery.entity.Repartidor;
import sv.edu.ues.delivery.entity.TipoLicencia;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RepartidorDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<Repartidor> query;
    
    @Mock
    private Root<Repartidor> root;

    @Mock
    private TypedQuery<Repartidor> typedQuery;
    
    @InjectMocks
    private RepartidorDao repartidorDao;
    
    private Repartidor repartidor;

    @BeforeAll
    public void setup(){
        repartidor = new Repartidor();
        
        repartidor.setId(1L);
        repartidor.setNombre("Jose");
        repartidor.setApellido("Perez");
        repartidor.setSalario(365.35);
        Date fecha = Date.valueOf("2020-12-12");
        repartidor.setFechaNacimiento(fecha);
        repartidor.setObservacion("No hay observaciones");
        repartidor.setActivo(false);
        repartidor.setTipoLicencia(TipoLicencia.CLASE_B);
    }
    
    //SHOWLIST
    @Test
    public void testListarRepartidor(){
        List<Repartidor> resultadoEsperado = Arrays.asList(repartidor);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Repartidor.class)).thenReturn(query);
        when(query.from(Repartidor.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Repartidor> resultado = repartidorDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }

    //READ
    @Test
    public void testObtenerRepartidorPorId(){
        Long id = 1L;
        when(em.find(Repartidor.class, id)).thenReturn(repartidor);
        Repartidor repartidorDevuelto = repartidorDao.obtenerPorId(1L).get();
        assertEquals(repartidor, repartidorDevuelto);
    }
    
    //CREATE
    @Test
    public void testInsertarRepartidor(){
        Repartidor repartidorDevuelto = repartidorDao.insertar(repartidor);
        verify(em, times(1)).persist(repartidor);
        assertEquals(repartidor, repartidorDevuelto);
    }

    //UPDATE
    @Test
    public void testActualizarRepartidor(){
        when(em.merge(repartidor)).thenReturn(repartidor);
        Repartidor repartidorDevuelto = repartidorDao.actualizar(repartidor);
        verify(em, times(1)).merge(repartidor);
        assertEquals(repartidor, repartidorDevuelto);
    }

    //DELETE
    @Test
    public void testEliminarRepartidor(){
        repartidorDao.eliminar(repartidor);
        verify(em, times(1)).remove(em.contains(repartidor)? repartidor : em.merge(repartidor));
    }

}
