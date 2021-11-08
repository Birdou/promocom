package br.ufrn.imd.promocon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.promocon.model.User;

@Repository
public interface UserRepository extends GenericRepository<User> {
	
	@Query(value = "SELECT * FROM users WHERE login = ?1 AND active = true", nativeQuery = true)
	Optional<User> findByLogin(String login);
}
