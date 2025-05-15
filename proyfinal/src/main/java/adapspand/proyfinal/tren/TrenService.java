package adapspand.proyfinal.tren;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import adapspand.proyfinal.parada.Parada;
import adapspand.proyfinal.parada.ParadaRepository;
import adapspand.proyfinal.ruta.Ruta;
import adapspand.proyfinal.ruta.RutaRepository;

@Service
public class TrenService {

	@Autowired
	private TrenRepository trenRepository;
	@Autowired
	private ParadaRepository paradaRepository;
	@Autowired
    private RutaRepository rutaRepository;
	
	public ResponseEntity<List<Tren>> findAll(
            Pageable pageable,
            @RequestParam(required = false) Long origenId,
            @RequestParam(required = false) Long destinoId) {

        Page<Tren> page;
        Sort sort = pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"));

        if (origenId != null && destinoId != null) {
            Parada origen = paradaRepository.findById(origenId).orElse(null);
            Parada destino = paradaRepository.findById(destinoId).orElse(null);

            if (origen != null && destino != null) {
                Ruta ruta = rutaRepository.findByOrigenAndDestino(origen, destino);

                if (ruta != null) {
                    page = trenRepository.findByRutaCorrespondiente(ruta, PageRequest.of(
                            pageable.getPageNumber(),
                            pageable.getPageSize(),
                            sort));
                } else {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            page = trenRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
        }
        return ResponseEntity.ok(page.getContent());
    }
}
