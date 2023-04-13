package sv.edu.ues.delivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Vehiculo")
@Table(name = "vehiculo")
@NoArgsConstructor @Data @EqualsAndHashCode
public class Vehiculo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_vehiculo")
	private Long id;
		
	@NotNull
	@Column(name = "tipo_vehiculo")
	private String tipoVehiculo;
		
	@NotNull
	@Column(name = "placa")
	private String placa;

	// REVISAR debe de ser una relación con el repartidor.
	@NotNull
	@Column(name = "propietario")
	private String propietario;
	
	@NotNull
	@Column(name = "activo")
	private boolean activo;

	@NotNull
	@Column(name = "comentario")
	private String comentario;
}

