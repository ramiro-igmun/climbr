package com.climbRat.controllers;

import com.climbRat.services.AccountService;
import com.climbRat.services.WallPostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WallPostController {
  private WallPostService wallPostService;
  private AccountService accountService;

  public WallPostController(WallPostService wallPostService, AccountService accountService) {
    this.wallPostService = wallPostService;
    this.accountService = accountService;
  }

  @PostMapping("wallpost/like")
  public String addLike(@RequestParam Long likedWallPost) {
    wallPostService.addLikeToWallPost(likedWallPost, accountService.getCurrentUserAccount());
    return "redirect:/home";
  }

}
