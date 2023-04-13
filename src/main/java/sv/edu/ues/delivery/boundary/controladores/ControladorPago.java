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
import sv.edu.ues.delivery.control.service.ServicioPago;
import sv.edu.ues.delivery.entity.Pago;

@Path("/pago")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorPago {
    
    @Context
    private UriInfo uriInfo;
    
    @Inject
    private ServicioPago servicioPago;
    
    @GET
    public Response listarPagos(){
        List<Pago> pagos = servicioPago.listar();
        return Response.ok().entity(pagos).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPagoPorId(@PathParam("id") Long id){
        Pago pago = servicioPago.obtenerPorId(id).orElse(null);
        if (pago == null){
            return Response.status(Status.NOT_FOUND)
                .header("mensaje", "El pago con id " + id + " no existe en la base de datos.")
                .build();
        }
        return Response.ok().entity(pago).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearPago(Pago pagoIngresado){
        try{
            Pago pago = servicioPago.insertar(pagoIngresado);
            return Response.created(uriInfo.getAbsolutePath()).entity(pago).build();
            
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
    public Response actualizarPago(@PathParam("id") Long id, Pago pago){
        Pago pagoRecuperado = servicioPago.obtenerPorId(id).orElse(null);
        if(pagoRecuperado == null){
            return Response.status(Status.NOT_FOUND)
                .header("mensaje", "El pago con id " + id + " no existe en la base de datos.")
                .build();
        }
        
        pagoRecuperado.setTipoPago(pago.getTipoPago());
        pagoRecuperado.setMonto(pago.getMonto());
        pagoRecuperado.setReferencia(pago.getReferencia());
        pagoRecuperado.setEstado(pago.getEstado());
        
        try{
            Pago pagoActualizado = servicioPago.actualizar(pagoRecuperado);
            return Response.ok(pagoActualizado).build();
            
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminarPagoPorId(@PathParam("id") Long id){
        Pago pago = servicioPago.obtenerPorId(id).orElse(null);
        if(pago == null){
            return Response.status(Status.NOT_FOUND)
                .header("mensaje", "El pago con id " + id + " no existe en la base de datos.")
                .build();
        }
        try{
            Pago pagoEliminado = servicioPago.eliminar(pago);
            return Response.ok(pagoEliminado).build();
            
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
    
}
