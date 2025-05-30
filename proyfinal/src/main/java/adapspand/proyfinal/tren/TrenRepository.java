package adapspand.proyfinal.tren;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import adapspand.proyfinal.ruta.Ruta;

public interface TrenRepository extends CrudRepository<Tren, Long>,
	PagingAndSortingRepository<Tren, Long>{
	
	Page<Tren> findByRutaCorrespondiente(Ruta ruta, PageRequest pageRequest);

}
