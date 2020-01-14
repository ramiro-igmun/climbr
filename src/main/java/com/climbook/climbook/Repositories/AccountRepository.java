package com.climbook.climbook.Repositories;

import com.climbook.climbook.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByUserName(String userName);
}
