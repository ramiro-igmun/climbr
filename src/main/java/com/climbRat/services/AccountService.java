package com.climbRat.services;

import com.climbRat.domain.Account;
import com.climbRat.domain.FollowingFollower;
import com.climbRat.repositories.AccountRepository;
import com.climbRat.repositories.FollowingFollowerRepository;
import com.climbRat.security.ClimbRatUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

  private final AccountRepository accountRepository;
  private FollowingFollowerRepository followingFollowerRepository;

  public AccountService(AccountRepository accountRepository, FollowingFollowerRepository followingFollowerRepository) {
    this.accountRepository = accountRepository;
    this.followingFollowerRepository = followingFollowerRepository;
  }


  public Account getCurrentUserAccount(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    ClimbRatUserDetails currentUserDetails = (ClimbRatUserDetails) auth.getPrincipal();
    return currentUserDetails.getCurrentUser();

  }

  public List<Account> getFollowers(Account user) {
    return followingFollowerRepository.getFollowers(user);
  }

  public List<Account> getFollowing(Account user){
    return followingFollowerRepository.getFollowing(user);
  }
}
