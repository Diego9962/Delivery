package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.TipoComercio;

@RequestScoped
public class TipoComercioDao extends DaoGenerico<TipoComercio, Long>{

    public TipoComercioDao() {
        super(TipoComercio.class);
    }
    
}
