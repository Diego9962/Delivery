package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.TerritorioDao;
import sv.edu.ues.delivery.entity.Territorio;

@RequestScoped
public class ServicioTerritorio extends ServicioGenerico<Territorio, Long>{
    
    @Inject
    private TerritorioDao territorioDao;

    @Override
    protected DaoGenerico<Territorio, Long> obtenerDao() {
        return territorioDao;
    }

    
}
