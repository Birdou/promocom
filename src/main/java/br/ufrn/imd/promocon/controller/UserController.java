package br.ufrn.imd.promocon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.promocon.enums.EnumRoles;
import br.ufrn.imd.promocon.model.User;
import br.ufrn.imd.promocon.model.exception.LoginInUseException;
import br.ufrn.imd.promocon.service.UserService;

@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/cadastro")
	public ModelAndView registerPage(User user, Model model) {
		ModelAndView mv = new ModelAndView("register");
		model.addAttribute("user", user);

		return mv;
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