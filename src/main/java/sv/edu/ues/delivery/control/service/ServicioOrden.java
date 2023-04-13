package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.OrdenDao;
import sv.edu.ues.delivery.entity.Orden;

@RequestScoped
public class ServicioOrden extends ServicioGenerico<Orden, Long> {
    
    @Inject OrdenDao ordenDao;
    
    @Override
    protected DaoGenerico<Orden, Long> obtenerDao(){
        return ordenDao;
    }
}
