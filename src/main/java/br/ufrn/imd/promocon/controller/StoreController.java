package br.ufrn.imd.promocon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.promocon.model.Store;
import br.ufrn.imd.promocon.model.StoreRate;
import br.ufrn.imd.promocon.model.User;
import br.ufrn.imd.promocon.model.exception.DuplicateStoreAddressException;
import br.ufrn.imd.promocon.service.StoreRateService;
import br.ufrn.imd.promocon.service.StoreService;

@Controller
@RequestMapping("/loja")
public class StoreController {

	@Autowired
	StoreService storeService;

	@Autowired
	StoreRateService storeRateService;

	@GetMapping("/cadastro")
	public ModelAndView storeRegisterPage(Store store, Model model) {
		ModelAndView mv = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			mv = new ModelAndView("register_store");
			model.addAttribute("store", store);
		} else {
			mv = new ModelAndView("login");
		}

		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView registerStore(Store store, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			User user = (User) principal;
			store.setOwner(user);
			try {
				storeService.saveStore(store);
			} catch (DuplicateStoreAddressException e) {
				e.printStackTrace();

				model.addAttribute("error", "Já existe uma loja cadastrada neste endereço");
				return storeRegisterPage(store, model);
			}
		} else {
			try {
				throw new ClassNotFoundException();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return new ModelAndView("redirect:/");
	}

	@GetMapping("/visualizar/{id}")
	public ModelAndView showStore(StoreRate rate, Model model, @PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("store");

		Store store = (Store) storeService.findById(id).get();

		model.addAttribute("rate", rate);
		model.addAttribute("store", store);

		return mv;
	}

	@PostMapping("/avaliar/{id}")
	public ModelAndView saveStoreRate(StoreRate rate, @PathVariable("id") Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			User user = (User) principal;
			Store store = (Store) storeService.findById(id).get();

			rate.setAuthor(user);
			rate.setStore(store);

			storeRateService.save(rate);

			return new ModelAndView("redirect:/");
		} else {
			return new ModelAndView("login");
		}
	}
}
