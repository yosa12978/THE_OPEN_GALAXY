package com.yosa.theopengalaxy.controllers;

import com.yosa.theopengalaxy.domain.Account;
import com.yosa.theopengalaxy.domain.Project;
import com.yosa.theopengalaxy.dtos.SortType;
import com.yosa.theopengalaxy.exceptions.ForbiddenException;
import com.yosa.theopengalaxy.services.AccountService;
import com.yosa.theopengalaxy.services.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProjectService projectService;

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("{username}")
    public String getAccount(Model model,
                             @RequestParam(value = "order", defaultValue = "DESC") SortType sortType,
                             @PathVariable("username") String username,
                             @RequestParam(value = "page", defaultValue = "0") int page, Principal principal){
        if(username.equals(""))
            throw new ForbiddenException("You have not permissions.");
        Page<Project> projects = projectService.getByAuthor(username, page, sortType);
        Account account = accountService.getByUsername(username);
        model.addAttribute("projects", projects);
        model.addAttribute("usr", account);
        model.addAttribute("page", page);
        logger.info("Returning projects for user = " + username);
        return "account";
    }

    @GetMapping("users/search")
    public String userSearch(Model model,
                             @RequestParam(value = "query", defaultValue = "") String query,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "order", defaultValue = "DESC") SortType sortType){
        if(query.equals(""))
            return "users";
        Page<Account> users = accountService.search(query, page, sortType);
        model.addAttribute("users", users);
        model.addAttribute("page", page);
        model.addAttribute("orderType", sortType);
        model.addAttribute("userQuery", query);
        logger.info("Searching users with username = " + query);
        return "users";
    }

    @GetMapping("/account/settings")
    public String accountSettings(Model model){
        return "settings";
    }

    @PostMapping("/account/userpic")
    public String updateUserPic(Model model, @RequestParam("icon") MultipartFile file, Principal principal) throws IOException {
        if(file != null)
            accountService.updateUserPic(principal.getName(), file);
        return "redirect:/account/settings";
    }

    @GetMapping("/confirm/{email_token}")
    public String confirmEmail(Model model, @PathVariable("email_token") String emailToken){
        accountService.confirmAccount(emailToken);
        return "confirm";
    }

    @GetMapping("/change/password/{email_token}")
    public String changePassword(Model model, @PathVariable("email_token") String emailToken){
        Account account = accountService.getUserByEmailToken(emailToken);
        return "changePassword";
    }

    @PostMapping("/change/password/{email_token}")
    public String changePassword(Model model,
                                 @PathVariable("email_token") String emailToken,
                                 @RequestParam("password") String password){
        return "redirect:/login";
    }
}
