package sv.edu.ues.delivery.control.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sv.edu.ues.delivery.control.dao.DaoGenerico;
import sv.edu.ues.delivery.control.dao.PersonaDao;
import sv.edu.ues.delivery.entity.Persona;

@RequestScoped
public class ServicioPersona extends ServicioGenerico<Persona, Long> {
    
    @Inject PersonaDao personaDao;
    
    @Override
    protected DaoGenerico<Persona, Long> obtenerDao(){
        return personaDao;
    }

}
