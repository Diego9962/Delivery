package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Repartidor;

@RequestScoped
public class RepartidorDao extends DaoGenerico<Repartidor, Long>{
    
    public RepartidorDao(){
        super(Repartidor.class);
    }
    
}
