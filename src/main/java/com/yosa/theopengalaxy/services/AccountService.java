package com.yosa.theopengalaxy.services;

import com.yosa.theopengalaxy.domain.Account;
import com.yosa.theopengalaxy.domain.Role;
import com.yosa.theopengalaxy.dtos.SortType;
import com.yosa.theopengalaxy.exceptions.NotFoundException;
import com.yosa.theopengalaxy.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account getByUsername(String username){
        return Optional.of(accountRepository.findByUsername(username))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Account getById(Long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));
    }

    public Account create(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Page<Account> search(String query, int page, SortType sortType){
        return accountRepository.findByUsernameContaining(query,
                PageRequest.of(page, 30, Sort.by(Sort.Direction.valueOf(sortType.name()), "username")));
    }

    public Account updateUserPic(String username, MultipartFile file) throws IOException {
        Account account = Optional.of(accountRepository.findByUsername(username))
                .orElseThrow(() -> new NotFoundException("User not found."));
        String filename = UUID.randomUUID().toString() + StringUtils.cleanPath(file.getOriginalFilename());
        String path = "D:\\spring boot apps\\theopengalaxy\\src\\main\\resources\\static\\user_icons\\" + filename;
        file.transferTo(new File(path));
        account.setUserPic("/user_icons/"+filename);
        accountRepository.save(account);
        return account;
    }

    public Account confirmAccount(String emailToken){
        Account account = accountRepository.findByEmailToken(emailToken)
                .orElseThrow(() -> new NotFoundException("User not found."));
        account.setEnabled(true);
        accountRepository.save(account);
        return account;
    }

    public boolean isAccountExist(String username){
        return accountRepository.findByUsername(username) != null;
    }

    public Account getUserByEmailToken(String emailToken){
        return accountRepository.findByEmailToken(emailToken)
                .orElseThrow(() -> new NotFoundException("Token Not Found."));
    }
}
