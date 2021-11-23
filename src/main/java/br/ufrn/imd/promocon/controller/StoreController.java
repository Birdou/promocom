package br.ufrn.imd.promocon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.promocon.model.Store;
import br.ufrn.imd.promocon.model.User;
import br.ufrn.imd.promocon.service.StoreService;

@Controller
@RequestMapping("/loja")
public class StoreController {

	@Autowired
	StoreService storeService;

	@GetMapping("/cadastro")
	public ModelAndView storeRegisterPage() {
		ModelAndView mv = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			User user = (User) principal;

			Store store = new Store();
			store.setOwner(user);

			mv = new ModelAndView("register_store");
			mv.addObject("store", store);
		} else {
			mv = new ModelAndView("login");
		}
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		System.out.println(user.getId());

		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView registerStore(Store store) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			User user = (User) principal;
			store.setOwner(user);
			storeService.save(store);
		} else {
			try {
				throw new ClassNotFoundException();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return new ModelAndView("redirect:/");
	}
}
