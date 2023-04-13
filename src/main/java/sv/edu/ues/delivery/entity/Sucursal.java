package sv.edu.ues.delivery.entity;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Sucursal")
@Table(name = "sucursal")
@NoArgsConstructor @Data @EqualsAndHashCode
public class Sucursal implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_sucursal")
	private long id;

	@NotNull
	private String nombre;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_direccion")
	private Direccion direccion;

	@Pattern(regexp = "^\\d{4}-\\d{4}$")
	@Column(name = "telefono")
	private String telefono;

	// @ManyToOne
	// @JoinColumn(name = "id_comercio")
	// private Comercio comercio;

	@OneToMany(mappedBy = "sucursal", fetch = FetchType.LAZY)
	private List<Producto> productos = new ArrayList<>();

	@OneToMany(mappedBy = "sucursal", fetch = FetchType.LAZY)
	private List<Orden> ordenes = new ArrayList<>();

	@OneToMany(mappedBy = "sucursal", fetch = FetchType.LAZY)
	private List<Repartidor> repartidores = new ArrayList<>();

	@Transient
	public void agregarProducto(Producto producto){
		this.productos.add(producto);
		producto.setSucursal(this);
	}

	@Transient
	public void eliminarProducto(Producto producto){
		this.productos.remove(producto);
		producto.setSucursal(null);
	}

	@Transient
	public void agregarOrden(Orden orden){
		this.ordenes.add(orden);
		orden.setSucursal(this);
	}

	@Transient
	public void eliminarOrden(Orden orden){
		this.ordenes.remove(orden);
		orden.setSucursal(null);
	}

	@Transient
	public void agregarRepartidor(Repartidor repartidor){
		this.repartidores.add(repartidor);
		repartidor.setSucursal(this);
	}

	@Transient
	public void eliminarRepartidor(Repartidor repartidor){
		this.repartidores.remove(repartidor);
		repartidor.setSucursal(null);
	}
}
