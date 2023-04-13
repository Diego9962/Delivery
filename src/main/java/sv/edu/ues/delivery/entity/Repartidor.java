package sv.edu.ues.delivery.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import jakarta.validation.constraints.Past;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Repartidor")
@Table(name = "repartidor")
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Repartidor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_repartidor")
    private Long id;

    @NotNull
    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Column(name = "apellido")
    private String apellido;

    @NotNull
    @Column(name = "tipo_licencia")
    @Enumerated(EnumType.STRING)
    private TipoLicencia tipoLicencia;

    @NotNull
    @Past
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @NotNull
    @Column(name = "activo")
    private boolean activo;

    @NotNull
    @Column(name = "observacion")
    private String observacion;

    @NotNull
    @Column(name = "salario")
    private Double salario;

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

    @OneToOne
    @JoinColumn(name = "id_orden")
    private Orden orden;

    @OneToMany(mappedBy = "repartidor", cascade = CascadeType.ALL)
    private List<Entrega> entregas = new ArrayList<>();

    @Transient
    public void agregarEntrega(Entrega entrega) {
        this.entregas.add(entrega);
        entrega.setRepartidor(this);
    }

    @Transient
    public void eliminarEntrega(Entrega entrega) {
        this.entregas.remove(entrega);
        entrega.setRepartidor(null);
    }
}
