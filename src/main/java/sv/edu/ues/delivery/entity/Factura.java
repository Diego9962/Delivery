package sv.edu.ues.delivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Factura")
@Table(name = "factura")
@NoArgsConstructor @Data @EqualsAndHashCode
public class Factura implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_factura")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_emision")
	@NotNull
	private Date fechaEmision;
	
	@Column(name = "anulada")
	private boolean anulada;
	
	@Column(name = "observaciones")
	private String observaciones;

	@ManyToOne
	@JoinColumn(name = "id_orden")
	private Orden orden;

	@OneToMany(mappedBy = "factura", fetch = FetchType.LAZY)
	private List<Pago> pagos = new ArrayList<>();
	
	@Transient
	public void agregarPago(Pago pago){
		this.pagos.add(pago);
		pago.setFactura(this);
	}

	@Transient
	public void eliminarPago(Pago pago){
		this.pagos.remove(pago);
		pago.setFactura(null);
	}
}
