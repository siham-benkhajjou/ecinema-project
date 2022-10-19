package com.hendisantika.controller;

import com.hendisantika.entity.Personne;
import com.hendisantika.service.NationaliteService;
import com.hendisantika.service.PersonneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
		model.addAttribute("peronne", new Personne());
		model.addAttribute("listNationalites", natService.getListAll());
		
		return "personne/form";
	}
}
