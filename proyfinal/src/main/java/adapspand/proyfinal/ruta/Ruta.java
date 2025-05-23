package adapspand.proyfinal.ruta;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import adapspand.proyfinal.billete.Billete;
import adapspand.proyfinal.linea.Linea;
import adapspand.proyfinal.parada.Estaciones;
import adapspand.proyfinal.parada.Parada;
import adapspand.proyfinal.tren.Tren;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "ruta")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origenId")
    @JsonManagedReference("ruta-origen")
    private Parada origen;

    @ManyToOne
    @JoinColumn(name = "destinoId")
    @JsonManagedReference("ruta-destino")
    private Parada destino;

    @ManyToMany
    @JoinTable(name = "ruta_paradas", joinColumns = @JoinColumn(name = "ruta_id"), inverseJoinColumns = @JoinColumn(name = "parada_id"))
    @OrderColumn(name = "trayecto")
    @JsonManagedReference("ruta-recorrido")
    private List<Parada> recorrido = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "LineasEnRuta", joinColumns = @JoinColumn(name = "rutaIdEnLinea"), inverseJoinColumns = @JoinColumn(name = "lineaIdEnRuta"))
    @JsonBackReference
    private List<Linea> lineasUsadas = new ArrayList<>();

    private boolean transbordo = false;

    @OneToMany(mappedBy = "rutaCorrespondiente")
    @JsonBackReference
    private List<Tren> trenes = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billete_id")
    private Billete billete;

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

    private List<List<Estaciones>> obtenerTodasLasLineas() {
        List<List<Estaciones>> lineas = new ArrayList<>();
       
        lineas.add(Linea.setLineaA());
        lineas.add(Linea.setLineaB());
        lineas.add(Linea.setLineaC());
        lineas.add(Linea.setLineaD());
        lineas.add(Linea.setLineaE());
       
        return lineas;
    }


    public Ruta getRuta(Parada origen, Parada destino) {
        List<Parada> mejorRuta = new ArrayList<>();
        boolean rutaEncontrada = false;

        List<List<Estaciones>> lineas = obtenerTodasLasLineas();

        for (List<Estaciones> linea : lineas) {
            for (int i = 0; i < linea.size(); i++) {
                if (linea.get(i) == origen.getNombre()) {
                    for (int j = i; j < linea.size(); j++) {
                        if (linea.get(j) == destino.getNombre()) {
                            for (int k = i; k <= j; k++) {
                                Parada parada = new Parada();
                                parada.setNombre(linea.get(k));
                               
                                if (k == i) {
                                    parada.esOrigen = true;
                                }
                                if (k == j) {
                                    parada.esDestino = true;
                                }
                                if (k != i && k != j) {
                                    parada.esIntermedio = true;
                                }
                                mejorRuta.add(parada);
                            }
                            rutaEncontrada = true;
                            break;
                        }
                    }
                }
                if (rutaEncontrada)
                    break;
            }
            if (rutaEncontrada)
                break;
        }

        Ruta ruta = new Ruta();
        ruta.origen = origen;
        ruta.destino = destino;
        ruta.recorrido = mejorRuta;

        return ruta;
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

    public List<Parada> getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(List<Parada> recorrido) {
        this.recorrido = recorrido;
    }

    public List<Tren> getTrenes() {
        return trenes;
    }

    public void setTrenes(List<Tren> trenes) {
        this.trenes = trenes;
    }

    public Billete getBillete() {
        return billete;
    }

    public void setBillete(Billete billete) {
        this.billete = billete;
    }

	public List<Linea> getLineasUsadas() {
		return lineasUsadas;
	}

	public void setLineasUsadas(List<Linea> lineasUsadas) {
		this.lineasUsadas = lineasUsadas;
	}

	public boolean isTransbordo() {
		return transbordo;
	}

	public void setTransbordo(boolean transbordo) {
		this.transbordo = transbordo;
	}
}