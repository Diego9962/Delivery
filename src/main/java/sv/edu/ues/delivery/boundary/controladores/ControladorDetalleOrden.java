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
import sv.edu.ues.delivery.control.service.ServicioDetalleOrden;
import sv.edu.ues.delivery.entity.DetalleOrden;

@Path("/detalle-orden")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorDetalleOrden {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ServicioDetalleOrden servicioDetalleOrden;

    @GET
    public Response listarDetalleOrdenes() {
        List<DetalleOrden> detalleOrdenes = servicioDetalleOrden.listar();
        return Response.ok().entity(detalleOrdenes).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerDetalleOrdenPorId(@PathParam("id") Long id) {
        DetalleOrden detalleOrden = servicioDetalleOrden.obtenerPorId(id).orElse(null);
        if (detalleOrden == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "El detalle orden con id " + id + " no existe en la base de datos")
                    .build();
        }
        return Response.ok().entity(detalleOrden).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearDetalleOrden(DetalleOrden detalleOrdenIngresado) {
        try {
            DetalleOrden detalleOrden = servicioDetalleOrden.insertar(detalleOrdenIngresado);
            return Response.created(uriInfo.getAbsolutePath()).entity(detalleOrden).build();
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
    public Response actualizarDetalleOrden(@PathParam("id") Long id, DetalleOrden detalleOrden) {
        DetalleOrden detalleOrdenRecuperado = servicioDetalleOrden.obtenerPorId(id).orElse(null);
        if (detalleOrdenRecuperado == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "El detalle orden con id " + id + " no existe en la base de datos.")
                    .build();
        }
        detalleOrdenRecuperado.setIdOrden(detalleOrden.getIdOrden());
        detalleOrdenRecuperado.setCodigoProducto(detalleOrden.getCodigoProducto());
        detalleOrdenRecuperado.setCantidad(detalleOrden.getCantidad());

        try {
            DetalleOrden detalleOrdenActualizado = servicioDetalleOrden.actualizar(detalleOrdenRecuperado);
            return Response.ok(detalleOrdenActualizado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarDetalleOrden(@PathParam("id") Long id) {
        DetalleOrden detalleOrden = servicioDetalleOrden.obtenerPorId(id).orElse(null);
        if (detalleOrden == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "El detalle orden con id " + id + " no existe en la base de datos.")
                    .build();
        }
        try {
            DetalleOrden detalleOrdenEliminado = servicioDetalleOrden.eliminar(detalleOrden);
            return Response.ok(detalleOrdenEliminado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
}
