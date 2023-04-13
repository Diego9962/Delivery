package sv.edu.ues.delivery.control.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.delivery.entity.Sucursal;

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

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SucursalDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<Sucursal> query;
    
    @Mock
    private Root<Sucursal> root;

    @Mock
    private TypedQuery<Sucursal> typedQuery;
    
    @InjectMocks
    private SucursalDao sucursalDao;
    
    private Sucursal sucursal;

    @BeforeAll
    public void setup(){
        sucursal = new Sucursal();
        
        sucursal.setId(1l);
        sucursal.setTelefono("1234-1234");
    }

    //SHOWLIST
    @Test
    public void testListarSucursal(){
        List<Sucursal> resultadoEsperado = Arrays.asList(sucursal);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Sucursal.class)).thenReturn(query);
        when(query.from(Sucursal.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Sucursal> resultado = sucursalDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }

    //READ
    @Test
    public void testObtenerSucursalPorId(){
        Long id = 1L;
        when(em.find(Sucursal.class, id)).thenReturn(sucursal);
        Sucursal sucursalDevuelta = sucursalDao.obtenerPorId(1L).get();
        assertEquals(sucursal, sucursalDevuelta);
    }

    //CREATE
    @Test
    public void testInsertarSucursal(){
        Sucursal sucursalDevuelta = sucursalDao.insertar(sucursal);
        verify(em, times(1)).persist(sucursal);
        assertEquals(sucursal, sucursalDevuelta);
    }

    //UPDATE
    @Test
    public void testActualizarSucursal(){
        when(em.merge(sucursal)).thenReturn(sucursal);
        Sucursal sucursalDevuelto = sucursalDao.actualizar(sucursal);
        verify(em, times(1)).merge(sucursal);
        assertEquals(sucursal, sucursalDevuelto);
    }

    //DELETE
    @Test
    public void testEliminarSucursal(){
        sucursalDao.eliminar(sucursal);
        verify(em, times(1)).remove(em.contains(sucursal)? sucursal : em.merge(sucursal));
    }

}
