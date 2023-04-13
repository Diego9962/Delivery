package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Persona;

@RequestScoped
public class PersonaDao extends DaoGenerico<Persona, Long>{
    
    public PersonaDao(){
        super(Persona.class);
    }
}
