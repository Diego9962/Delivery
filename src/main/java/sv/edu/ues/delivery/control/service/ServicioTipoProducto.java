package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.TipoProductoDao;
import sv.edu.ues.delivery.entity.TipoProducto;

@RequestScoped
public class ServicioTipoProducto extends ServicioGenerico<TipoProducto, Long> {
    
    @Inject TipoProductoDao tipoProductoDao;
    
    @Override
    protected DaoGenerico<TipoProducto, Long> obtenerDao(){
        return tipoProductoDao;
    }

}
