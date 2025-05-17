package adapspand.proyfinal.ruta;

import java.util.List;

import org.springframework.boot.SpringApplication;

import adapspand.proyfinal.linea.Linea;
import adapspand.proyfinal.parada.Parada;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ruta")
public class Ruta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Parada origen;
	
	private Parada destino;
	
	private List<Parada> recorrido;
	
	private List<Linea> lineasUsadas;
	
	private boolean transbordo = false;

	public Ruta() {
		super();
	}

	public Ruta(Long id, Parada origen, Parada destino, List<Parada> recorrido) {
		super();
		this.id = id;
		this.origen = origen;
		this.destino = destino;
		this.recorrido = recorrido;
	}
	
	public static void main(String[] args) {
//		SpringApplication.run(Ruta.class, args);
	}
}
