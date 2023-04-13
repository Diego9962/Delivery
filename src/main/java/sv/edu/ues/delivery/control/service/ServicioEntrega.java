package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.EntregaDao;
import sv.edu.ues.delivery.entity.Entrega;

@RequestScoped
public class ServicioEntrega extends ServicioGenerico<Entrega, Long> {
    
    @Inject EntregaDao entregaDao;
    
    @Override
    protected DaoGenerico<Entrega, Long> obtenerDao(){
        return entregaDao;
    }

}
