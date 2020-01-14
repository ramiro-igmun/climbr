package com.climbRat.services;

import com.climbRat.repositories.AccountRepository;
import com.climbRat.repositories.WallPostRepository;
import com.climbRat.domain.WallPost;
import com.climbRat.security.ClimbRatUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    return (ClimbRatUserDetails) auth.getPrincipal();
  }

  public List<WallPost> getHomePageWallPosts(){
    Pageable pageable = PageRequest.of(0,25, Sort.by("postDate").descending());
    return wallPostRepository.findCurrentUserHomePageWallPosts(getUserDetails().getCurrentUser(),pageable);
  }

  public void saveWallPost(String message){
    WallPost wallPost = new WallPost();
    wallPost.setMessage(message);
    wallPost.setAuthor(accountRepository.findByUserName(getUserDetails().getUsername()).get());

    wallPostRepository.save(wallPost);
  }


}
