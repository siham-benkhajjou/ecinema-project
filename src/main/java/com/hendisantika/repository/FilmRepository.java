package com.hendisantika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hendisantika.dto.InlineFilm;
import com.hendisantika.entity.Film;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;




@CrossOrigin("http://localhost:4200")
@Repository
@RepositoryRestResource(excerptProjection = InlineFilm.class)
public interface FilmRepository extends JpaRepository<Film, Long> {

}

