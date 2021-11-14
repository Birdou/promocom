package br.ufrn.imd.promocon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.promocon.model.Store;
import br.ufrn.imd.promocon.service.StoreService;

@Controller
@RequestMapping("/loja")
public class StoreController {

	@Autowired
	StoreService storeService;

	@PostMapping("/novo-loja")
	public ModelAndView registerStore(Store store) {
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		store.setOwner(username);

		storeService.save(store);

		return new ModelAndView("redirect:/");
	}
}
