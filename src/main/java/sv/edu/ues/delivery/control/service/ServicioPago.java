package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.PagoDao;
import sv.edu.ues.delivery.entity.Pago;

@RequestScoped
public class ServicioPago extends ServicioGenerico<Pago, Long> {
    
    @Inject PagoDao pagoDao;
    
    @Override
    protected DaoGenerico<Pago, Long> obtenerDao(){
        return pagoDao;
    }

}
