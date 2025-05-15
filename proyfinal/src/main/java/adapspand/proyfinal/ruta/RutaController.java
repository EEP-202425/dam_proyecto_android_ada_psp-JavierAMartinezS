package adapspand.proyfinal.ruta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/rutas")
public class RutaController {

	@Autowired
    private RutaService rutaService;

   

    @GetMapping(value = "", params = "billeteId")
    public ResponseEntity<Ruta> getRutaByBilleteId(@RequestParam Long billeteId) {
        return rutaService.getRutaByBilleteId(billeteId);
    }

    @PostMapping
    public ResponseEntity<Void> createRuta(
            @RequestBody Ruta nuevaRuta, UriComponentsBuilder ucb) {
        return rutaService.createRuta(nuevaRuta, ucb);
    }
    
    @DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		rutaService.delete(id);
	}
}
