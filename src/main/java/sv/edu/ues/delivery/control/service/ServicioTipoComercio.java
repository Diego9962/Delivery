package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.TipoComercioDao;
import sv.edu.ues.delivery.entity.TipoComercio;

@RequestScoped
public class ServicioTipoComercio extends ServicioGenerico<TipoComercio, Long> {
    
    @Inject TipoComercioDao tipoComercioDao;
    
    @Override
    protected DaoGenerico<TipoComercio, Long> obtenerDao(){
        return tipoComercioDao;
    }

}
