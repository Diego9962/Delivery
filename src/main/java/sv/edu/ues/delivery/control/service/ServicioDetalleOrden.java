package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.DetalleOrdenDao;
import sv.edu.ues.delivery.entity.DetalleOrden;

@RequestScoped
public class ServicioDetalleOrden extends ServicioGenerico<DetalleOrden, Long> {
    @Inject DetalleOrdenDao detalleOrdenDao;
    
    @Override
    protected DaoGenerico<DetalleOrden, Long> obtenerDao(){
        return detalleOrdenDao;
    }

}
