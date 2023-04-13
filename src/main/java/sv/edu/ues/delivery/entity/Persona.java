package sv.edu.ues.delivery.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Persona")
@Table(name = "persona")
@NoArgsConstructor @Data @EqualsAndHashCode
public class Persona implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_persona")
    private Long id;
    
    @NotNull
    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Column(name = "apellido")
    private String apellido;

    @NotNull
    @Column(name = "direccion")
    private String direccion;

    @Past
    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "fecha_nacimiento")
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss")
    private Date fechaNacimiento;

    @OneToMany(mappedBy = "persona", cascade =CascadeType.ALL)
    private List<Orden> ordenes = new ArrayList<>();

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<Entrega> entregas = new ArrayList<>();

    @Transient
    public void agregarOrden(Orden order){
        this.ordenes.add(order);
        order.setPersona(this);
    }

    @Transient
    public void eliminarOrder(Orden order){
        this.ordenes.remove(order);
        order.setPersona(null);
    }

    @Transient
    public void agregarEntrega(Entrega entrega){
        this.entregas.add(entrega);
        entrega.setPersona(this);
    }

    @Transient
    public void eliminarEntrega(Entrega entrega){
        this.entregas.remove(entrega);
        entrega.setPersona(null);
    }

}
