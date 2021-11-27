package br.ufrn.imd.promocon.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.promocon.model.Sale;
import br.ufrn.imd.promocon.model.Store;
import br.ufrn.imd.promocon.model.User;
import br.ufrn.imd.promocon.service.SaleService;
import br.ufrn.imd.promocon.service.StoreService;
import br.ufrn.imd.promocon.utils.FileUploadUtils;

@Controller
@RequestMapping("/promocao")
public class SaleController {

	@Autowired
	SaleService saleService;

	@Autowired
	StoreService storeService;

	@GetMapping("/publicar")
	public ModelAndView salePostPage() {

		List<Store> stores = storeService.findAll();

		ModelAndView mv = new ModelAndView("publish_sale");
		mv.addObject("sale", new Sale());
		mv.addObject("stores", stores);

		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView saveSale(Sale sale, @RequestParam("imageFile") MultipartFile multipartFile) {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		sale.setImage(fileName);
		sale.setAuthor(user);

		String uploadDir = "src/main/resources/static/sale-images";

		try {
			FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);

			saleService.save(sale);
		} catch (IOException e) {
			e.printStackTrace();
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
		saleService.removeById(id);

		return new ModelAndView("redirect:/");
	}
}
