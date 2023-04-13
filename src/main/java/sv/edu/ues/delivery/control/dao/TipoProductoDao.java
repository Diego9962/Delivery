package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.TipoProducto;

@RequestScoped
public class TipoProductoDao extends DaoGenerico<TipoProducto, Long> {
    
    public TipoProductoDao(){
        super(TipoProducto.class);
    }
}
