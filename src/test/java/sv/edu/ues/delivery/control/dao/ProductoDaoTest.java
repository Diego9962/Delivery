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
import sv.edu.ues.delivery.entity.Producto;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ProductoDaoTest {
    
    @Mock
    private EntityManager em;
    
    @Mock
    private CriteriaBuilder cb;
    
    @Mock
    private CriteriaQuery<Producto> query;
    
    @Mock
    private Root<Producto> root;

    @Mock
    private TypedQuery<Producto> typedQuery;
    
    @InjectMocks
    private ProductoDao productoDao;
    
    private Producto producto;
    
    @BeforeAll
    public void setup(){
        producto = new Producto();
        
        producto.setCodigo("110110");
        producto.setNombre("Pizza");
        producto.setDescripcion("Piza con peperoni ");
        producto.setActivo(true);
        producto.setPrecioCompra(22.5);
        producto.setPrecioVenta(30.0);
        producto.setCantidadExistente(50);
    }

    //SHOWLIST
    @Test
    public void testListarProducto(){
        List<Producto> resultadoEsperado = Arrays.asList(producto);
        
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Producto.class)).thenReturn(query);
        when(query.from(Producto.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Producto> resultado = productoDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }

    //READ
    @Test
    public void testObtenerProductoPorId(){
        String codigo = "110110";
        when(em.find(Producto.class, codigo)).thenReturn(producto);
        Producto productoDevuelto = productoDao.obtenerPorId(codigo).get();
        assertEquals(producto, productoDevuelto);
    }

    //CREATE
    @Test
    public void testInsertarProducto(){
        Producto productoDevuelto = productoDao.insertar(producto);
        verify(em, times(1)).persist(producto);
        assertEquals(producto, productoDevuelto);
    }

    //UPDATE
    @Test
    public void testActualizarProducto(){
        when(em.merge(producto)).thenReturn(producto);
        Producto comercioDevuelto = productoDao.actualizar(producto);
        verify(em, times(1)).merge(producto);
        assertEquals(producto, comercioDevuelto);
    }

    //DELETE
    @Test
    public void testEliminarComercio(){
        productoDao.eliminar(producto);
        verify(em, times(1)).remove(em.contains(producto)? producto : em.merge(producto));
    }

}
