package com.climbook.climbook.Services;

import com.climbook.climbook.Repositories.AccountRepository;
import com.climbook.climbook.Repositories.WallPostRepository;
import com.climbook.climbook.domain.Account;
import com.climbook.climbook.domain.WallPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

  private AccountRepository accountRepository;
  private WallPostRepository wallPostRepository;
  private Account currentUserAccount;

  @Autowired
  public HomeService(AccountRepository accountRepository, WallPostRepository wallPostRepository) {
    this.accountRepository = accountRepository;
    this.wallPostRepository = wallPostRepository;
  }

  public Account setCurrentUserAccount(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    currentUserAccount = accountRepository.findByUserName(auth.getName());
    return currentUserAccount;
  }

  public Account getCurrentUserAccount(){
    return currentUserAccount;
  }

  public void saveWallPost(String message){
    WallPost wallPost = new WallPost();
    wallPost.setMessage(message);
    wallPost.setAuthor(currentUserAccount);

    wallPostRepository.save(wallPost);
  }


}
