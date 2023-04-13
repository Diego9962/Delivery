package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.FacturaDao;
import sv.edu.ues.delivery.entity.Factura;

@RequestScoped
public class ServicioFactura extends ServicioGenerico<Factura, Long> {
    
    @Inject FacturaDao facturaDao;
    
    @Override
    protected DaoGenerico<Factura, Long> obtenerDao(){
        return facturaDao;
    }

}
