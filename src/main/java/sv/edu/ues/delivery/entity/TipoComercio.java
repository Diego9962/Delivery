package sv.edu.ues.delivery.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "TipoComercio")
@Table(name = "tipo_comercio")
@NoArgsConstructor @Data @EqualsAndHashCode
public class TipoComercio implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tipo_comercio")
    private Long id;

    @NotNull
    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Column(name = "activo")
    private boolean activo;
    
    @Column(name = "comentarios")
    private String comentarios;

}
