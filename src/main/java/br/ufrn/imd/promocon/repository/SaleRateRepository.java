package br.ufrn.imd.promocon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.promocon.model.SaleRate;

@Repository
public interface SaleRateRepository extends GenericRepository<SaleRate> {
	
	@Query(value="SELECT * FROM sale_ratings sr WHERE sr.id_sale = ?1 AND sr.id_author = ?2 AND sr.active = true", nativeQuery = true)
	Optional<SaleRate> findBySaleAndAuthor(Long saleId, Long authorId);

}
