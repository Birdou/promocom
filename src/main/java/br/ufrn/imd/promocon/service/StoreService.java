package br.ufrn.imd.promocon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.promocon.model.Address;
import br.ufrn.imd.promocon.model.Store;
import br.ufrn.imd.promocon.model.exception.DuplicateStoreAddressException;
import br.ufrn.imd.promocon.repository.StoreRepository;

@Service
public class StoreService extends GenericService<Store> {
	@Autowired
	StoreRepository storeRepository;

	@Autowired
	AddressService addressService;
	
	public Store findByAddress(Address address) {
		Optional<Store> opt = storeRepository.findByAddress(address.getZipcode(), address.getNumber(), address.getCity(), address.getStateCode());
		
		if(opt.isPresent()) {
			return opt.get();
		} else {
			return null;
		}
	}
	
	public void saveStore(Store store) throws DuplicateStoreAddressException {
		Store duplicate = findByAddress(store.getAddress());
		
		if(duplicate != null) {
			throw new DuplicateStoreAddressException("There's already a store registered in this address!");
		}
		
		if(store.getAddress().getId() == null) {
			addressService.setLatLongByAddress(store.getAddress());
		}
		
		super.save(store);
	}
}
