package adapspand.proyfinal.billete;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BilleteRepository extends CrudRepository<Billete, Long>,
	PagingAndSortingRepository<Billete, Long>{
	
	Page<Billete> findByPropietario(String propietario, PageRequest pageRequest);
}
