package br.ufrn.imd.promocon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.promocon.enums.EnumRoles;
import br.ufrn.imd.promocon.model.Address;
import br.ufrn.imd.promocon.model.User;
import br.ufrn.imd.promocon.model.exception.LoginInUseException;
import br.ufrn.imd.promocon.service.AddressService;
import br.ufrn.imd.promocon.service.UserService;

@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;
	
	@GetMapping("/cadastro")
	public ModelAndView registerPage(User user, Model model) {
		ModelAndView mv = new ModelAndView("register");
		model.addAttribute("user", user);

		return mv;
	}
	
	@GetMapping("/alterar-endereco")
	public ModelAndView changeAddressView(Address address, Model model) {
		ModelAndView mv = new ModelAndView("change_address");
		
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findById(principal.getId()).get();
		
		address = user.getAddress();
		address.setId(null);
		
		model.addAttribute("address", address);
		
		return mv;
	}
	
	@PostMapping("/alterar-endereco")
	public ModelAndView changeAddress(Address address, Model model) {
		
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findById(principal.getId()).get();
	
		user.setAddress(address);
		
		try {
			userService.saveUser(user);
		} catch (LoginInUseException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/");
	}

	@PostMapping("/salvar")
	public ModelAndView registerUser(User user, Model model) {
		user.setRole(EnumRoles.ADMIN);
		
		try {
			userService.saveUser(user);
		} catch (LoginInUseException e) {
			e.printStackTrace();
			
			model.addAttribute("error", "Este login já está em uso!");
			
			return registerPage(user, model);
		}

		return new ModelAndView("redirect:/login");
	}
}