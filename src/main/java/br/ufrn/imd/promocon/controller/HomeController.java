package br.ufrn.imd.promocon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
