package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Sucursal;

@RequestScoped
public class SucursalDao extends DaoGenerico<Sucursal, Long>{
   
    public SucursalDao(){
        super(Sucursal.class);
    }
    
}
