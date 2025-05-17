package adapspand.proyfinal.linea;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import adapspand.proyfinal.parada.Parada;
import adapspand.proyfinal.ruta.Ruta;

public interface LineaRepository extends CrudRepository<Linea, Long>,
	PagingAndSortingRepository<Linea, Long>{
	
	Page<Linea> findByParadas(Parada parada, PageRequest pageRequest);
	
	Page<Linea> findByRutas(Ruta ruta, PageRequest pageRequest);
}
