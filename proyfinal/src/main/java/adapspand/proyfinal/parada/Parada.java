package adapspand.proyfinal.parada;

import java.util.ArrayList;
import java.util.List;
import adapspand.proyfinal.linea.Linea;
import adapspand.proyfinal.ruta.Ruta;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "parada")
public class Parada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Estaciones nombre;

    public boolean esOrigen;

    @OneToMany(mappedBy = "origen")
    private List<Ruta> rutasComoOrigen = new ArrayList<>();

    public boolean esDestino;

    @OneToMany(mappedBy = "destino")
    private List<Ruta> rutasComoDestino = new ArrayList<>();

    public boolean esIntermedio;

    @ManyToMany(mappedBy = "recorrido")
    private List<Ruta> rutas = new ArrayList<>();

    @ManyToMany(mappedBy = "paradas")
    private List<Linea> lineas = new ArrayList<>();

    @ManyToMany(mappedBy = "recorrido")
    private List<Ruta> rutasDondeAparece;

    public Parada() {
        super();
    }

    public Parada(Long id, Estaciones nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }

    public Estaciones getNombre() {
        return nombre;
    }

    public void setNombre(Estaciones estacion) {
        this.nombre = estacion;
    }
}