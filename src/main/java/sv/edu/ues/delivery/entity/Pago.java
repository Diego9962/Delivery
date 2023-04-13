package sv.edu.ues.delivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Pago")
@Table(name = "pago")
@NoArgsConstructor @Data @EqualsAndHashCode
public class Pago implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_pago")
	private long id;
	
	@NotNull
	@Column(name = "tipo_pago")
	private String tipoPago;
	
	@NotNull
	@Column(name = "monto")
	private float monto;
	
	@NotNull
	@Column(name = "referencia")
	private String referencia;
	
	@NotNull
	@Column(name = "estado")
	private String estado;

	@ManyToOne
	@JoinColumn(name = "id_factura")
	private Factura factura;
}
