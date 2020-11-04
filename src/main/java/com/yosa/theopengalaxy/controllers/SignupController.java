package com.yosa.theopengalaxy.controllers;

import com.yosa.theopengalaxy.domain.Account;
import com.yosa.theopengalaxy.domain.Role;
import com.yosa.theopengalaxy.dtos.AccountReadDto;
import com.yosa.theopengalaxy.services.AccountService;
import com.yosa.theopengalaxy.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.Format;
import java.util.Collections;
import java.util.Scanner;
import java.util.UUID;

@Controller
public class SignupController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(AccountReadDto accountDto, Model model){
        if(accountService.isAccountExist(accountDto.username)){
            model.addAttribute("error", "Username is already in use.");
            return "signup";
        }
        Account account = accountService.create(new Account(accountDto.username,
                                        accountDto.password,
                                        Collections.singleton(Role.ROLE_USER),
                                        accountDto.email,
                                        UUID.randomUUID().toString(),
                                        UUID.randomUUID().toString()));
        try{
            File file  = new File("/emailTemplates/confirm.html");
            Scanner scanner = new Scanner(file);
            StringBuilder data = new StringBuilder();
            while(scanner.hasNextLine())
                data.append(scanner.nextLine());
            String finalData = String.format(data.toString(), "THE OPEN GALAXY: Email Confirm", account.getUsername(),
                    "https://localhost:8089/user/confirm/" + account.getEmailToken());
            emailService.sendMail(account.getEmail(), finalData,"THE OPEN GALAXY: Email Confirm");
        }catch (FileNotFoundException | MessagingException e){
            e.printStackTrace();
        }
        return "redirect:/login";
    }
}
