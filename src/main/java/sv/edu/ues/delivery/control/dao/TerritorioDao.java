package sv.edu.ues.delivery.control.dao;

import jakarta.enterprise.context.RequestScoped;
import sv.edu.ues.delivery.entity.Territorio;

@RequestScoped
public class TerritorioDao extends DaoGenerico<Territorio, Long>{
    
    public TerritorioDao(){
        super(Territorio.class);
    }
}
