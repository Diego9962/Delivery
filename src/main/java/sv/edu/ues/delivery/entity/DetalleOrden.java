package sv.edu.ues.delivery.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "DetalleOrden")
@Table(name = "detalle_orden")
@IdClass(DetalleOrdenPK.class)
@NoArgsConstructor @Data @EqualsAndHashCode
public class DetalleOrden implements Serializable{

    @Id
    @Column(name = "orden")
    private Long idOrden;

    @Id
    @Column(name = "producto")
    private String codigoProducto;

    @NotNull
    @Min(1)
    @Column(name = "cantidad")
    private int cantidad;


    public DetalleOrden(Long idOrden, String codigoProducto){
        this.idOrden = idOrden;
        this.codigoProducto = codigoProducto;
    }

    public DetalleOrdenPK getId(){
        return new DetalleOrdenPK(idOrden, codigoProducto);
    }

    public void setId(DetalleOrdenPK id){
        this.idOrden = id.getIdOrden();
        this.codigoProducto = id.getCodigoProducto();
    }
}
