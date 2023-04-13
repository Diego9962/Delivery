package sv.edu.ues.delivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "TipoProducto")
@Table(name = "tipo_producto")
@NoArgsConstructor @Data @EqualsAndHashCode
public class TipoProducto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tipo_producto")
    private Long id;
    
    @NotNull
    @Column(name = "nombre")
    private String nombre;
    
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    
    @NotNull
    @Column(name = "comentarios")
    private String comentarios;
    
    @OneToMany(mappedBy = "tipoProducto", fetch = FetchType.LAZY)
    private List<Producto> productos = new ArrayList<>();
    
    @Transient
    public void agregarProducto(Producto producto){
        this.productos.add(producto);
        producto.setTipoProducto(this);
    }
    
    @Transient
    public void eliminarProducto(Producto producto){
        this.productos.remove(producto);
        producto.setTipoProducto(null);
    }
}
