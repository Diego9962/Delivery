package sv.edu.ues.delivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Territorio")
@Table(name = "territorio")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Territorio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_territorio")
    private Long idTerritorio;

    @NotNull
    private String nombre;

    @Column(name = "texto_visible")
    private String textoVisible;

    @Column(name = "hijos_obligatorios")
    @Min(0)
    private Integer hijosObligatorios;

    @ManyToOne
    @JoinColumn(name = "id_padre")
    private Territorio idTerritorioPadre;

}
