package adapspand.proyfinal.billete;

import adapspand.proyfinal.parada.Parada;
import adapspand.proyfinal.ruta.Ruta;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "billete")
public class Billete {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private int precio;
	
	private String propietario;
	
	@ManyToOne
    @JoinColumn(name = "origen_id")
	private Parada origen;
	
	@ManyToOne
    @JoinColumn(name = "destino_id")
	private Parada destino;
	
	@OneToOne(mappedBy = "billete") 
	private Ruta ruta;
	
	public Billete() {
		super();
	}

	public Billete(Long id, Parada origen, Parada destino) {
		super();
		this.id = id;
		this.origen = origen;
		this.destino = destino;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Parada getOrigen() {
		return origen;
	}

	public void setOrigen(Parada origen) {
		this.origen = origen;
	}

	public Parada getDestino() {
		return destino;
	}

	public void setDestino(Parada destino) {
		this.destino = destino;
	}
	
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}
	
	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}
	
}
