package com.hendisantika.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import com.hendisantika.entity.Salle;
import com.hendisantika.entity.Seance;

@CrossOrigin("http://localhost:4200")
@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {
	
}
