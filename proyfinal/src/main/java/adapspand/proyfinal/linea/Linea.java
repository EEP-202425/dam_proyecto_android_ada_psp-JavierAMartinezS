package adapspand.proyfinal.linea;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import adapspand.proyfinal.parada.Estaciones;
import adapspand.proyfinal.parada.Parada;
import adapspand.proyfinal.ruta.Ruta;
import adapspand.proyfinal.tren.Tren;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "linea")
public class Linea {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nombre;
	
	@OneToMany(mappedBy = "linea")
	@JsonManagedReference
	private List<Tren> trenesCorrespondientes = new ArrayList<>();
	
	@ManyToMany
    @JoinTable(
        name = "lineasParada",
        joinColumns = @JoinColumn(name = "lineasCorrespondientes"),
        inverseJoinColumns = @JoinColumn(name = "paradasCorrespondientes")
    )
    private List<Parada> paradas = new ArrayList<>();
	
	@ManyToMany(mappedBy = "lineasUsadas")
	private List<Ruta> rutas = new ArrayList<>();
	
	public Linea() {
		super();
	}
	
	public Linea(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	

	public static List<Estaciones> setLineaA() {
		List<Estaciones> estaciones = new ArrayList<>();
	    estaciones.add(Estaciones.HELSINKI);
	    estaciones.add(Estaciones.TALLIN);
	    estaciones.add(Estaciones.RIGA);
	    estaciones.add(Estaciones.VILNA);
	    estaciones.add(Estaciones.VARSOVIA);
	    estaciones.add(Estaciones.BERLIN);
	    estaciones.add(Estaciones.PRAGA);
	    estaciones.add(Estaciones.VIENA);
	    estaciones.add(Estaciones.MUNICH);
	    estaciones.add(Estaciones.ZURICH);
	    estaciones.add(Estaciones.MILAN);
	    estaciones.add(Estaciones.ROMA);
	    estaciones.add(Estaciones.NAPOLES);
	    return estaciones;
	}
	
	public static List<Estaciones> setLineaB() {
		List<Estaciones> estaciones = new ArrayList<>();
	    estaciones.add(Estaciones.LISBOA);
	    estaciones.add(Estaciones.MADRID);
	    estaciones.add(Estaciones.BURDEOS);
	    estaciones.add(Estaciones.LYON);
	    estaciones.add(Estaciones.MILAN);
	    estaciones.add(Estaciones.ROMA);
	    estaciones.add(Estaciones.ZAGREB);
	    estaciones.add(Estaciones.SARAJEVO);
	    estaciones.add(Estaciones.TIRANA);
	    estaciones.add(Estaciones.ATENAS);
	    estaciones.add(Estaciones.SOFIA);
	    estaciones.add(Estaciones.BUCAREST);
	    estaciones.add(Estaciones.CHISINAU);
	    estaciones.add(Estaciones.KIEV);
	    return estaciones;
	}
	
	public static List<Estaciones> setLineaC() {
		List<Estaciones> estaciones = new ArrayList<>();
	    estaciones.add(Estaciones.MADRID);
	    estaciones.add(Estaciones.BARCELONA);
	    estaciones.add(Estaciones.MARSELLA);
	    estaciones.add(Estaciones.LYON);
	    estaciones.add(Estaciones.PARIS);
	    estaciones.add(Estaciones.LUXEMBURGO);
	    estaciones.add(Estaciones.FRANKFURT);
	    estaciones.add(Estaciones.VIENA);
	    estaciones.add(Estaciones.BUDAPEST);
	    estaciones.add(Estaciones.BELGRADO);
	    estaciones.add(Estaciones.SOFIA);
	    estaciones.add(Estaciones.ESTAMBUL);
	    return estaciones;
	}
	
	public static List<Estaciones> setLineaD() {
		List<Estaciones> estaciones = new ArrayList<>();
	    estaciones.add(Estaciones.GLASGOW);
	    estaciones.add(Estaciones.BELFAST);
	    estaciones.add(Estaciones.DUBLIN);
	    estaciones.add(Estaciones.LIVERPOOL);
	    estaciones.add(Estaciones.LONDRES);
	    estaciones.add(Estaciones.PARIS);
	    estaciones.add(Estaciones.BRUSELAS);
	    estaciones.add(Estaciones.ANTWERP);
	    estaciones.add(Estaciones.AMSTERDAM);
	    estaciones.add(Estaciones.BERLIN);
	    estaciones.add(Estaciones.VARSOVIA);
	    estaciones.add(Estaciones.LVIV);
	    estaciones.add(Estaciones.KIEV);
	    return estaciones;
	}
	
	public static List<Estaciones> setLineaE() {
		List<Estaciones> estaciones = new ArrayList<>();
	    estaciones.add(Estaciones.OSLO);
	    estaciones.add(Estaciones.ESTOCOLMO);
	    estaciones.add(Estaciones.COPENAGUE);
	    estaciones.add(Estaciones.HAMBURGO);
	    estaciones.add(Estaciones.FRANKFURT);
	    estaciones.add(Estaciones.MUNICH);
	    estaciones.add(Estaciones.ZURICH);
	    estaciones.add(Estaciones.MILAN);
	    return estaciones;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Tren> getTrenesCorrespondientes() {
		return trenesCorrespondientes;
	}

	public void setTrenesCorrespondientes(List<Tren> trenesCorrespondientes) {
		this.trenesCorrespondientes = trenesCorrespondientes;
	}

	public List<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(List<Parada> paradas) {
		this.paradas = paradas;
	}

	public List<Ruta> getRutas() {
		return rutas;
	}

	public void setRutas(List<Ruta> rutas) {
		this.rutas = rutas;
	}
}
