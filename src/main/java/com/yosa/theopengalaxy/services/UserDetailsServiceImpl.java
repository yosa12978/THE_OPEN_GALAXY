package com.yosa.theopengalaxy.services;

import com.yosa.theopengalaxy.domain.Account;
import com.yosa.theopengalaxy.domain.Role;
import com.yosa.theopengalaxy.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if(account == null)
            throw new UsernameNotFoundException(username);

        List<GrantedAuthority> user_roles = new ArrayList<GrantedAuthority>();
        for(Role i : account.getRoles())
            user_roles.add(new SimpleGrantedAuthority(i.name()));

        return new User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                user_roles);
    }
}
