package br.ufrn.imd.promocon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.ufrn.imd.promocon.dto.DataDto;
import br.ufrn.imd.promocon.model.Address;
import br.ufrn.imd.promocon.repository.AddressRepository;

@Service
public class AddressService extends GenericService<Address> {
	
	@Autowired
	AddressRepository addressRepository;
	
	@Value("${spring.application.healthUnit.api.url}")
	private String baseUrl;
	
	@Value("${spring.application.healthUnit.api.token}")
	private String token;
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	public Address findByZipcodeNumberCityAndState(Address ad) {
		Optional<Address> opt = addressRepository.findByZipcodeNumberCityAndState(ad.getZipcode(), ad.getNumber(), ad.getCity(), ad.getStateCode());
		
		if(opt.isPresent()) {
			return opt.get();
		}else {
			return null;
		}
	}
		
	public Address setLatLongByAddress(Address address) {
		String query = address.toString().replace(" ", "+");
		String finalUrl = baseUrl + query + ".json?access_token=" + token;
		DataDto responseDto = restTemplate.getForObject(finalUrl, DataDto.class); 
		if (responseDto.getFeatures().length > 0) {
			address.setLatitude(responseDto.getFeatures()[0].getGeometry().getCoordinates()[1]);
			address.setLongitude(responseDto.getFeatures()[0].getGeometry().getCoordinates()[0]);	
		}
		return address;
	}
}
