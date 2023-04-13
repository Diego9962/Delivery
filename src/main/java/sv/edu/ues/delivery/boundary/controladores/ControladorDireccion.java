package sv.edu.ues.delivery.boundary.controladores;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import sv.edu.ues.delivery.control.service.ServicioDireccion;
import sv.edu.ues.delivery.entity.Direccion;

@Path("/direccion")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorDireccion {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ServicioDireccion servicioDireccion;

    @GET
    public Response listarDirecciones() {
        List<Direccion> direcciones = servicioDireccion.listar();
        return Response.ok().entity(direcciones).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerDireccionPorId(@PathParam("id") Long id) {
        Direccion direccion = servicioDireccion.obtenerPorId(id).orElse(null);
        if (direccion == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La dirección con id " + id + " no existe en la base de datos.")
                    .build();
        }
        return Response.ok().entity(direccion).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearDireccion(Direccion direccionIngresada) throws URISyntaxException {
        try {
            Direccion direccion = servicioDireccion.insertar(direccionIngresada);
            return Response.created(new URI(uriInfo.getAbsolutePath() + "/" + direccion.getId())).entity(direccion).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response actualizarDireccion(@PathParam("id") Long id, Direccion direccion){
        Direccion direccionRecuperado = servicioDireccion.obtenerPorId(id).orElse(null);
        if(direccionRecuperado == null){
            return Response.status(Status.NOT_FOUND)
                .header("mensaje", "La dirección con id " + id + " no existe en la base de datos.")
                .build();
        }
        direccionRecuperado.setDireccion(direccion.getDireccion());
        direccionRecuperado.setLatitud(direccion.getLatitud());
        direccionRecuperado.setLongitud(direccion.getLongitud());
        
        try{
            Direccion direccionActualizado = servicioDireccion.actualizar(direccionRecuperado);
            return Response.ok(direccionActualizado).build();
        
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminarDireccionPorId(@PathParam("id") Long id) {
        Direccion direccion = servicioDireccion.obtenerPorId(id).orElse(null);
        if (direccion == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La dirección con id " + id + " no existe en la base de datos")
                    .build();
        }
        try {
            Direccion direccionElimando = servicioDireccion.eliminar(direccion);
            return Response.ok(direccionElimando).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
}
