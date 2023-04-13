package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Orden;

@RequestScoped
public class OrdenDao extends DaoGenerico<Orden, Long>{
    
    public OrdenDao(){
        super(Orden.class);
    }
    
}
