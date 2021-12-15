package br.ufrn.imd.promocon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.promocon.model.StoreRate;
import br.ufrn.imd.promocon.repository.StoreRateRepository;

@Service
public class StoreRateService extends GenericService<StoreRate> {

    @Autowired
    StoreRateRepository saleRateRepository;
    
    public StoreRate findByStoreAndAuthor(Long idStore, Long idAuthor) {
    	Optional<StoreRate> opt = saleRateRepository.findByStoreAndAuthor(idStore, idAuthor);
    	
    	if(opt.isPresent()) {
    		return opt.get();
    	} else {
    		return null;
    	}
    }

    public void saveStoreRate(StoreRate rate) {
        super.save(rate);
    }
}
