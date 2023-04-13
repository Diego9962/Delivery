package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.ProductoDao;
import sv.edu.ues.delivery.entity.Producto;

@RequestScoped
public class ServicioProducto extends ServicioGenerico<Producto, String> {
    
    @Inject ProductoDao productoDao;
    
    @Override
    protected DaoGenerico<Producto, String> obtenerDao(){
        return productoDao;
    }

}
