package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.VehiculoDao;
import sv.edu.ues.delivery.entity.Vehiculo;

@RequestScoped
public class ServicioVehiculo extends ServicioGenerico<Vehiculo, Long> {
    
    @Inject VehiculoDao vehiculoDao;
    
    @Override
    protected DaoGenerico<Vehiculo, Long> obtenerDao(){
        return vehiculoDao;
    }
}
