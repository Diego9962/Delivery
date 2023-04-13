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
import sv.edu.ues.delivery.control.service.ServicioTipoProducto;
import sv.edu.ues.delivery.entity.TipoProducto;

@Path("/tipo-producto")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorTipoProducto {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ServicioTipoProducto servicioTipoProducto;

    @GET
    public Response listarTipoProductos() {
        List<TipoProducto> tipoProductos = servicioTipoProducto.listar();
        return Response.ok().entity(tipoProductos).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerTipoProductoPorId(@PathParam("id") Long id) {
        TipoProducto tipoProducto = servicioTipoProducto.obtenerPorId(id).orElse(null);
        if (tipoProducto == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "El tipo producto con id " + id + " no existe en la base de datos.")
                    .build();
        }
        return Response.ok().entity(tipoProducto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearTipoProducto(TipoProducto tipoProductoIngresado) {
        try {
            TipoProducto tipoProducto = servicioTipoProducto.insertar(tipoProductoIngresado);
            return Response.created(uriInfo.getAbsolutePath()).entity(tipoProducto).build();
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
    public Response actualizarTipoProducto(@PathParam("id") Long id, TipoProducto tipoProducto){
        TipoProducto tipoProductoRecuperado = servicioTipoProducto.obtenerPorId(id).orElse(null);
        if(tipoProductoRecuperado == null){
            return Response.status(Status.NOT_FOUND)
                .header("mensaje", "El tipo producto con id " + id + " no existe en la base de datos.")
                .build();
        }
        tipoProductoRecuperado.setNombre(tipoProducto.getNombre());
        tipoProductoRecuperado.setActivo(tipoProducto.isActivo());
        tipoProductoRecuperado.setComentarios(tipoProducto.getComentarios());
        
        try{
            TipoProducto tipoProductoActualizado = servicioTipoProducto.actualizar(tipoProductoRecuperado);
            return Response.ok(tipoProductoActualizado).build();
            
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminarTipoProductoPorId(@PathParam("id") Long id) {
        TipoProducto tipoProducto = servicioTipoProducto.obtenerPorId(id).orElse(null);
        if (tipoProducto == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "El tipo producto con id " + id + " no existe en la base de datos.")
                    .build();
        }
        try {
            TipoProducto tipoProductoEliminado = servicioTipoProducto.eliminar(tipoProducto);
            return Response.ok(tipoProductoEliminado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
}
