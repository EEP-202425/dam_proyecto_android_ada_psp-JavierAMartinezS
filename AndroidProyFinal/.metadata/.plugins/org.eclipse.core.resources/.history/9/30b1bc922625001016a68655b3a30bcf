package adapspand.proyfinal.tren;

import org.springframework.boot.SpringApplication;

import adapspand.proyfinal.linea.Linea;
import adapspand.proyfinal.ruta.Ruta;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tren")
public class Tren {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Linea lineaCorrespondiente;

	private Ruta rutaCorrespondiente;

	public Tren() {
		super();
	}

	public Tren(Long id, Linea lineaCorrespondiente, Ruta rutaCorrespondiente) {
		super();
		this.id = id;
		this.lineaCorrespondiente = lineaCorrespondiente;
		this.rutaCorrespondiente = rutaCorrespondiente;
	}

	public static void main(String[] args) {
//		SpringApplication.run(Tren.class, args);
	}
}
