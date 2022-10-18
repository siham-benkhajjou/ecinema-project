package com.hendisantika.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import com.hendisantika.entity.Nationalite;
import com.hendisantika.entity.Salle;

@CrossOrigin("http://localhost:4200")
@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {
	Page<Salle> findByNumeroStartsWith(@RequestParam("numero")  int numero, Pageable pageable);
}
