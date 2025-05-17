package adapspand.proyfinal.tren;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/trenes")
public class TrenController {
	
	@Autowired
	private TrenService trenService;
	
	
	@GetMapping
    private ResponseEntity<List<Tren>> findAll(
            Pageable pageable,
            @RequestParam(required = false) Long origenId,
            @RequestParam(required = false) Long destinoId) {

        return trenService.findAll(pageable, origenId, destinoId);
	}
}
