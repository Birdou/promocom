package br.ufrn.imd.promocon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import br.ufrn.imd.promocon.model.Address;
import br.ufrn.imd.promocon.model.User;
import br.ufrn.imd.promocon.model.exception.LoginInUseException;
import br.ufrn.imd.promocon.repository.UserRepository;

@Service
public class UserService extends GenericService<User> {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressService addressService;

	public User findByLogin(String login) {
		Optional<User> opt = userRepository.findByLogin(login);

		if (opt.isPresent()) {
			return opt.get();
		} else {
			return null;
		}
	}

	public void saveUser(User user) throws LoginInUseException{
		Address duplicateAddress = addressService.findByZipcodeNumberCityAndState(user.getAddress());
		
		if(duplicateAddress != null) {
			user.setAddress(duplicateAddress);
		}
		
		User duplicate = findByLogin(user.getLogin());
		
		if((user.getId() == null && duplicate != null) 
				|| (user.getId() != null && duplicate != null && !user.getId().equals(duplicate.getId()))) {
			throw new LoginInUseException("Login already in use!");
		}
		
		if(user.getAddress().getId() == null) {
			addressService.setLatLongByAddress(user.getAddress());
		}
		
		if(user.getId() == null) {
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
		}
			
		super.save(user);
	}
}
