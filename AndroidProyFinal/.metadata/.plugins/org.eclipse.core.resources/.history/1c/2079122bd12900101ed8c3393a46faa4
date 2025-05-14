package adapspand.proyfinal.billete;

import adapspand.proyfinal.parada.Parada;
import adapspand.proyfinal.ruta.Ruta;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "billete")
public class Billete {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private int precio;
	
	private Parada origen;
	
	private Parada destino;
	
	@OneToOne
	@JoinColumn(name= "ruta")
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
	
	public static void main(String[] args) {
//		SpringApplication.run(Billete.class, args);
	}
}
