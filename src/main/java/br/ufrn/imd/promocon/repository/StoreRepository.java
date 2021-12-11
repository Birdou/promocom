package br.ufrn.imd.promocon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.promocon.model.Store;

@Repository
public interface StoreRepository extends GenericRepository<Store> {

	@Query(value = "SELECT * FROM stores WHERE name = ?1 AND active = true", nativeQuery = true)
	Optional<Store> findByName(String name);
	
	@Query(value = "SELECT * FROM stores st "
			+ "INNER JOIN addresses ad ON st.id_address = ad.id "
			+ "WHERE ad.zipcode = ?1 "
			+ "AND ad.number = ?2 "
			+ "AND ad.city = ?3 "
			+ "AND ad.state_code = ?4 "
			+ "AND ad.active = true "
			+ "AND st.active = true", nativeQuery = true)
	Optional<Store> findByAddress(String zipcode, String number, String city, String uf);
}
