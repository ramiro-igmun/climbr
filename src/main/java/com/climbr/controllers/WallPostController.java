package com.climbr.controllers;

import com.climbr.domain.Account;
import com.climbr.domain.WallPost;
import com.climbr.services.AccountService;
import com.climbr.services.WallPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class WallPostController {
  private WallPostService wallPostService;
  private AccountService accountService;

  public WallPostController(WallPostService wallPostService, AccountService accountService) {
    this.wallPostService = wallPostService;
    this.accountService = accountService;
  }

  @GetMapping("/{userProfileString}/wallpost/{wallPostId}")
  public String wallPostPage(Model model, @PathVariable("wallPostId") Long wallPostId, @PathVariable("userProfileString") String userProfileString) {
    Account account = accountService.findByProfileString(userProfileString);
    WallPost wallPost = wallPostService.getWallPost(wallPostId);
    model.addAttribute("account", account);
    model.addAttribute("mainWallPost", wallPost);
    model.addAttribute("currentUser", accountService.getCurrentUserAccount());
    model.addAttribute("comments", wallPostService.getWallPostComments(wallPost));
    model.addAttribute("followers", accountService.getFollowers(account));
    model.addAttribute("followed", accountService.getFollowing(account));
    return "wallpost";
  }

  @PostMapping("wallpost/like")
  public String addLike(@RequestParam Long likedWallPost) {
    wallPostService.addLikeToWallPost(likedWallPost, accountService.getCurrentUserAccount());
    return "redirect:/home";
  }

}
