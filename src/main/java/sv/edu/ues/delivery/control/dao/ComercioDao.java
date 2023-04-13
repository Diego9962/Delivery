package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Comercio;

@RequestScoped
public class ComercioDao extends DaoGenerico<Comercio, Long>{
    
    public ComercioDao(){
        super(Comercio.class);
    }
}
