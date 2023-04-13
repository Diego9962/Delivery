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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import sv.edu.ues.delivery.control.service.ServicioTipoComercio;
import sv.edu.ues.delivery.entity.TipoComercio;

@Path("/tipocomercio")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorTipoComercio {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ServicioTipoComercio servicioTipoComercio;

    @GET
    public Response listarTipoComercios() {
        List<TipoComercio> tipoComercios = servicioTipoComercio.listar();
        return Response.ok().entity(tipoComercios).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerTipoComercioPorId(@PathParam("id") Long id) {
        TipoComercio tipoComercio = servicioTipoComercio.obtenerPorId(id).orElse(null);
        if (tipoComercio == null) {
            return Response.status(Status.NOT_FOUND)
                    .header(RestResourcePattern.ID_NOT_FOUND, "El tipo de comercio con id " + id + " no existe en la base de datos.")
                    .build();
        }
        return Response.ok().entity(tipoComercio).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearTipoComercio(TipoComercio tipoComercioIngresado) throws URISyntaxException {
        try {
            TipoComercio tipoComercio = servicioTipoComercio.insertar(tipoComercioIngresado);
            return Response.created(new URI(uriInfo.getAbsolutePath() + "/" + tipoComercio.getId())).entity(tipoComercio).build();
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
    public Response actualizarTipoComercio(@PathParam("id") Long id, TipoComercio tipoComercio) {
        TipoComercio tipoComercioRecuperado = servicioTipoComercio.obtenerPorId(id).orElse(null);
        if (tipoComercioRecuperado == null) {
            return Response.status(Status.NOT_FOUND)
                    .header(RestResourcePattern.ID_NOT_FOUND, "El tipo de comercio con id " + id + " no existe en la base de datos.")
                    .build();
        }

        tipoComercioRecuperado.setNombre(tipoComercio.getNombre());
        tipoComercioRecuperado.setActivo(tipoComercio.isActivo());
        tipoComercioRecuperado.setComentarios(tipoComercio.getComentarios());
        try {
            TipoComercio tipoComercioActualizado = servicioTipoComercio.actualizar(tipoComercioRecuperado);
            return Response.ok(tipoComercioActualizado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarTipoComercioPorId(@PathParam("id") Long id) {
        TipoComercio tipoComercio = servicioTipoComercio.obtenerPorId(id).orElse(null);
        if (tipoComercio == null) {
            return Response.status(Status.NOT_FOUND)
                    .header(RestResourcePattern.ID_NOT_FOUND, "El tipo de comercio con id " + id + " no existe en la base de datos.")
                    .build();
        }
        try {
            TipoComercio tipoComercioEliminado = servicioTipoComercio.eliminar(tipoComercio);
            return Response.ok(tipoComercioEliminado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
}
