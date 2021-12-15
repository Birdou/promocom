package br.ufrn.imd.promocon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.promocon.model.SaleRate;
import br.ufrn.imd.promocon.repository.SaleRateRepository;

@Service
public class SaleRateService extends GenericService<SaleRate> {

	@Autowired
	SaleRateRepository saleRateRepository;

	public SaleRate findBySaleAndAuthor(Long idSale, Long idAuthor) {
		Optional<SaleRate> opt = saleRateRepository.findBySaleAndAuthor(idSale, idAuthor);
		
		if(opt.isPresent()) {
			return opt.get();
		} else {
			return null;
		}
	}
	
	public void saveSaleRate(SaleRate rate) {
		super.save(rate);
	}
}
