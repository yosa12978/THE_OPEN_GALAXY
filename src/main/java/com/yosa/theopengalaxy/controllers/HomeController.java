package com.yosa.theopengalaxy.controllers;

import com.yosa.theopengalaxy.domain.Project;
import com.yosa.theopengalaxy.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("")
    public String home(Model model){
        List<Project> projects = projectService.getLastProjects(3);
        model.addAttribute("projects", projects);
        return "home";
    }

    @GetMapping("/privacy")
    public String privacy(Model model){
        return "privacy";
    }
}
