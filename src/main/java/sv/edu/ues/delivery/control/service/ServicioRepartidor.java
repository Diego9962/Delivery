package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.RepartidorDao;
import sv.edu.ues.delivery.entity.Repartidor;

@RequestScoped
public class ServicioRepartidor extends ServicioGenerico<Repartidor, Long> {
    
    @Inject RepartidorDao repartidorDao;
    
    @Override
    protected DaoGenerico<Repartidor, Long> obtenerDao(){
        return repartidorDao;
    }
    
}
