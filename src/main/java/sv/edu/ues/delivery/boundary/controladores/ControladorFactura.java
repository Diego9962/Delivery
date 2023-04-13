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
import sv.edu.ues.delivery.control.service.ServicioFactura;
import sv.edu.ues.delivery.entity.Factura;

@Path("/factura")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorFactura {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ServicioFactura servicioFactura;

    @GET
    public Response listarFacturas() {
        List<Factura> facturas = servicioFactura.listar();
        return Response.ok().entity(facturas).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerFacturaPorId(@PathParam("id") Long id) {
        Factura factura = servicioFactura.obtenerPorId(id).orElse(null);
        if (factura == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La factura con id " + id + " no existe en la base de datos.")
                    .build();
        }
        return Response.ok().entity(factura).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearFactura(Factura facturaIngresada) {
        try {
            Factura factura = servicioFactura.insertar(facturaIngresada);
            return Response.created(uriInfo.getAbsolutePath()).entity(factura).build();
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
    public Response actualizarFactura(@PathParam("id") Long id, Factura factura) {
        Factura facturaRecuperado = servicioFactura.obtenerPorId(id).orElse(null);
        if (facturaRecuperado == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La factura con id " + id + " no existe en la base de datos.")
                    .build();
        }
        facturaRecuperado.setFechaEmision(factura.getFechaEmision());
        facturaRecuperado.setAnulada(factura.isAnulada());
        facturaRecuperado.setObservaciones(factura.getObservaciones());

        try {
            Factura facturaActualizado = servicioFactura.actualizar(facturaRecuperado);
            return Response.ok(facturaActualizado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarFacturaPorId(@PathParam("id") Long id) {
        Factura factura = servicioFactura.obtenerPorId(id).orElse(null);
        if (factura == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "La factura con id " + id + " no existe en la base de datos.")
                    .build();
        }
        try {
            Factura facturaEliminado = servicioFactura.eliminar(factura);
            return Response.ok(facturaEliminado).build();

        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

}
