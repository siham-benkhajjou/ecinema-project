package com.hendisantika.controller;

import com.hendisantika.entity.Seance;
import com.hendisantika.entity.User;
import com.hendisantika.repository.SeanceRepository;
import com.hendisantika.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-adminlte3
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 25/11/20
 * Time: 08.41
 */
@Controller
public class AdminPageController {
    @Autowired
    private UserRepository userRepository;
    private SeanceRepository seanceRepository;

    @GetMapping({"/user/list", "/admin/user"})
    public String listUser() {
        return "user-list";
    }

    @GetMapping("/user/list2")
    public String listUser2(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-list2";
    }

    @GetMapping("/user/add")
    public String showFormUser(Model model) {
        model.addAttribute("user", new User());
        return "user-add";
    }

    @PostMapping("/user/add")
    public String addUser(Model model, User user) {
        userRepository.save(user);
        model.addAttribute("user", new User());
        return "user-list";
    }
	/*
	 * @GetMapping("/seance/add") public String showFormseance(Model model) {
	 * model.addAttribute("seance", new Seance()); return "seance-add"; }
	 * 
	 * @PostMapping("/seance/add") public String addSeance(Model model, Seance
	 * seance) { seanceRepository.save(seance); model.addAttribute("seance", new
	 * Seance()); return "seance-list"; }
	 */
}
