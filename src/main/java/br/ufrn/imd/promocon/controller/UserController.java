package br.ufrn.imd.promocon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.promocon.enums.EnumRoles;
import br.ufrn.imd.promocon.model.User;
import br.ufrn.imd.promocon.service.UserService;

@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/cadastro")
	public ModelAndView registerPage() {
		ModelAndView mv = new ModelAndView("register");
		mv.addObject("user", new User());

		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView registerUser(User user) {
		user.setRole(EnumRoles.ADMIN);
		userService.save(user);

		return new ModelAndView("login");
	}
}