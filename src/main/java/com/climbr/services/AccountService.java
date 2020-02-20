package com.climbr.services;

import com.climbr.domain.Account;
import com.climbr.domain.FollowingFollower;
import com.climbr.domain.FollowingFollowerKey;
import com.climbr.repositories.AccountRepository;
import com.climbr.repositories.FollowingFollowerRepository;
import com.climbr.security.ClimbRatUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

  private final AccountRepository accountRepository;
  private final FollowingFollowerRepository followingFollowerRepository;

  public AccountService(AccountRepository accountRepository, FollowingFollowerRepository followingFollowerRepository) {
    this.accountRepository = accountRepository;
    this.followingFollowerRepository = followingFollowerRepository;
  }

  public List<Account> getAllUsers() {
    return accountRepository.findAll();
  }

  public Account getCurrentUserAccountIfAuthenticated() {
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      ClimbRatUserDetails currentUserDetails = (ClimbRatUserDetails) auth.getPrincipal();
      return currentUserDetails.getCurrentUser();
    } catch (ClassCastException e) {
      return null;
    }
  }

  public Account findByProfileString(String profileString) {
    Optional<Account> optionalAccount = accountRepository.findByProfileString(profileString);
    return optionalAccount.orElseThrow(() -> new UsernameNotFoundException("not found:" + profileString));
  }

  public List<Account> getFollowers(Account user) {
    return followingFollowerRepository.getFollowers(user);
  }

  public List<Account> getFollowing(Account user) {
    return followingFollowerRepository.getFollowing(user);
  }

  public boolean isFollowerOfCurrentUser(Account account) {
    Account currentUser = getCurrentUserAccountIfAuthenticated();
    if (currentUser == null) {
      return false;
    }
    return followingFollowerRepository.isFollowerOfUser(currentUser, account);
  }

  public boolean isCurrentUserFollowing(Account account) {
    Account currentUser = getCurrentUserAccountIfAuthenticated();
    if (currentUser == null) {
      return false;
    }
    return followingFollowerRepository.isFollowerOfUser(account, currentUser);
  }

  @Transactional
  public void deleteFollowerFollowing(Long accountId, Long currentUserId) {
    followingFollowerRepository.deleteFollowerFollowing(accountId, currentUserId);
  }

  public void startFollowing(Long accountId) {
    FollowingFollower followingFollower = new FollowingFollower();
    followingFollower.setFollower(getCurrentUserAccountIfAuthenticated());
    followingFollower.setFollowing(accountRepository.getOne(accountId));
    followingFollower.setId(new FollowingFollowerKey(getCurrentUserAccountIfAuthenticated().getId(), accountId));
    followingFollowerRepository.save(followingFollower);
  }

  @Transactional
  public void updateUserProfile(String newUserName, String newProfileString) {
    Account currentUser = getCurrentUserAccountIfAuthenticated();
    if (!newUserName.isBlank()) {
      currentUser.setUserName(newUserName);
    }
    if (!newProfileString.isBlank()) {
      currentUser.setProfileString(newProfileString);
    }
    accountRepository.save(currentUser);
  }

  public void saveUser(Account user) {
    accountRepository.save(user);
  }
}
