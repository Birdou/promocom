package br.ufrn.imd.promocon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.promocon.model.Sale;

@Repository
public interface SaleRepository extends GenericRepository<Sale> {
	
	@Query(value = "SELECT * FROM sales WHERE lower(sales.title) like(concat('%', ?1, '%')) AND sales.active = true", nativeQuery = true)
	Optional<List<Sale>> findByTerm(String term);
	
	@Query(value = "SELECT * FROM sales WHERE sales.category = ?1 AND sales.active = true", nativeQuery = true)
	Optional<List<Sale>> findByCategory(String category);
	
}
