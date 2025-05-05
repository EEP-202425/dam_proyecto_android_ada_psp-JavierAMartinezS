package adapspand.proyfinal.parada;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import adapspand.proyfinal.ruta.Ruta;

public interface ParadaRepository extends CrudRepository<Parada, Long>,
	PagingAndSortingRepository<Parada, Long>{
	
	Page<Parada> buscarPorRuta(Ruta ruta, PageRequest pageRequest);
}
