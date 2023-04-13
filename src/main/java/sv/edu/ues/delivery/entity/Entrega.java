package sv.edu.ues.delivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Entrega")
@Table(name = "entrega")
@NoArgsConstructor @Data @EqualsAndHashCode
public class Entrega implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_entrega")
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creación")
    @NotNull
    @PastOrPresent
    private Date fechaCreacion;

    @NotNull
    @Column(name = "estado_entrega")
    @Enumerated(EnumType.STRING)
    private EstadoEntrega estadoEntrega;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent
    @Column(name = "fecha_alcanzado")
    private Date fechaAlcanzado;
    
    @NotNull
    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "id_repartidor")
    private Repartidor repartidor;
    
}
