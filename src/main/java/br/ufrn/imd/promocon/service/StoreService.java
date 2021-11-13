package br.ufrn.imd.promocon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.promocon.model.Store;
import br.ufrn.imd.promocon.repository.StoreRepository;

@Service
public class StoreService extends GenericService<Store> {
	@Autowired
	StoreRepository storeRepository;

	@Override
	public void save(Store store) {
		super.save(store);
	}
}
