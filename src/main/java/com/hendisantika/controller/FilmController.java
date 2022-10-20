package com.hendisantika.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hendisantika.entity.Film;
import com.hendisantika.entity.Media;
import com.hendisantika.entity.Personne;
import com.hendisantika.entity.Personne.TypePersonne;
import com.hendisantika.service.FilmService;
import com.hendisantika.service.GenreService;
import com.hendisantika.service.MediaService;
import com.hendisantika.service.NationaliteService;
import com.hendisantika.service.PersonneService;
import com.hendisantika.util.AddActors;
import com.hendisantika.util.FileUploadUtil;

@Controller
@RequestMapping("film")
public class FilmController {

    private FilmService filmService;
    private GenreService genreService;
    private PersonneService personneService;
    private NationaliteService natService;
    private final String UPLOAD_DIR = "/src/main/resources/static/photos/films/";

    @Autowired
    public void setFilmService(FilmService filmService) {
        this.filmService = filmService;
    }
    

    @Autowired
    public void setNatService(NationaliteService natService) {
        this.natService = natService;
    }
    
    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
    
    @Autowired
    public void setPersonneService(PersonneService personneService) {
        this.personneService = personneService;
    }

    @GetMapping
    public String index() {
        return "redirect:/film/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {
        Page<Film> page = filmService.getList(pageNumber);
        
        
        System.out.println("Taille de la page : ");
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("listFilms", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "film/list";

    }

    @GetMapping("/add")
    public String add(Model model) {
    	
    	
    	List<Personne> listActeurs = new ArrayList<Personne>();
    	List<Personne> listRealisateurs = new ArrayList<Personne>();
    	listActeurs = personneService.getActeurs();
    	listRealisateurs =personneService.getDirector();
    	
        model.addAttribute("listActeurs", listActeurs);
        model.addAttribute("listRealisateurs", listRealisateurs);
        model.addAttribute("film", new Film());
        model.addAttribute("listeNationalites", natService.getListAll());
        model.addAttribute("listeGenres", genreService.getListAll());
        return "film/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
    	
    	List<Personne> listActeurs = new ArrayList<Personne>();
    	List<Personne> listRealisateurs = new ArrayList<Personne>();
    	listActeurs = personneService.getActeurs();
    	listRealisateurs =personneService.getDirector();
        model.addAttribute("film", filmService.get(id));
        model.addAttribute("listeGenres", genreService.getListAll());
        model.addAttribute("listActeurs", listActeurs);
        model.addAttribute("listRealisateurs", listRealisateurs);
        model.addAttribute("listeNationalites", natService.getListAll());
        return "film/form";

    }

    @PostMapping(value = "/save")
    public String save(@RequestParam("file") MultipartFile file,Film film, @RequestParam("acteur") String acteur,final RedirectAttributes ra) {
    	//check if is there a file
    	if (!file.isEmpty()) {
    		// normalize the file path
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
         // save the file on the local file system
            try {
            	String uuid = UUID.randomUUID().toString();
            	String uploadDir = UPLOAD_DIR;
            	FileUploadUtil.saveFile(uploadDir, uuid+fileName, file);
            	film.setCover("/photos/covers/"+uuid+fileName);
            	List<Personne> acteurs = AddActors.stringToPersonne(acteur, personneService);
            	film.setActeurs(acteurs);
            	filmService.save(film);
            } catch (IOException e) {
            	System.out.println("#####\nUpload Error:\n"+e);
                e.printStackTrace();
            }
    	}
    	
    	
    	Film save = filmService.save(film);
        ra.addFlashAttribute("successFlash", "Film Ajoutée avec succès");
        return "redirect:/film";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

    	filmService.delete(id);
        return "redirect:/film";

    }
    
    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        model.addAttribute("personne", filmService.get(id));
        return "film/details";

    }
    
    @GetMapping("/show/list")
    public String showPersons() {
        return "/film/listNG";
    }
    
    @GetMapping(path="/NG/listp", produces = "application/json")
    public @ResponseBody List<Film> getAllFilms() {
    	List<Film> allFilms = new ArrayList<Film>();
    	allFilms = filmService.getListAll();
    	System.out.println("Size of List allPersons : "+allFilms.size());
        return allFilms;
    }
}
