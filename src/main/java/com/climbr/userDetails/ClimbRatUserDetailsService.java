package com.climbr.userDetails;

import com.climbr.domain.Account;
import com.climbr.repositories.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClimbRatUserDetailsService implements UserDetailsService {

  private final AccountRepository accountRepository;

  public ClimbRatUserDetailsService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Account> currentUser = accountRepository.findByUserName(username);

    Account user = currentUser.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));

    return new ClimbRatUserDetails(user);
  }
}
