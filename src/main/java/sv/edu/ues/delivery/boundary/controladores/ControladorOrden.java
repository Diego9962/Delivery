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
import sv.edu.ues.delivery.control.service.ServicioOrden;
import sv.edu.ues.delivery.entity.Orden;

@Path("/orden")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorOrden {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ServicioOrden servicioOrden;

    @GET
    public Response listarOrdenes() {
        List<Orden> ordenes = servicioOrden.listar();
        return Response.ok().entity(ordenes).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerOrdenPorId(@PathParam("id") Long id) {
        Orden orden = servicioOrden.obtenerPorId(id).orElse(null);
        if (orden == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La orden con id " + id + " no existe en la base de datos.")
                    .build();
        }
        return Response.ok().entity(orden).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearOrden(Orden ordenIngresada) {
        try {
            Orden orden = servicioOrden.insertar(ordenIngresada);
            return Response.created(uriInfo.getAbsolutePath()).entity(orden).build();
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
    public Response actualizarOrden(@PathParam("id") Long id, Orden orden) {
        Orden ordenRecuperado = servicioOrden.obtenerPorId(id).orElse(null);
        if (ordenRecuperado == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La orden con id " + id + " no existe en la base de datos.")
                    .build();
        }
        ordenRecuperado.setFechaOrden(orden.getFechaOrden());
        ordenRecuperado.setObservaciones(orden.getObservaciones());
        ordenRecuperado.setEstado(orden.getEstado());

        try {
            Orden ordenActualizado = servicioOrden.actualizar(ordenRecuperado);
            return Response.ok(ordenActualizado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarOrdenPorId(@PathParam("id") Long id) {
        Orden orden = servicioOrden.obtenerPorId(id).orElse(null);
        if (orden == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La orden con id " + id + " no existe en la base de datos.")
                    .build();
        }
        try {
            Orden ordenEliminado = servicioOrden.eliminar(orden);
            return Response.ok(ordenEliminado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
}
