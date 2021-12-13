package br.ufrn.imd.promocon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.promocon.model.SaleRate;
import br.ufrn.imd.promocon.repository.SaleRateRepository;

@Service
public class SaleRateService extends GenericService<SaleRate> {

	@Autowired
	SaleRateRepository saleRateRepository;

	public void saveSaleRate(SaleRate rate) {
		super.save(rate);
	}
}
