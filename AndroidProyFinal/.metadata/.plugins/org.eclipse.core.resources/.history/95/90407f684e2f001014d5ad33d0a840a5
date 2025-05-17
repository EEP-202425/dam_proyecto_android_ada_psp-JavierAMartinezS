package adapspand.proyfinal.ruta;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import adapspand.proyfinal.billete.Billete;
import adapspand.proyfinal.parada.Parada;
import adapspand.proyfinal.tren.Tren;

public interface RutaRepository extends CrudRepository<Ruta, Long>,
	PagingAndSortingRepository<Ruta, Long>{
	
	Ruta findByBillete(Billete billete);
	
	Page<Ruta> findByTrenes(Tren tren, PageRequest pr);
	
	Page<Ruta> findByRecorrido(Parada parada, PageRequest pageRequest);
}
