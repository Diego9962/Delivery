package sv.edu.ues.delivery.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Comercio")
@Table(name = "comercio")
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Comercio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_comercio")
    private long id;

    @NotNull
    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Column(name = "activo")
    private boolean activo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "logo")
    private String logo;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Sucursal> sucursales = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<TipoComercio> tipos = new ArrayList<>();

    public Comercio(Long id){
        this.id = id;
    }

    @Transient
    public void agregarSucursal(Sucursal sucursal) {
        if(!this.sucursales.contains(sucursal)){
            this.sucursales.add(sucursal);
            // sucursal.setComercio(this);
        }
    }

    @Transient
    public void eliminarSucursal(Sucursal sucursal) {
        if(this.sucursales.contains(sucursal)){
            this.sucursales.remove(sucursal);
            // sucursal.setComercio(null);
        }
    }

    @Transient
    public void agregarTipo(TipoComercio tipo){
        if(!this.tipos.contains(tipo)){
            this.tipos.add(tipo);
        }
    }

    @Transient
    public void eliminarTipo(TipoComercio tipo){
        if(this.tipos.contains(tipo)){
            this.tipos.remove(tipo);
        }
    }

}
