package adapspand.proyfinal.linea;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import adapspand.proyfinal.parada.Parada;
import adapspand.proyfinal.parada.ParadaRepository;
import adapspand.proyfinal.ruta.Ruta;
import adapspand.proyfinal.ruta.RutaRepository;

@RestController
@RequestMapping("/lineas")
public class LineaController {
	
	@Autowired
	private LineaService lineaService;
	
	@GetMapping
    private ResponseEntity<List<Linea>> findAll(
    		@RequestParam(required = false) Long origenId,
            @RequestParam(required = false) Long destinoId) {
		return lineaService.findAll(origenId, destinoId);
    }
}
