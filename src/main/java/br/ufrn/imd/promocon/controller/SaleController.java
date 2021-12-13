package br.ufrn.imd.promocon.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.promocon.enums.EnumCategories;
import br.ufrn.imd.promocon.model.Sale;
import br.ufrn.imd.promocon.model.SaleRate;
import br.ufrn.imd.promocon.model.Store;
import br.ufrn.imd.promocon.model.User;
import br.ufrn.imd.promocon.model.exception.InvalidDiscountException;
import br.ufrn.imd.promocon.service.SaleRateService;
import br.ufrn.imd.promocon.service.SaleService;
import br.ufrn.imd.promocon.service.StoreService;
import br.ufrn.imd.promocon.utils.FileUploadUtils;

@Controller
@RequestMapping("/promocao")
public class SaleController {

	@Autowired
	SaleService saleService;

	@Autowired
	SaleRateService saleRateService;

	@Autowired
	StoreService storeService;

	@GetMapping("/publicar")
	public ModelAndView salePostPage(Sale sale, Model model) {

		List<Store> stores = storeService.findAll();

		ModelAndView mv = new ModelAndView("publish_sale");
		model.addAttribute("sale", sale);
		model.addAttribute("stores", stores);
		model.addAttribute("categories", EnumCategories.values());

		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView saveSale(Sale sale, @RequestParam("imageFile") MultipartFile multipartFile, Model model) {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		sale.setImage(fileName);
		sale.setAuthor(user);

		String uploadDir = "src/main/resources/static/sale-images";

		try {
			FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
			if (sale.getStore().getOwner().getUsername().equals(user.getUsername())) {
				sale.setVerified(true);
			} else {
				sale.setVerified(false);
			}
			saleService.saveSale(sale);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidDiscountException e) {
			e.printStackTrace();

			model.addAttribute("error", "Desconto inválido!");

			return salePostPage(sale, model);
		}

		return new ModelAndView("redirect:/");
	}

	@GetMapping("/visualizar/{id}")
	public ModelAndView showSale(@PathVariable("id") Long id) {
		Sale sale = (Sale) saleService.findById(id).get();

		ModelAndView mv = new ModelAndView("sale");
		mv.addObject("sale", sale);

		return mv;
	}

	@GetMapping("/excluir/{id}")
	public ModelAndView removeSale(@PathVariable("id") Long id) {
		Sale sale = saleService.findById(id).get();

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			User user = (User) principal;
			if (sale.getAuthor().getUsername().equals(user.getUsername()) ||
					sale.getStore().getOwner().getUsername().equals(user.getUsername())) {
				saleService.remove(sale);
			}
		}

		return new ModelAndView("redirect:/");
	}

	@GetMapping("/avaliar/{id}")
	public ModelAndView rateSale(SaleRate rate, Model model, @PathVariable("id") Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof User)) {
			return new ModelAndView("login");
		}

		Sale sale = (Sale) saleService.findById(id).get();
		model.addAttribute("sale", sale);
		model.addAttribute("rate", rate);

		return new ModelAndView("rate_sale");
	}

	@PostMapping("/avaliar/{id}")
	public ModelAndView saveSaleRate(SaleRate rate, @PathVariable("id") Long id) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Sale sale = (Sale) saleService.findById(id).get();

		rate.setAuthor(user);
		rate.setSale(sale);

		saleRateService.save(rate);

		return new ModelAndView("redirect:/");
	}
}
