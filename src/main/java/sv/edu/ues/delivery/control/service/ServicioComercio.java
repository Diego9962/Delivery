package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.ComercioDao;
import sv.edu.ues.delivery.entity.Comercio;

@RequestScoped
public class ServicioComercio extends ServicioGenerico<Comercio, Long>{
    
    @Inject ComercioDao comercioDao;

    @Override
    protected DaoGenerico<Comercio, Long> obtenerDao(){
        return comercioDao;
    }
}
