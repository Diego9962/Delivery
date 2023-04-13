package sv.edu.ues.delivery.control.dao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.delivery.entity.Persona;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PersonaDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private CriteriaBuilder cb;

    @Mock
    private CriteriaQuery<Persona> query;

    @Mock
    private Root<Persona> root;

    @Mock
    private TypedQuery<Persona> typedQuery;

    @InjectMocks
    private PersonaDao personaDao;

    private Persona persona;

    @BeforeAll
    public void setUp(){
        persona = new Persona();
        persona.setId(1L);
        persona.setNombre("jairo");
        persona.setApellido("flores");
        persona.setDireccion("alguna direccion");
        persona.setFechaNacimiento(new Date());
    }

    @Test
    public void testListarPersonas(){
        List<Persona> resultadoEsperado = Arrays.asList(persona);
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Persona.class)).thenReturn(query);
        when(query.from(Persona.class)).thenReturn(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(resultadoEsperado);

        List<Persona> resultado = personaDao.listar();
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObtenerPersonaPorId(){
        Long id = 1L;
        when(em.find(Persona.class, id)).thenReturn(persona);
        Persona personaDevuelta = personaDao.obtenerPorId(1L).get();
        assertEquals(persona, personaDevuelta);
    }

    @Test
    public void testInsertarPersona(){
        Persona personaDevuelta = personaDao.insertar(persona);
        verify(em, times(1)).persist(persona);
        assertEquals(persona, personaDevuelta);
    }

    @Test
    public void testActualizarPersona(){
        when(em.merge(persona)).thenReturn(persona);
        Persona personaDevuelta = personaDao.actualizar(persona);
        verify(em, times(1)).merge(persona);
        assertEquals(persona, personaDevuelta);
    }

    @Test
    public void testEliminarPersona(){
        personaDao.eliminar(persona);
        verify(em, times(1)).remove(em.contains(persona)? persona : em.merge(persona));
    }
}
