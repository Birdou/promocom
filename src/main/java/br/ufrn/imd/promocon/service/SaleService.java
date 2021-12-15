package br.ufrn.imd.promocon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.promocon.model.Sale;
import br.ufrn.imd.promocon.model.exception.InvalidDiscountException;
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
	
	public List<Sale> findByCategory(String category){
		Optional<List<Sale>> opt = saleRepository.findByCategory(category);
		List<Sale> sales = new ArrayList<Sale>();
		
		if(opt.isPresent()) {
			sales = opt.get();
		}
		
		return sales;
	}
	
	public List<Sale> findByStore(Long idStore){
		Optional<List<Sale>> opt = saleRepository.findByStore(idStore);
		List<Sale> sales = new ArrayList<Sale>();
		
		if(opt.isPresent()) {
			sales = opt.get();
		}
		
		return sales;
	}
	
	public List<Sale> findAllOrderedByDistance(double latitude, double longitude){
		Optional<List<Sale>> opt = saleRepository.findAllOrderedByDistance(latitude, longitude);
		List<Sale> sales = new ArrayList<Sale>();
		
		if(opt.isPresent()) {
			sales = opt.get();
		}
		
		return sales;
	}
	
	public void saveSale(Sale sale) throws InvalidDiscountException {
		if(sale.getOriginalPrice() <= sale.getSalePrice()) {
			throw new InvalidDiscountException("The given discount is invalid!");
		}
		
		sale.setDiscount((sale.getOriginalPrice() - sale.getSalePrice()) / sale.getOriginalPrice());
		
		super.save(sale);
	}
}
