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
	
	@Query(value = "SELECT * FROM sales WHERE sales.id_store = ?1 AND sales.active = true", nativeQuery = true)
	Optional<List<Sale>> findByStore(Long storeId);
	
	@Query(value = "SELECT sa.discount, sa. category, sa.data_creation, "
			+ "sa.data_modification, sa.active, sa.id, sa.description, sa.image, sa.original_price, sa.sale_price, sa.title, sa.verified, sa.id_author, sa.id_store, calculate_distance(ad.latitude, ad.longitude, ?1, ?2, 'K') as distance FROM sales sa\r\n"
			+ "INNER JOIN stores st ON sa.id_store = st.id\r\n"
			+ "INNER JOIN addresses ad ON st.id_address = ad.id\r\n "
			+ "WHERE sa.active = true AND st.active = true AND ad.active = true "
			+ "ORDER BY distance ASC ", nativeQuery = true)
	Optional<List<Sale>> findAllOrderedByDistance(double latitude, double longitude);
}
