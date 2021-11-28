package br.ufrn.imd.promocon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.promocon.model.Sale;
import br.ufrn.imd.promocon.repository.SaleRepository;

@Service
public class SaleService extends GenericService<Sale> {
	
	@Autowired
	SaleRepository saleRepository;
	
	public List<Sale> findByTerm(String term){
		Optional<List<Sale>> opt = saleRepository.findByTerm(term);
		List<Sale> sales = new ArrayList<Sale>();
		
		if(opt.isPresent()) {
			sales = opt.get();
		}
		
		return sales;
	}

}
