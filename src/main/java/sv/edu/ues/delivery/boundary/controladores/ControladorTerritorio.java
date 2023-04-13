package sv.edu.ues.delivery.boundary.controladores;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

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
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Response.Status;
import sv.edu.ues.delivery.control.service.ServicioTerritorio;
import sv.edu.ues.delivery.entity.Territorio;

@RequestScoped
@Path("/territorio")
@Produces(MediaType.APPLICATION_JSON)
public class ControladorTerritorio {

    @Context
    private UriInfo uriInfo;
    
    @Inject
    private ServicioTerritorio servicioTerritorio;

    @GET
    public Response listarTerritorios(){
        List<Territorio> listaTerritorios = servicioTerritorio.listar();
        return Response.ok(listaTerritorios).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerTerritorioPorId(@PathParam("id") Long id){
        Territorio territorio = servicioTerritorio.obtenerPorId(id).orElse(null);
        if(territorio == null){
            return Response.status(Status.NOT_FOUND)
                    .header(RestResourcePattern.ID_NOT_FOUND, "El territorio con id " + id + " no existe en la base de datos.")
                    .build();
        }

        return Response.ok(territorio).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregarTerritorio(Territorio territorio) throws URISyntaxException{
        try{
            Territorio nuevoTerritorio = servicioTerritorio.insertar(territorio);
            return Response.created(new URI(uriInfo.getAbsolutePath() + "/" + nuevoTerritorio.getIdTerritorio()))
                .entity(nuevoTerritorio)
                .build();
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarTerritorio(@PathParam("id") Long id, Territorio territorio){
        Territorio territorioRecuperado = servicioTerritorio.obtenerPorId(id).orElse(null);
        if(territorioRecuperado == null){
            return Response.status(Status.NOT_FOUND)
                    .header(RestResourcePattern.ID_NOT_FOUND, "El territorio con id " + id + " no existe en la base de datos.")
                    .build();
        }

        territorioRecuperado.setNombre(territorio.getNombre());
        territorioRecuperado.setHijosObligatorios(territorio.getHijosObligatorios());
        territorioRecuperado.setTextoVisible(territorio.getTextoVisible());

        try{
            Territorio territorioActualizado = servicioTerritorio.actualizar(territorioRecuperado);
            return Response.ok(territorioActualizado).build();
        }catch(ConstraintViolationException ex){
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }

    }

    @DELETE
    @Path("/{id}")
    public Response eliminarTerritorio(@PathParam("id") Long id){
        Territorio territorio = servicioTerritorio.obtenerPorId(id).orElse(null);
        if(territorio == null){
            return Response.status(Status.NOT_FOUND)
                    .header(RestResourcePattern.ID_NOT_FOUND, "El territorio con id " + id + " no existe en la base de datos")
                    .build();
        }
        try {
            Territorio territorioEliminado = servicioTerritorio.eliminar(territorio);
            return Response.ok(territorioEliminado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
}
