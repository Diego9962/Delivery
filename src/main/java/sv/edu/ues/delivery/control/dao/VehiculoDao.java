package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Vehiculo;

@RequestScoped
public class VehiculoDao extends DaoGenerico<Vehiculo, Long>{
    
    public VehiculoDao(){
        super(Vehiculo.class);
    }
    
}
