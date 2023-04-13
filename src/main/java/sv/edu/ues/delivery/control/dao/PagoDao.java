package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Pago;

@RequestScoped
public class PagoDao extends DaoGenerico<Pago, Long>{
    public PagoDao(){
        super(Pago.class);
    }
    
}
