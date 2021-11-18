package br.ufrn.imd.promocon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.promocon.model.Sale;
import br.ufrn.imd.promocon.repository.SaleRepository;

@Service
public class SaleService extends GenericService<Sale> {
	
	@Autowired
	SaleRepository saleRepository;

}
