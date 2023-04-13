package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.DetalleOrden;

@RequestScoped
public class DetalleOrdenDao extends DaoGenerico<DetalleOrden, Long> {
    
    public DetalleOrdenDao(){
        super(DetalleOrden.class);
    }

}
