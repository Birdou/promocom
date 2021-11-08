package br.ufrn.imd.promocon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.ufrn.imd.promocon.model.User;

@Component
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = (User) userService.findByLogin(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return user;	
	}

}
