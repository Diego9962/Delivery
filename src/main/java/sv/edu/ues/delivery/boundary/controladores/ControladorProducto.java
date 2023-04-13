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
import sv.edu.ues.delivery.control.service.ServicioProducto;
import sv.edu.ues.delivery.entity.Producto;

@Path("/producto")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ControladorProducto {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ServicioProducto servicioProducto;

    @GET
    public Response listarPagos() {
        List<Producto> pagos = servicioProducto.listar();
        return Response.ok().entity(pagos).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerProductoPorId(@PathParam("id") String id) {
        Producto producto = servicioProducto.obtenerPorId(id).orElse(null);
        if (producto == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "El producto con id " + id + " no existe en la base de datos.")
                    .build();
        }
        return Response.ok().entity(producto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearPago(Producto productoIngresado) {
        try {
            Producto producto = servicioProducto.insertar(productoIngresado);
            return Response.created(uriInfo.getAbsolutePath()).entity(producto).build();
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
    public Response actualizarProducto(@PathParam("id") String id, Producto producto) {
        Producto productoRecuperado = servicioProducto.obtenerPorId(id).orElse(null);
        if (productoRecuperado == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "El producto con id " + id + " no existe en la base de datos.")
                    .build();
        }
        productoRecuperado.setCodigo(producto.getCodigo());
        productoRecuperado.setNombre(producto.getNombre());
        productoRecuperado.setActivo(producto.isActivo());
        productoRecuperado.setDescripcion(producto.getDescripcion());
        productoRecuperado.setPrecioCompra(producto.getPrecioCompra());
        productoRecuperado.setPrecioVenta(producto.getPrecioVenta());
        productoRecuperado.setCantidadExistente(producto.getCantidadExistente());

        try {
            Producto productoActualizado = servicioProducto.actualizar(productoRecuperado);
            return Response.ok(productoActualizado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarProductoPorId(@PathParam("id") String id) {
        Producto producto = servicioProducto.obtenerPorId(id).orElse(null);
        if (producto == null) {
            return Response.status(Status.NOT_FOUND)
                    .header("mensaje", "El producto con id " + id + " no existe en la base de datos.")
                    .build();
        }
        try {
            Producto productoEliminado = servicioProducto.eliminar(producto);
            return Response.ok(productoEliminado).build();
        } catch (ConstraintViolationException ex) {
            List<String> errores = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return Response.status(Status.BAD_REQUEST).entity(errores).build();
        }
    }
}
