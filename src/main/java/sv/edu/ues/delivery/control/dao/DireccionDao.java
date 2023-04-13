package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Direccion;

@RequestScoped
public class DireccionDao extends DaoGenerico<Direccion, Long> {
    
    public DireccionDao(){
        super(Direccion.class);
    }
    
}
