package br.ufrn.imd.promocon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

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
		User duplicate = findByLogin(user.getLogin());
		
		if(duplicate != null) {
			throw new LoginInUseException("Login already in use!");
		}
		
		if(user.getAddress().getId() == null) {
			addressService.setLatLongByAddress(user.getAddress());
		}
		
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));

		super.save(user);
	}
}
