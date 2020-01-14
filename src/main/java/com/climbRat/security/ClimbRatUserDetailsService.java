package com.climbRat.security;

import com.climbRat.domain.Account;
import com.climbRat.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClimbRatUserDetailsService implements UserDetailsService {

  @Autowired
  AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Account> currentUser = accountRepository.findByUserName(username);

    Account user = currentUser.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));

    return new ClimbRatUserDetails(user);
  }
}
