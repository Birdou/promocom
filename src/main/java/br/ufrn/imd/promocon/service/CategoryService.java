package br.ufrn.imd.promocon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.promocon.model.Category;
import br.ufrn.imd.promocon.repository.CategoryRepository;

@Service
public class CategoryService extends GenericService<Category> {
	
	@Autowired
	CategoryRepository categoryRepository;

}
