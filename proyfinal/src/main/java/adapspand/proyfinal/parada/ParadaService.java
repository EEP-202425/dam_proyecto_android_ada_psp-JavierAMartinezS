package adapspand.proyfinal.parada;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import adapspand.proyfinal.ruta.Ruta;
import adapspand.proyfinal.ruta.RutaRepository;

@Service
public class ParadaService {

	@Autowired
	private ParadaRepository paradaRepository;
	@Autowired
	private RutaRepository rutaRepository;
	
public ResponseEntity<List<Parada>> findAll(@RequestParam Long rutaId,Pageable pageable) {
		
		Ruta ruta = rutaRepository.findById(rutaId).orElse(null);
	    if (ruta == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    Page<Parada> page = paradaRepository.findByRutas(
                ruta,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "nombre"))
                ));
		return ResponseEntity.ok(page.getContent());
	}
}
