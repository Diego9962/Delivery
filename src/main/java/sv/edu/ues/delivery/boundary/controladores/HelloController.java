package sv.edu.ues.delivery.boundary.controladores;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/hello")
public class HelloController {

    @GET
    public Response hello(){
        return Response.ok("Hola").build();
    }
    
}
