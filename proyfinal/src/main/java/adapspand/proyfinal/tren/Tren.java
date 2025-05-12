package adapspand.proyfinal.tren;

import adapspand.proyfinal.linea.Linea;
import adapspand.proyfinal.ruta.Ruta;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tren")
public class Tren {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "lineaId")
	private Linea linea;

	@ManyToOne
	@JoinColumn(name = "rutaId")
	private Ruta rutaCorrespondiente;

	public Tren() {
		super();
	}

	public Tren(Long id, Linea lineaCorrespondiente, Ruta rutaCorrespondiente) {
		super();
		this.id = id;
		this.linea = lineaCorrespondiente;
		this.rutaCorrespondiente = rutaCorrespondiente;
	}

}
