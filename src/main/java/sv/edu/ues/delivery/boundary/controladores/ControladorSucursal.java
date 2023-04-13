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
import sv.edu.ues.delivery.control.service.ServicioSucursal;
import sv.edu.ues.delivery.entity.Sucursal;

@Path("/sucursal")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorSucursal {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ServicioSucursal servicioSucursal;

    @GET
    public Response listarSucursales() {
        List<Sucursal> sucursales = servicioSucursal.listar();
        return Response.ok().entity(sucursales).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerSucursalesPorId(@PathParam("id") Long id) {
        Sucursal sucursal = servicioSucursal.obtenerPorId(id).orElse(null);
        if (sucursal == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La sucursal con id " + id + " no existe en la base de datos.")
                    .build();
        }
        return Response.ok().entity(sucursal).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearSucursal(Sucursal sucursalIngresada) {
        try {
            Sucursal sucursal = servicioSucursal.insertar(sucursalIngresada);
            return Response.created(uriInfo.getAbsolutePath()).entity(sucursal).build();
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
    public Response actualizarSucursal(@PathParam("id") Long id, Sucursal sucursal) {
        Sucursal sucursalRecuperado = servicioSucursal.obtenerPorId(id).orElse(null);
        if (sucursalRecuperado == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La sucursal con id " + id + " no existe en la base de datos.")
                    .build();
        }
        sucursalRecuperado.setNombre(sucursal.getNombre());
        sucursalRecuperado.setTelefono(sucursal.getTelefono());

        try {
            Sucursal sucursalActualizado = servicioSucursal.actualizar(sucursalRecuperado);
            return Response.ok(sucursalActualizado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarSucursalPorId(@PathParam("id") Long id) {
        Sucursal sucursal = servicioSucursal.obtenerPorId(id).orElse(null);
        if (sucursal == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La sucursal con id " + id + " no existe en la base de datos.")
                    .build();
        }
        try {
            Sucursal sucursalEliminado = servicioSucursal.eliminar(sucursal);
            return Response.ok(sucursalEliminado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
}
