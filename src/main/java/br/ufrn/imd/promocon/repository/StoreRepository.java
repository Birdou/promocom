package br.ufrn.imd.promocon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.promocon.model.Store;

@Repository
public interface StoreRepository extends GenericRepository<Store> {

	@Query(value = "SELECT * FROM stores WHERE name = ?1 AND active = true", nativeQuery = true)
	Optional<Store> findByName(String name);
}
