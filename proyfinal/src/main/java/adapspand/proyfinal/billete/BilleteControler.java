package adapspand.proyfinal.billete;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import adapspand.proyfinal.ruta.Ruta;

@RestController
@RequestMapping("/billetes")
public class BilleteControler {
	
	private final BilleteRepository billeteRepository;
	
	public BilleteControler( BilleteRepository br) {
		this.billeteRepository = br;
	}
	
	@GetMapping
	public ResponseEntity<List<Billete>> findAll(Pageable pageable, Principal principal) {
		Page<Billete> page = billeteRepository.findByPropietario(
				principal.getName(),
				PageRequest.of(
						pageable.getPageNumber(),
						pageable.getPageSize(),
						pageable.getSortOr(Sort.by(Sort.Direction.ASC, "precio"))
				));
		return ResponseEntity.ok(page.getContent());
	}
	
	@PostMapping
	public ResponseEntity<Void> createBillete(
			@RequestBody Billete nuevoBillete, UriComponentsBuilder ucb
			) {
			Billete billeteBueno = new Billete(null, nuevoBillete.getOrigen(),
					nuevoBillete.getDestino());
			Billete billeteDevuelto = billeteRepository.save(billeteBueno);
			URI uriBuena = ucb.path("billetes/{id}").buildAndExpand(billeteDevuelto.getId()).toUri();
			return ResponseEntity.created(uriBuena).build();
	}
}

//@PostMapping
//public ResponseEntity<Void> createBillete(
//        @RequestBody Billete nuevoBillete,
//        UriComponentsBuilder ucb) {
//
//    // Si el billete tiene una Ruta asociada, se toma su origen/destino
//    if (nuevoBillete.getRuta() != null) {
//        nuevoBillete.setOrigen(nuevoBillete.getRuta().getOrigen());
//        nuevoBillete.setDestino(nuevoBillete.getRuta().getDestino());
//    }
//
//    Billete billeteGuardado = billeteRepository.save(nuevoBillete);
//    URI uri = ucb.path("/billetes/{id}").buildAndExpand(billeteGuardado.getId()).toUri();
//    return ResponseEntity.created(uri).build();
//}