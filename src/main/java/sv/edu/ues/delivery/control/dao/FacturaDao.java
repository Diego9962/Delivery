package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Factura;

@RequestScoped
public class FacturaDao extends DaoGenerico<Factura, Long>{
    
    public FacturaDao(){
        super(Factura.class);
    }
    
}
