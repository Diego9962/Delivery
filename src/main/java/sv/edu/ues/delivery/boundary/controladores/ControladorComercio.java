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

import sv.edu.ues.delivery.control.service.ServicioComercio;
import sv.edu.ues.delivery.control.service.ServicioSucursal;
import sv.edu.ues.delivery.control.service.ServicioTipoComercio;
import sv.edu.ues.delivery.entity.Comercio;
import sv.edu.ues.delivery.entity.Sucursal;
import sv.edu.ues.delivery.entity.TipoComercio;

@Path("/comercio")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorComercio {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ServicioComercio servicioComercio;

    @Inject
    private ServicioTipoComercio servicioTipoComercio;

    @Inject
    private ServicioSucursal servicioSucursal;

    @GET
    public Response listarComercios() {
        List<Comercio> comercios = servicioComercio.listar();
        return Response.ok().entity(comercios).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerComercioPorId(@PathParam("id") Long id) {
        Comercio comercio = servicioComercio.obtenerPorId(id).orElse(null);
        if (comercio == null) {
            return Response.status(Status.NOT_FOUND)
                    .header(RestResourcePattern.ID_NOT_FOUND, "El comercio con id " + id + " no existe en la base de datos.")
                    .build();
        }
        return Response.ok().entity(comercio).build();
    }

    @GET
    @Path("/{id}/tipocomercio")
    public Response obtenerTiposComercio(@PathParam("id") Long id){
        Comercio comercio = servicioComercio.obtenerPorId(id).orElse(null);
        if(comercio == null){
            return Response.status(Status.NOT_FOUND)
                .header(RestResourcePattern.ID_NOT_FOUND, "El comercio con id " + id + " no existe en la base de datos.")
                .build();
        }
        List<TipoComercio> tipos = comercio.getTipos();
        return Response.ok(tipos).header(RestResourcePattern.CONTAR_REGISTROS, tipos.size()).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/tipocomercio/{idTipoComercio}")
    public Response agregarTipoComercio(@PathParam("id") Long id, @PathParam("idTipoComercio") Long idTipoComercio) throws URISyntaxException{
        Comercio comercio = servicioComercio.obtenerPorId(id).orElse(null);
        if(comercio == null){
            return Response.status(Status.BAD_REQUEST)
                .header(RestResourcePattern.WRONG_PARAMETER, "Los parametros enviados son invalidos")
                .build();
        }

        TipoComercio tipoComercio = servicioTipoComercio.obtenerPorId(idTipoComercio).orElse(null);
        
        if(tipoComercio == null){
            return Response.status(Status.BAD_REQUEST)
                .header(RestResourcePattern.WRONG_PARAMETER, "Los parametros enviados son invalidos")
                .build();
        }
        comercio.agregarTipo(tipoComercio);
        comercio = servicioComercio.actualizar(comercio);
        
        return Response.created(uriInfo.getAbsolutePath()).entity(comercio).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearComercio(Comercio comercioIngresado) throws URISyntaxException {
        try {
            Comercio comercio = servicioComercio.insertar(comercioIngresado);
            return Response.created(new URI(uriInfo.getAbsolutePath() + "/" + comercio.getId())).entity(comercio).build();
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
    public Response actualizarComercio(@PathParam("id") Long id, Comercio comercio) {
        Comercio comercioRecuperado = servicioComercio.obtenerPorId(id).orElse(null);
        if (comercioRecuperado == null) {
            return Response.status(Status.NOT_FOUND)
                    .header(RestResourcePattern.ID_NOT_FOUND, "El comercio con id " + id + " no existe en la base de datos.")
                    .build();
        }
        comercioRecuperado.setNombre(comercio.getNombre());
        comercioRecuperado.setActivo(comercio.isActivo());
        comercioRecuperado.setDescripcion(comercio.getDescripcion());
        comercioRecuperado.setLogo(comercio.getLogo());

        try {
            Comercio comercioActualizado = servicioComercio.actualizar(comercioRecuperado);
            return Response.ok(comercioActualizado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarComercioPorId(@PathParam("id") Long id) {
        Comercio comercio = servicioComercio.obtenerPorId(id).orElse(null);
        if (comercio == null) {
            return Response.status(Status.NOT_FOUND)
                    .header(RestResourcePattern.ID_NOT_FOUND, "El comercio con id " + id + " no existe en la base de datos")
                    .build();
        }
        try {
            Comercio comercioEliminado = servicioComercio.eliminar(comercio);
            return Response.ok(comercioEliminado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @POST
    @Path("/{id}/sucursal")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response asociarSucursal(@PathParam("id") Long id, Sucursal sucursal) throws URISyntaxException{
        Comercio comercio = servicioComercio.obtenerPorId(id).orElse(null);
        if(comercio == null){
            return Response.status(Status.BAD_REQUEST)
                    .build();
        }
        try{
            Sucursal sucursalCreada = servicioSucursal.insertar(sucursal);
            comercio.agregarSucursal(sucursalCreada);
            servicioComercio.actualizar(comercio);
            return Response.created(new URI(uriInfo.getAbsolutePath() + "/" + sucursalCreada.getId()))
                .entity(sucursalCreada)
                .build();
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

}
