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
import sv.edu.ues.delivery.control.service.ServicioEntrega;
import sv.edu.ues.delivery.entity.Entrega;

@Path("/entrega")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorEntrega {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ServicioEntrega servicioEntrega;

    @GET
    public Response listarEntregas() {
        List<Entrega> entregas = servicioEntrega.listar();
        return Response.ok().entity(entregas).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerEntregaPorId(@PathParam("id") Long id) {
        Entrega entrega = servicioEntrega.obtenerPorId(id).orElse(null);
        if (entrega == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La entrega con id " + id + " no existe en la base de datos.")
                    .build();
        }
        return Response.ok().entity(entrega).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearEntrega(Entrega entregaIngresada) {
        try {
            Entrega entrega = servicioEntrega.insertar(entregaIngresada);
            return Response.created(uriInfo.getAbsolutePath()).entity(entrega).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Response.Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response actualizarEntrega(@PathParam("id") Long id, Entrega entrega) {
        Entrega entregaRecuperado = servicioEntrega.obtenerPorId(id).orElse(null);
        if (entregaRecuperado == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La entrega con id " + id + " no existe en la base de datos.")
                    .build();
        }

        entregaRecuperado.setFechaCreacion(entrega.getFechaCreacion());
        entregaRecuperado.setEstadoEntrega(entrega.getEstadoEntrega());
        entregaRecuperado.setFechaAlcanzado(entrega.getFechaAlcanzado());
        entregaRecuperado.setObservaciones(entrega.getObservaciones());

        try {
            Entrega entregaActualizado = servicioEntrega.actualizar(entregaRecuperado);
            return Response.ok(entregaActualizado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }

    }

    @DELETE
    @Path("/{id}")
    public Response eliminarEntregaPorId(@PathParam("id") Long id) {
        Entrega entrega = servicioEntrega.obtenerPorId(id).orElse(null);
        if (entrega == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La entrega con id " + id + " no existe en la base de datos.")
                    .build();
        }
        try {
            Entrega entregaEliminado = servicioEntrega.eliminar(entrega);
            return Response.ok(entregaEliminado).build();

        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
}
