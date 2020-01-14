package com.climbRat.services;

import com.climbRat.repositories.AccountRepository;
import com.climbRat.repositories.WallPostRepository;
import com.climbRat.domain.WallPost;
import com.climbRat.security.ClimbRatUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

  private AccountRepository accountRepository;
  private WallPostRepository wallPostRepository;

  @Autowired
  public HomeService(AccountRepository accountRepository, WallPostRepository wallPostRepository) {
    this.accountRepository = accountRepository;
    this.wallPostRepository = wallPostRepository;
  }

  public ClimbRatUserDetails getUserDetails(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    ClimbRatUserDetails currentUser = (ClimbRatUserDetails) auth.getPrincipal();
    return currentUser;
  }

  public List<WallPost> getHomePageWallPosts(){
    return wallPostRepository.findByAuthor(getUserDetails().getCurrentUser());
  }

  public void saveWallPost(String message){
    WallPost wallPost = new WallPost();
    wallPost.setMessage(message);
    wallPost.setAuthor(accountRepository.findByUserName(getUserDetails().getUsername()).get());

    wallPostRepository.save(wallPost);
  }


}
