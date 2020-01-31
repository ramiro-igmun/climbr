package com.climbr.services;

import com.climbr.domain.Account;
import com.climbr.repositories.AccountRepository;
import com.climbr.repositories.FollowingFollowerRepository;
import com.climbr.security.ClimbRatUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

  private final AccountRepository accountRepository;
  private final FollowingFollowerRepository followingFollowerRepository;
  private HttpSession httpSession;

  public AccountService(AccountRepository accountRepository, FollowingFollowerRepository followingFollowerRepository, HttpSession httpSession) {
    this.accountRepository = accountRepository;
    this.followingFollowerRepository = followingFollowerRepository;
    this.httpSession = httpSession;
  }

  public Account getCurrentUserAccount() {
    if (httpSession.getAttribute("currentUser") == null) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      ClimbRatUserDetails currentUserDetails = (ClimbRatUserDetails) auth.getPrincipal();
      Account currentUser = currentUserDetails.getCurrentUser();
      httpSession.setAttribute("currentUser", currentUser);
      return currentUser;
    }
    return (Account) httpSession.getAttribute("currentUser");
  }

  public Account findByProfileString(String profileString){
    Optional<Account> optionalAccount = accountRepository.findByProfileString(profileString);
    return optionalAccount.orElseThrow(() -> new UsernameNotFoundException("not found:" + profileString));
  }

  public List<Account> getFollowers(Account user) {
    return followingFollowerRepository.getFollowers(user);
  }

  public List<Account> getFollowing(Account user) {
    return followingFollowerRepository.getFollowing(user);
  }
}
