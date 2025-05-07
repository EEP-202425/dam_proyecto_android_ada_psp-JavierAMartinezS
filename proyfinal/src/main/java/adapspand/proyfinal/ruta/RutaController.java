package adapspand.proyfinal.ruta;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import adapspand.proyfinal.billete.Billete;
import adapspand.proyfinal.billete.BilleteRepository;

@RestController
@RequestMapping("/rutas")
public class RutaController {
	
	private final RutaRepository rutaRepository;
	private final BilleteRepository billeteRepository;
	
	private RutaController(RutaRepository rr, BilleteRepository br) {
		this.rutaRepository = rr;
		this.billeteRepository = br;
	}
	
	
	@GetMapping
    public ResponseEntity<Ruta> findByBillete(@RequestParam Long billeteId) {
        Optional<Billete> billeteOpt = billeteRepository.findById(billeteId);
        
        if (billeteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Ruta ruta = rutaRepository.buscarPorBillete(billeteOpt.get());
        return Optional.ofNullable(ruta)
                       .map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
