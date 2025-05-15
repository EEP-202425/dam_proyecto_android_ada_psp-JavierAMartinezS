package adapspand.proyfinal.parada;

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

import adapspand.proyfinal.ruta.Ruta;
import adapspand.proyfinal.ruta.RutaRepository;

@RestController
@RequestMapping("/paradas")
public class ParadaController {
	
	@Autowired
	private ParadaService paradaService;
	
	@GetMapping
	public ResponseEntity<List<Parada>> findAll(@RequestParam Long rutaId,Pageable pageable) {
		return paradaService.findAll(rutaId, pageable);
	}
}
