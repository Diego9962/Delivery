package sv.edu.ues.delivery.boundary.controladores;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.delivery.boundary.BaseIntegrationTest;
import sv.edu.ues.delivery.entity.Comercio;
import sv.edu.ues.delivery.entity.Direccion;
import sv.edu.ues.delivery.entity.Sucursal;
import sv.edu.ues.delivery.entity.Territorio;
import sv.edu.ues.delivery.entity.TipoComercio;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComercioIT extends BaseIntegrationTest{

    static String endpoint;
    
    static Client cliente;
    static WebTarget target;
    static Long idComercioCreado;
    static Integer idTipoCreado;

    @BeforeAll
    public static void lanzarPayaraTest(){
        endpoint = "http://" + payara.getHost() + ":" + payara.getFirstMappedPort() + "/delivery/";
        cliente = ClientBuilder.newClient();
        target = cliente.target(endpoint);
    }

    @Test
    @Order(1)
    public void crearTest(){
        System.out.println("Comercio - crear");
        assertTrue(payara.isRunning());
        int esperado = Response.Status.CREATED.getStatusCode(); //201
        Comercio creado = new Comercio();
        creado.setActivo(Boolean.TRUE);
        creado.setNombre("Restaurante El Buen Gusto");

        Response respuesta = target.path("comercio").request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(creado, MediaType.APPLICATION_JSON));
        assertEquals(esperado, respuesta.getStatus());
        assertTrue(respuesta.getHeaders().containsKey("location"));
        idComercioCreado = Long.valueOf(respuesta.getHeaderString("location").split("comercio/")[1]);
        assertNotNull(idComercioCreado);
        // //validar excepciones
        respuesta = target.path("comercio").request(MediaType.APPLICATION_JSON)
            .post(Entity.json(null));
        assertEquals(400, respuesta.getStatus());
    }

    @Test
    @Order(2)
    public void findByIdTest(){
        System.out.println("Comercio - findById");
        assertTrue(payara.isRunning());
        assertNotNull(idComercioCreado);
        int esperado = 200;
        Response respuesta = target.path("comercio/{id}").resolveTemplate("id", idComercioCreado)
            .request(MediaType.APPLICATION_JSON).get();
        
        assertEquals(esperado, respuesta.getStatus());
        Comercio encontrado = respuesta.readEntity(Comercio.class);
        assertEquals(idComercioCreado, encontrado.getId());
        //excepciones
        respuesta = target.path("comercio/{id}").resolveTemplate("id", 999)
            .request(MediaType.APPLICATION_JSON).get();
        assertEquals(404, respuesta.getStatus());
        assertTrue(respuesta.getHeaders().containsKey(RestResourcePattern.ID_NOT_FOUND));
    }

    @Test
    @Order(3)
    public void crearTipoComercioTest(){
        System.out.println("Comercio - crearTipoComercio");
        assertTrue(payara.isRunning());
        int esperado = Response.Status.CREATED.getStatusCode();
        TipoComercio creado = new TipoComercio();
        creado.setActivo(Boolean.TRUE);
        creado.setNombre("Restaurante ");
        Response respuesta = target.path("tipocomercio").request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(creado, MediaType.APPLICATION_JSON));
        assertEquals(esperado, respuesta.getStatus());
        assertTrue(respuesta.getHeaders().containsKey("location"));
        idTipoCreado = Integer.valueOf(respuesta.getHeaderString("location").split("tipocomercio/")[1]);
        assertNotNull(idTipoCreado);
        respuesta = target.path("tipocomercio").request(MediaType.APPLICATION_JSON)
            .post(Entity.json(null));
        assertEquals(400, respuesta.getStatus());
    }

    @Test
    @Order(4)
    public void validarTipoVacio(){
        System.out.println("Comercio - validarTipoVacio");
        assertTrue(payara.isRunning());
        int esperado = 200;
        Response respuesta = target.path("comercio/{id}/tipocomercio").resolveTemplate("id", idComercioCreado)
            .request(MediaType.APPLICATION_JSON).get();
        assertEquals(esperado, respuesta.getStatus());
        assertTrue(respuesta.getHeaders().containsKey(RestResourcePattern.CONTAR_REGISTROS));
        assertEquals(0, Integer.valueOf(respuesta.getHeaderString(RestResourcePattern.CONTAR_REGISTROS)));
        //excepciones
        respuesta = target.path("comercio/{id}/tipocomercio").resolveTemplate("id", 999)
            .request(MediaType.APPLICATION_JSON).get();
        assertEquals(404, respuesta.getStatus());
        assertTrue(respuesta.getHeaders().containsKey(RestResourcePattern.ID_NOT_FOUND));
    }

    @Test
    @Order(5)
    public void agregarTipoComercio(){
        System.out.println("Comercio - agregarTipoComercio");
        assertTrue(payara.isRunning());
        int esperado = Response.Status.CREATED.getStatusCode();
        Response respuesta = target.path("comercio/{id}/tipocomercio/{idTipoComercio}")
            .resolveTemplate("id", idComercioCreado)
            .resolveTemplate("idTipoComercio", idTipoCreado)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity("", MediaType.APPLICATION_JSON));

        if(respuesta.getStatus() == 400){
            System.out.println(respuesta.getHeaderString(RestResourcePattern.WRONG_PARAMETER));
        }
        assertEquals(esperado, respuesta.getStatus());
        //excepciones
        respuesta = target.path("comercio/{id}/tipocomercio/{idTipoComercio}")
            .resolveTemplate("id", 999)
            .resolveTemplate("idTipoComercio", 999)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity("", MediaType.APPLICATION_JSON));
        
        assertEquals(400, respuesta.getStatus());  
    }

    @Test
    @Order(6)
    public void validarTipoLlenoTest(){
        System.out.println("Comercio - validarTipoLleno");
        assertTrue(payara.isRunning());
        int esperado = 200;
        Response respuesta = target.path("comercio/{id}/tipocomercio")
            .resolveTemplate("id", idComercioCreado)
            .request(MediaType.APPLICATION_JSON)
            .get();
        assertEquals(esperado, respuesta.getStatus());
        assertTrue(respuesta.getHeaders().containsKey(RestResourcePattern.CONTAR_REGISTROS));
        assertEquals(1, Integer.valueOf(respuesta.getHeaderString(RestResourcePattern.CONTAR_REGISTROS)));
    }

    @Test
    @Order(7)
    public void crearSucursalTest(){
        System.out.println("Comercio - crearSucursal");
        assertTrue(payara.isRunning());
        //crear territorio
        Territorio sv = new Territorio();
        sv.setHijosObligatorios(14);
        sv.setIdTerritorioPadre(null);
        sv.setNombre("El Salvador");
        sv.setTextoVisible("pais");
        Response respuestaSv = target.path("territorio")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(sv, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaSv.getStatus());
        assertTrue(respuestaSv.getHeaders().containsKey("location"));
        sv.setIdTerritorio(Long.valueOf(respuestaSv.getHeaderString("location").split("territorio/")[1]));
        assertNotNull(sv.getIdTerritorio());
        Territorio santaAna = new Territorio();
        santaAna.setIdTerritorioPadre(sv);
        santaAna.setNombre("Santa Ana");
        santaAna.setTextoVisible("departamento");
        santaAna.setHijosObligatorios(13);
        Response respuestaSantaAna = target.path("territorio")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(santaAna, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaSantaAna.getStatus());
        assertTrue(respuestaSantaAna.getHeaders().containsKey("location"));
        santaAna.setIdTerritorio(Long.valueOf(respuestaSantaAna.getHeaderString("location").split("territorio/")[1]));
        assertNotNull(santaAna.getIdTerritorio());
        //excepciones territorio
        respuestaSv = target.path("territorio")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(null));
        assertEquals(400, respuestaSv.getStatus());
        //crear direccion
        Direccion direccion = new Direccion();
        direccion.setIdTerritorio(santaAna);
        direccion.setDireccion("Final 1a 2 CL OTE RES LOMAS TECANA. No 18");
        direccion.setLatitud(BigDecimal.TEN);
        direccion.setLongitud(BigDecimal.ONE);
        direccion.setReferencias("UNA CUADRA ABAJO DEL SIHUA");
        Response respuestaDireccion = target.path("direccion")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(direccion, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaDireccion.getStatus());
        assertTrue(respuestaDireccion.getHeaders().containsKey("location"));
        direccion.setId(Long.valueOf(respuestaDireccion.getHeaderString("location").split("direccion/")[1]));
        assertNotNull(direccion.getId());
        //excepciones direccion
        respuestaDireccion = target.path("direccion")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(null));
        assertEquals(400, respuestaDireccion.getStatus());
        //asociar direccion a sucursal
        Sucursal s = new Sucursal();
        s.setDireccion(direccion);
        s.setNombre("LA ROTONDA");
        Response respuestaSucursal = target.path("comercio/{id}/sucursal")
            .resolveTemplate("id", idComercioCreado)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(s, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaSucursal.getStatus());
        assertTrue(respuestaSucursal.getHeaders().containsKey("location"));
        //excepciones
        respuestaSucursal = target.path("comercio/{id}/sucursal")
            .resolveTemplate("id", 999)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(s, MediaType.APPLICATION_JSON));
        assertEquals(400, respuestaSucursal.getStatus());
    }
}
