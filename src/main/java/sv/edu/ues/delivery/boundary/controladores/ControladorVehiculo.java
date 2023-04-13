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
import java.util.List;
import java.util.stream.Collectors;
import sv.edu.ues.delivery.control.service.ServicioVehiculo;
import sv.edu.ues.delivery.entity.Vehiculo;

@Path("/vehiculo")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorVehiculo {
    
    @Context
    private UriInfo uriInfo;
    
    @Inject
    private ServicioVehiculo servicioVehiculo;
    
    @GET
    public Response listarTipoProductos(){
        List<Vehiculo> vehiculos = servicioVehiculo.listar();
        return Response.ok().entity(vehiculos).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerVehiculoPorId(@PathParam("id") Long id){
        Vehiculo vehiculo = servicioVehiculo.obtenerPorId(id).orElse(null);
        if(vehiculo == null){
            return Response.status(Status.NOT_FOUND)
                .header("mensaje", "El vehículo con id " + id + " no existe en la base de datos.")
                .build();
        }
        return Response.ok().entity(vehiculo).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearVehiculo(Vehiculo vehiculoIngresado){
        try{
            Vehiculo vehiculo = servicioVehiculo.insertar(vehiculoIngresado);
            return Response.created(uriInfo.getAbsolutePath()).entity(vehiculo).build();
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
    public Response actualizarVehiculo(@PathParam("id") Long id, Vehiculo vehiculo){
        Vehiculo vehiculoRecuperado = servicioVehiculo.obtenerPorId(id).orElse(null);
        if(vehiculoRecuperado == null){
            return Response.status(Status.NOT_FOUND)
                .header("mensaje", "El vehículo con id " + id + " no existe en la base de datos.")
                .build();
        }
        vehiculoRecuperado.setTipoVehiculo(vehiculo.getTipoVehiculo());
        vehiculoRecuperado.setPlaca(vehiculo.getPlaca());
        vehiculoRecuperado.setPropietario(vehiculo.getPropietario());
        vehiculoRecuperado.setActivo(vehiculo.isActivo());
        vehiculoRecuperado.setComentario(vehiculo.getComentario());
        
        try{
            Vehiculo vehiculoActualizado = servicioVehiculo.actualizar(vehiculoRecuperado);
            return Response.ok(vehiculoActualizado).build();
            
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminarVehiculoPorId(@PathParam("id") Long id){
        Vehiculo vehiculo = servicioVehiculo.obtenerPorId(id).orElse(null);
        if(vehiculo == null){
            return Response.status(Status.NOT_FOUND)
                .header("mensaje", "El vehículo con id " + id + " no existe en la base de datos.")
                .build();
        }
        try{
            Vehiculo vehiculoEliminado = servicioVehiculo.eliminar(vehiculo);
            return Response.ok(vehiculoEliminado).build();
            
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
}
