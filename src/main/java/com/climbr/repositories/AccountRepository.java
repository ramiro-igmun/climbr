package com.climbr.repositories;

import com.climbr.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByUserName(String userName);

  Optional<Account> findByProfileString(String profileString);

}
