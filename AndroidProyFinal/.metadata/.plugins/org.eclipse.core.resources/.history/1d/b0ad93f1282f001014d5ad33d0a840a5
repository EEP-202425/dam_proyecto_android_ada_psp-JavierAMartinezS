package adapspand.proyfinal.linea;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import adapspand.proyfinal.ruta.Ruta;

@RestController
@RequestMapping("/lineas")
public class LineaController {
	
	private final LineaRepository lineaRepository;
	
	private LineaController(LineaRepository lr) {
		this.lineaRepository = lr;
	}
	
	@GetMapping
	private ResponseEntity<List<Linea>> findAll(Pageable pageable, Ruta ruta) {
		Page<Linea> page = lineaRepository.findByRutas(
				ruta.getRuta(ruta.getOrigen(), ruta.getDestino()),
				PageRequest.of(
						pageable.getPageNumber(),
						pageable.getPageSize(),
						pageable.getSortOr(Sort.by(Sort.Direction.ASC, "destino"))
				));
		return ResponseEntity.ok(page.getContent());
	}
}
