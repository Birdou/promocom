package br.ufrn.imd.promocon.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import br.ufrn.imd.promocon.model.GenericEntity;

@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity> extends JpaRepository<T, Long> {
	
	@Override
	@Query(value = "select e from #{#entityName} e where e.active = true")
	List<T> findAll();
	
	@Override
	@Query(value = "select e from #{#entityName} e where e.id = ?1 and e.active = true")
	Optional<T> findById(Long id);
	
	@Override
	@Transactional
	default void deleteById(Long long1) {
		Optional<T> entity = findById(long1);
		entity.get().setActive(false);
		save(entity.get());
	}

	@Override
	@Transactional
	default void delete(T obj) {
		obj.setActive(false);
		save(obj);
	}
}
