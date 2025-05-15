package adapspand.proyfinal.billete;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BilleteService {
	
	@Autowired
	private BilleteRepository billeteRepository;
	
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
	
	public ResponseEntity<Void> create(
			@RequestBody Billete nuevoBillete, UriComponentsBuilder ucb
			) {
			Billete billeteBueno = new Billete(null, nuevoBillete.getOrigen(),
					nuevoBillete.getDestino());
			Billete billeteDevuelto = billeteRepository.save(billeteBueno);
			URI uriBuena = ucb.path("billetes/{id}").buildAndExpand(billeteDevuelto.getId()).toUri();
			return ResponseEntity.created(uriBuena).build();
	}
}
