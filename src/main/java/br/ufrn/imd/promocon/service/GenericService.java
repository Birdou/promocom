package br.ufrn.imd.promocon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufrn.imd.promocon.model.GenericEntity;
import br.ufrn.imd.promocon.repository.GenericRepository;

public class GenericService<T extends GenericEntity> {
	
	@Autowired
	protected GenericRepository<T> repository;
	
	public void save(T obj){
		repository.save(obj);
	}
	
	public void remove(T obj) {
		repository.delete(obj);
	}
	
	public void removeById(Long id) {
		repository.deleteById(id);
	}
	
	public List<T> findAll() {
		return repository.findAll();
	}
	
	public Optional<T> findById(Long id) {
		return repository.findById(id);
	}
}
