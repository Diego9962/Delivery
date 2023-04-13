package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.DireccionDao;
import sv.edu.ues.delivery.entity.Direccion;

@RequestScoped
public class ServicioDireccion extends ServicioGenerico<Direccion, Long> {
    
    @Inject DireccionDao direccionDao;
    
    @Override
    protected DaoGenerico<Direccion, Long> obtenerDao(){
        return direccionDao;
    }

}
