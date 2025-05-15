package adapspand.proyfinal.ruta;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import adapspand.proyfinal.billete.Billete;
import adapspand.proyfinal.billete.BilleteRepository;

@Service
public class RutaService {

	@Autowired
	private RutaRepository rutaRepository;
	@Autowired
    private BilleteRepository billeteRepository;
    
	public ResponseEntity<Ruta> getRutaByBilleteId(@RequestParam Long billeteId) {
        Optional<Billete> billeteOpt = billeteRepository.findById(billeteId);

        if (billeteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ruta ruta = rutaRepository.findByBillete(billeteOpt.get());
        return ResponseEntity.of(Optional.ofNullable(ruta));
    }
	
	public ResponseEntity<Void> createRuta(
            @RequestBody Ruta nuevaRuta, UriComponentsBuilder ucb) {
        Ruta rutaDevuelta = rutaRepository.save(nuevaRuta);
        URI uriBuena = ucb.path("/rutas/{id}").buildAndExpand(rutaDevuelta.getId()).toUri();
        return ResponseEntity.created(uriBuena).build();
    }
}
