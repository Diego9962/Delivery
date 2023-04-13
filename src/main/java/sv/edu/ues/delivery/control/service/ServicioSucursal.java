package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.SucursalDao;
import sv.edu.ues.delivery.entity.Sucursal;

@RequestScoped
public class ServicioSucursal extends ServicioGenerico<Sucursal, Long> {
    
    @Inject SucursalDao sucursalDao;
    
    @Override
    protected DaoGenerico<Sucursal, Long> obtenerDao(){
        return sucursalDao;
    }

}
