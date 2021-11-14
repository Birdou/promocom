package br.ufrn.imd.promocon.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.promocon.model.Store;
import br.ufrn.imd.promocon.model.User;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("/")
	public ModelAndView homePage() {
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}

	@GetMapping("/login")
	public ModelAndView loginPage() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}

	@GetMapping("/cadastro")
	public ModelAndView registerPage() {
		ModelAndView mv = new ModelAndView("register");
		mv.addObject("user", new User());

		return mv;
	}

	@GetMapping("/cadastro-loja")
	public ModelAndView storeRegisterPage() {
		ModelAndView mv = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();

			Store store = new Store();
			store.setOwner(username);

			mv = new ModelAndView("register_store");
			mv.addObject("store", store);
		} else {
			mv = new ModelAndView("login");
		}

		return mv;
	}
}
