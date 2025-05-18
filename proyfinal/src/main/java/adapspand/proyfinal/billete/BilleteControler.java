package adapspand.proyfinal.billete;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/billetes")
public class BilleteControler {
	
	@Autowired
	private BilleteService billeteService;
	
	@GetMapping
	public ResponseEntity<List<Billete>> findAll(Pageable pageable, Principal principal) {
		return billeteService.findAll(pageable, principal);
	}
	
	@PostMapping
	public ResponseEntity<Void> createBillete(
			@RequestBody Billete nuevoBillete, UriComponentsBuilder ucb
			) {
			return billeteService.create(nuevoBillete, ucb);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		billeteService.delete(id);
	}
}