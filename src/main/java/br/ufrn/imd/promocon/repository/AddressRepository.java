package br.ufrn.imd.promocon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.promocon.model.Address;

@Repository
public interface AddressRepository extends GenericRepository<Address> {
	
	@Query(value = "SELECT * FROM addresses ad "
			+ "WHERE ad.zipcode = ?1 "
			+ "AND ad.number = ?2 "
			+ "AND ad.city = ?3 "
			+ "AND ad.state_code = ?4 "
			+ "AND ad.active = true ", nativeQuery = true)
	public Optional<Address> findByZipcodeNumberCityAndState(String zipcode, String number, String city, String uf);
}
