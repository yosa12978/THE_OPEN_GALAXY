package com.yosa.theopengalaxy.controllers;

import com.yosa.theopengalaxy.domain.Project;
import com.yosa.theopengalaxy.domain.Review;
import com.yosa.theopengalaxy.dtos.ProjectReadDto;
import com.yosa.theopengalaxy.dtos.SortType;
import com.yosa.theopengalaxy.services.AccountService;
import com.yosa.theopengalaxy.services.ProjectService;
import com.yosa.theopengalaxy.services.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ReviewService reviewService;

    private static Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @GetMapping("recent")
    public String getRecentProjects(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Project> projects = projectService.getRecentProject(page);
        model.addAttribute("projects", projects);
        model.addAttribute("page", page);
        logger.info("Returning recent posts");
        return "recent";
    }

    @GetMapping("top")
    public String getTopProjects(Model model){
        List<Project> projects = projectService.getTop();
        model.addAttribute("projects", projects);
        logger.info("Returning top projects");
        return "top";
    }

    @GetMapping("search")
    public String searchProjects(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "query", defaultValue = "") String query,
                                 @RequestParam(value = "order", defaultValue = "DESC") SortType sortType){
        if(query.equals(""))
            return "search";
        Page<Project> projects = projectService.search(query, page, sortType);
        model.addAttribute("projects", projects);
        model.addAttribute("page", page);
        logger.info("Searching projects with query = " + query);
        return "search";
    }

    @GetMapping("tags")
    public String searchTagsProjects(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "tag", defaultValue = "") String tag,
                                     @RequestParam(value = "order", defaultValue = "DESC") SortType sortType){
        if(tag.equals(""))
            return "tags";
        Page<Project> projects = projectService.getByTag(tag, page, sortType);
        model.addAttribute("projects", projects);
        model.addAttribute("tag", tag);
        model.addAttribute("page", page);
        model.addAttribute("orderType", sortType.name());
        logger.info("Returning projects by tag = " + tag);
        return "tags";
    }

    @GetMapping("detail/{id}")
    public String getProject(@PathVariable("id") Long id, Model model, @RequestParam(value = "page", defaultValue = "0") int page, Principal principal){
        Project project = projectService.getById(id);
        Page<Review> reviews = reviewService.getReviews(id, page);
        projectService.increaseViews(id);
        boolean hasReview;
        Review review;
        try{
            hasReview = reviewService.getReviewByUsernameAndProject(id, principal.getName()).isPresent();
            review = reviewService.getReviewByUsernameAndProject(id, principal.getName()).get();
        } catch(Exception e) {
            hasReview = false;
            review = null;
        }
        logger.info("Returning project with id = " + id);
        model.addAttribute("proj", project);
        model.addAttribute("reviews", reviews);
        model.addAttribute("hasReview", hasReview);
        model.addAttribute("page", page);
        model.addAttribute("ureview", review);
        return "project";
    }

    @GetMapping("recommended")
    public String getRecommendedProjects(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Project> projects = projectService.getRecommended(page);
        model.addAttribute("projects", projects);
        logger.info("Returning recommended projects");
        return "recommended";
    }

    @GetMapping("create")
    public String create(Model model){
        return "create";
    }

    @PostMapping("create")
    public String createProject(ProjectReadDto projectDto, Model model, Principal principal) throws IllegalStateException, IOException{
        Project newProject = new Project(projectDto.getTitle(), projectDto.getUrl(), accountService.getByUsername(principal.getName()));
        if(projectDto.getDescription() != null)
            newProject.setDescription(projectDto.getDescription());
        if(projectDto.getTags() != null)
            newProject.setTags(Arrays.asList(projectDto.getTags().split(" ")));
        if(projectDto.getImage() != null){
            String filename = UUID.randomUUID().toString() + "." + StringUtils.cleanPath(projectDto.getImage().getOriginalFilename());
            String baseDir = "D:\\spring boot apps\\theopengalaxy\\src\\main\\resources\\static\\previews\\" + filename;
            projectDto.getImage().transferTo(new File(baseDir));
            logger.info("Uploaded new file name = " + filename);
            newProject.setImage("/previews/" + filename);
        }
        newProject.setPubDate(new Date());
        Project createdProject = projectService.createProject(newProject);
        logger.info("Created project with id = " + createdProject.getId());
        return "redirect:/projects/detail/"+createdProject.getId();
    }

    @GetMapping("delete/{id}")
    public String deleteProject(@PathVariable("id") Long id, Model model, Principal principal){
        projectService.deleteProject(id, principal.getName());
        logger.info("Deleted project with id = " + id);
        return "redirect:/user/" + principal.getName();
    }
}
