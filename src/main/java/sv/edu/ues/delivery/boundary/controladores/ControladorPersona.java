package sv.edu.ues.delivery.boundary.controladores;

import java.util.List;
import java.util.stream.Collectors;

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
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Response.Status;
import sv.edu.ues.delivery.control.service.ServicioPersona;
import sv.edu.ues.delivery.entity.Persona;

@Path("/cliente")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorPersona {

    @Context
    private UriInfo uriInfo;
    
    @Inject
    private ServicioPersona servicioPersona;

    @GET
    public Response listarClientes(){
        List<Persona> clientes = servicioPersona.listar();
        return Response.ok().entity(clientes).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerClientePorId(@PathParam("id") Long id){
        Persona cliente = servicioPersona.obtenerPorId(id).orElse(null);
        if(cliente == null){
            return Response.status(Status.NOT_FOUND)
                .header("mensaje", "El cliente con id " + id + " no existe en la base de datos.")
                .build();
        }
        return Response.ok().entity(cliente).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearCliente(Persona persona){
        try{
            Persona cliente = servicioPersona.insertar(persona);
            return Response.created(uriInfo.getAbsolutePath()).entity(cliente).build();
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response actualizarCliente(@PathParam("id") Long id, Persona persona){
        Persona clienteRecuperado = servicioPersona.obtenerPorId(id).orElse(null);
        if(clienteRecuperado == null){
            return Response.status(Status.NOT_FOUND)
                .header("mensaje", "El cliente con id " + id + " no existe en la base de datos.")
                .build();
        }
        clienteRecuperado.setNombre(persona.getNombre());
        clienteRecuperado.setApellido(persona.getApellido());
        clienteRecuperado.setDireccion(persona.getDireccion());
        clienteRecuperado.setFechaNacimiento(persona.getFechaNacimiento());

        try{
            Persona clienteActualizado = servicioPersona.actualizar(clienteRecuperado);
            return Response.ok(clienteActualizado).build();
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarClientePorId(@PathParam("id") Long id){
        Persona cliente = servicioPersona.obtenerPorId(id).orElse(null);
        if(cliente == null){
            return Response.status(Status.NOT_FOUND)
                .header("mensaje", "El cliente con id " + id + " no existe en la base de datos.")
                .build();
        }
        try{
            Persona clienteEliminado = servicioPersona.eliminar(cliente);
            return Response.ok(clienteEliminado).build();
            
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
}
