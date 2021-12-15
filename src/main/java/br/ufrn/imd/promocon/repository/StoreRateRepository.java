package br.ufrn.imd.promocon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.promocon.model.StoreRate;

@Repository
public interface StoreRateRepository extends GenericRepository<StoreRate> {
	
	@Query(value="SELECT * FROM store_ratings sr WHERE sr.id_store = ?1 AND sr.id_author = ?2 AND sr.active = true", nativeQuery = true)
	Optional<StoreRate> findByStoreAndAuthor(Long idStore, Long idAuthot);
}
