package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Producto;

@RequestScoped
public class ProductoDao extends DaoGenerico<Producto, String>{
    
    public ProductoDao(){
        super(Producto.class);
    }
    
}