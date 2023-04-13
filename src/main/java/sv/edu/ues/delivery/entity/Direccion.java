package sv.edu.ues.delivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name= "Direccion")
@Table(name = "direccion")
@NoArgsConstructor @Data @EqualsAndHashCode
public class Direccion implements Serializable {

	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_direccion")
	private Long id;

	@NotNull
	@Column(name = "direccion")
	private String direccion;

	@NotNull
	@Column(name = "latitud")
	private BigDecimal latitud;

	@NotNull
	@Column(name = "longitud")
	private BigDecimal longitud;

	private String referencias;

	@OneToOne
	@JoinColumn(name = "id_territorio")
	private Territorio idTerritorio;

	
}
