package br.ufrn.imd.promocon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.promocon.model.StoreRate;
import br.ufrn.imd.promocon.repository.StoreRateRepository;

@Service
public class StoreRateService extends GenericService<StoreRate> {

    @Autowired
    StoreRateRepository saleRateRepository;

    public void saveStoreRate(StoreRate rate) {
        super.save(rate);
    }
}
