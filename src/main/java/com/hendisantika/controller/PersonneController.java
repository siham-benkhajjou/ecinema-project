package com.hendisantika.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.hendisantika.entity.Nationalite;
import com.hendisantika.entity.Personne;
import com.hendisantika.service.NationaliteService;
import com.hendisantika.service.PersonneService;
import com.hendisantika.util.FileUploadUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("personne")
public class PersonneController {

	private PersonneService personneService;
	private NationaliteService natService;
	private final String UPLOAD_DIR = "/src/main/resources/static/photos/personnes/";

	@Autowired
	public void setPersonneService(PersonneService personneService) {
		this.personneService = personneService;
	}

	@Autowired
	public void setNatService(NationaliteService natService) {
		this.natService = natService;
	}

	@GetMapping
	public String index() {
		return "redirect:/personne/1";
	}

	@GetMapping(value = "/{pageNumber}")
	public String list(@PathVariable Integer pageNumber, Model model) {
		Page<Personne> page = personneService.getList(pageNumber);
		System.out.println("Taille de la page : ");

		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());

		model.addAttribute("listPersonnes", page);
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);

		return "personne/list";
	}

	@GetMapping(value = "/add")
	public String add(Model model) {
		model.addAttribute("personne", new Personne());
		model.addAttribute("listeNationalites", natService.getListAll());

		return "personne/form";
	}

	@PostMapping(value = "/save")
	public String save(@RequestParam("file") MultipartFile file, Personne personne, final RedirectAttributes ra) {

		if (!file.isEmpty()) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			try {
				String uuid = UUID.randomUUID().toString();
				String uploadDir = UPLOAD_DIR;
				FileUploadUtil.saveFile(uploadDir, uuid + fileName, file);
				personne.setPhoto("/photos/personnes/" + uuid + fileName);
			} catch (IOException e) {
				System.out.println("#####\nUpload Error:\n" + e);
				e.printStackTrace();
			}
		}

		Personne savedPersonne = personneService.save(personne);
		ra.addFlashAttribute("successFlash", "Personne Ajoutée avec succès");

		return "redirect:/personne";
	}
	
	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		
		Personne personne = personneService.get(id);
		List<Nationalite> natList = natService.getListAll();
		
		model.addAttribute("personne", personne);
		model.addAttribute("listeNationalites", natList);
		return "personne/form";
	}
	
	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable Long id) {
		
		personneService.delete(id);
		return "redirect:/personne";
	}
	
	@GetMapping(value = "/details/{id}")
	public String details(@PathVariable Long id, Model model) {
		Personne personne = personneService.get(id);
		
		model.addAttribute("personne", personne);
		model.addAttribute("listFlims", personne.getFilms());
		
		return "personne/details";
	}
}
