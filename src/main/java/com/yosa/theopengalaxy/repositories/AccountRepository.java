package com.yosa.theopengalaxy.repositories;

import com.yosa.theopengalaxy.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    Page<Account> findByUsernameContaining(String username, Pageable pageable);
    Optional<Account> findByEmailToken(String emailToken);
}
