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
import sv.edu.ues.delivery.control.service.ServicioRepartidor;
import sv.edu.ues.delivery.entity.Repartidor;

@Path("/repartidor")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorRepartidor {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ServicioRepartidor servicioRepartidor;

    @GET
    public Response listarRepartidores() {
        List<Repartidor> repartidores = servicioRepartidor.listar();
        return Response.ok().entity(repartidores).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerClientePorId(@PathParam("id") Long id) {
        Repartidor repartidor = servicioRepartidor.obtenerPorId(id).orElse(null);
        if (repartidor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .header("mensaje", "El repartidor con id " + id + " no existe en la base de datos.")
                    .build();
        }
        return Response.ok().entity(repartidor).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearRepartidor(Repartidor repartidorIngresado) {
        try {
            Repartidor repartidor = servicioRepartidor.insertar(repartidorIngresado);
            return Response.created(uriInfo.getAbsolutePath()).entity(repartidor).build();
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
    public Response actualizarRepartidor(@PathParam("id") Long id, Repartidor repartidor) {
        Repartidor repartidorRecuperado = servicioRepartidor.obtenerPorId(id).orElse(null);
        if (repartidorRecuperado == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "El repartidor con id " + id + " no existe en la base de datos.")
                    .build();
        }
        repartidorRecuperado.setNombre(repartidor.getNombre());
        repartidorRecuperado.setApellido(repartidor.getApellido());
        repartidorRecuperado.setTipoLicencia(repartidor.getTipoLicencia());
        repartidorRecuperado.setFechaNacimiento(repartidor.getFechaNacimiento());
        repartidorRecuperado.setActivo(repartidor.isActivo());
        repartidorRecuperado.setObservacion(repartidor.getObservacion());
        repartidorRecuperado.setSalario(repartidor.getSalario());

        try {
            Repartidor repartidorActualizado = servicioRepartidor.actualizar(repartidorRecuperado);
            return Response.ok(repartidorActualizado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarRepartidorPorId(@PathParam("id") Long id) {
        Repartidor repartidor = servicioRepartidor.obtenerPorId(id).orElse(null);
        if (repartidor == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "El repartidor " + id + " no existe en la base de datos.")
                    .build();
        }

        try {
            Repartidor repartidorEliminado = servicioRepartidor.eliminar(repartidor);
            return Response.ok(repartidorEliminado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

}
