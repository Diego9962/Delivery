package sv.edu.ues.delivery.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @Data
public class DetalleOrdenPK implements Serializable{
    
    private Long idOrden;

    private String codigoProducto;
}
