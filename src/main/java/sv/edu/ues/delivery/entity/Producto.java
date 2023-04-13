package sv.edu.ues.delivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Producto")
@Table(name = "producto")
@NoArgsConstructor @Data @EqualsAndHashCode
public class Producto implements Serializable {
	
	@Id
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "nombre")
	private String nombre;

	@NotNull
	@Column(name = "activo")
	private boolean activo;

	@NotNull 
	@Column(name = "descripcion")
	private String descripcion;

	@NotNull
	@Column(name = "precio_compra")
	private Double precioCompra;

	@NotNull
	@Column(name = "precio_venta")
	private Double precioVenta;

	@Min(0)
	@Column(name = "cantidad_existente")
	@NotNull
	private int cantidadExistente;

	@ManyToOne
	@JoinColumn(name = "id_sucursal")
	private Sucursal sucursal;
        
        @ManyToOne
        @JoinColumn(name = "id_tipo_producto")
        private TipoProducto tipoProducto;
}
