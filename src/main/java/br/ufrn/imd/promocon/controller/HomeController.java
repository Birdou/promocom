package br.ufrn.imd.promocon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.promocon.model.Sale;
import br.ufrn.imd.promocon.model.Store;
import br.ufrn.imd.promocon.service.SaleService;
import br.ufrn.imd.promocon.service.StoreService;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	StoreService storeService;

	@Autowired
	SaleService saleService;

	@GetMapping("/")
	public ModelAndView homePage() {

		List<Store> stores = storeService.findAll();
		List<Sale> sales = saleService.findAll();

		ModelAndView mv = new ModelAndView("home");

		mv.addObject("stores", stores);
		mv.addObject("sales", sales);

		return mv;
	}
	
	@GetMapping("/busca")
	public ModelAndView filteredHomePage(@RequestParam("searchTerm") String term) {
		if(term == null || term.equals("")) {
			return new ModelAndView("redirect:/");
		}
		
		List<Sale> sales = saleService.findByTerm(term);
		
		ModelAndView mv = new ModelAndView("home");
		
		mv.addObject("sales", sales);
		
		return mv;
	}

	@GetMapping("/login")
	public ModelAndView loginPage() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	
	@GetMapping("/login-erro")
	public ModelAndView loginErrorPage() {
		ModelAndView mv = new ModelAndView("login");
		
		mv.addObject("error", "Login e/ou senha inválidos.");
		
		return mv;
	}
}
