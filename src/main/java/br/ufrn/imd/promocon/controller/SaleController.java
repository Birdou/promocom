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
import br.ufrn.imd.promocon.utils.RandomStringGenerator;

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
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			List<Store> stores = storeService.findAll();

			ModelAndView mv = new ModelAndView("publish_sale");
			model.addAttribute("sale", sale);
			model.addAttribute("stores", stores);
			model.addAttribute("categories", EnumCategories.values());

			return mv;
		} else {
			return new ModelAndView("login");
		}
	}

	@PostMapping("/salvar")
	public ModelAndView saveSale(Sale sale, @RequestParam("imageFile") MultipartFile multipartFile, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String originalFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String fileName = RandomStringGenerator.generate(32);

		int index = originalFileName.lastIndexOf('.');
		if (index != -1) {
			fileName += originalFileName.substring(index);
		}

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
	public ModelAndView showSale(SaleRate rate, Model model, @PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("sale");

		Sale sale = (Sale) saleService.findById(id).get();

		mv.addObject("sale", sale);

		model.addAttribute("sale", sale);
		model.addAttribute("rate", rate);

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

	@PostMapping("/avaliar/{id}")
	public ModelAndView saveSaleRate(SaleRate rate, @PathVariable("id") Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			User user = (User) principal;
			Sale sale = (Sale) saleService.findById(id).get();

			rate.setAuthor(user);
			rate.setSale(sale);

			saleRateService.save(rate);

			return new ModelAndView("redirect:/");
		} else {
			return new ModelAndView("login");
		}
	}
}
