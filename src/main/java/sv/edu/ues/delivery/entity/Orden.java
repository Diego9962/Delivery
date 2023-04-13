package sv.edu.ues.delivery.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Orden")
@Table(name = "orden")
@NoArgsConstructor @Data @EqualsAndHashCode
public class Orden implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_orden")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_orden")
    @NotNull
    @PastOrPresent
    private Date fechaOrden;

    @Column(name = "observaciones")
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "estado")
    private EstadoOrden estado;

    @ManyToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

    @OneToOne(mappedBy = "orden", fetch = FetchType.LAZY)
    private Repartidor repartidor;

    @OneToMany(mappedBy = "orden", fetch = FetchType.LAZY)
    private List<Factura> facturas = new ArrayList<>();

    @Transient
    public void asignarRepartidor(Repartidor repartidor){
        this.repartidor = repartidor;
        repartidor.setOrden(this);
    }

    @Transient
    public void desasignarRepartidor(Repartidor repartidor){
        if(this.repartidor != null){
            this.repartidor = null;
            repartidor.setOrden(null);
        }
    }

    @Transient
    public void agregarFactura(Factura factura){
        this.facturas.add(factura);
        factura.setOrden(this);
    }

    @Transient
    public void eliminarFactura(Factura factura){
        this.facturas.remove(factura);
        factura.setOrden(null);
    }
    
}
