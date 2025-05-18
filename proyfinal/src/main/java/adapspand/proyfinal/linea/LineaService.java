package adapspand.proyfinal.linea;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import adapspand.proyfinal.parada.Parada;
import adapspand.proyfinal.parada.ParadaRepository;
import adapspand.proyfinal.ruta.Ruta;
import adapspand.proyfinal.ruta.RutaRepository;


@Service
public class LineaService {

	@Autowired
	private LineaRepository lineaRepository;
	@Autowired
	private ParadaRepository paradaRepository;
	@Autowired
    private RutaRepository rutaRepository;
	
	public ResponseEntity<List<Linea>> findAll(
            @RequestParam(required = false) Long origenId,
            @RequestParam(required = false) Long destinoId) {

        List<Linea> lineas;

        if (origenId != null && destinoId != null) {
            Parada origen = paradaRepository.findById(origenId).orElse(null);
            Parada destino = paradaRepository.findById(destinoId).orElse(null);

            if (origen != null && destino != null) {
                Ruta ruta = rutaRepository.findByOrigenAndDestino(origen, destino);

                if (ruta != null) {
                    lineas = (List<Linea>) lineaRepository.findAll();
                    lineas = lineas.stream()
                            .filter(linea -> linea.getRutas().contains(ruta))
                            .collect(Collectors.toList());
                    if(lineas.isEmpty()){
                         return ResponseEntity.badRequest().build();
                    }
                } else {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            lineas = (List<Linea>) lineaRepository.findAll();
        }
        return ResponseEntity.ok(lineas);
    }
}
