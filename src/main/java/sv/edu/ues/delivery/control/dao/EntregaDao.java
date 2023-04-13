package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Entrega;

@RequestScoped
public class EntregaDao extends DaoGenerico<Entrega, Long> {
    
    public EntregaDao(){
        super(Entrega.class);
    }
}
