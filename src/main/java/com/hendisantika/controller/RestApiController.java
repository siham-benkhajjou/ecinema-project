package com.hendisantika.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hendisantika.entity.Nationalite;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api")
public class RestApiController {
	
	@GetMapping(path="/nationalites", produces = "application/json")
    public @ResponseBody List<Nationalite> getAllNationalites() {
    	List<Nationalite> allNationalite = new ArrayList<Nationalite>();
    	
    	//System.out.println("Size of List allNationalite : "+allCourses.size());
        return allNationalite;
    }
	
	class Course implements Serializable{
		private static final long serialVersionUID = 1L;
		private String titre;
		private Integer nb_etud;
		public Course(String titre, Integer nb_etud) {
			super();
			this.titre = titre;
			this.nb_etud = nb_etud;
		}
		public String getTitre() {
			return titre;
		}
		public void setTitre(String titre) {
			this.titre = titre;
		}
		public Integer getNb_etud() {
			return nb_etud;
		}
		public void setNb_etud(Integer nb_etud) {
			this.nb_etud = nb_etud;
		}
		@Override
		public String toString() {
			return "Course [titre=" + titre + ", nb_etud=" + nb_etud + "]";
		}
	}
}
