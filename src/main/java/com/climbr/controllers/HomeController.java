package com.climbr.controllers;

import com.climbr.domain.Account;
import com.climbr.domain.WallPost;
import com.climbr.services.AccountService;
import com.climbr.services.WallPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@Controller
public class HomeController {

  private final WallPostService wallPostService;
  private final AccountService accountService;


  public HomeController(WallPostService wallPostService, AccountService accountService) {
    this.wallPostService = wallPostService;
    this.accountService = accountService;
  }

  @GetMapping("/home")
  public String climbookHome(Model model) {
    Account currentUser = accountService.getCurrentUserAccountInSecuredContext();
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("users", accountService.getAllUsers());
    model.addAttribute("wallPosts", wallPostService.getHomePageWallPosts(currentUser));
    model.addAttribute("followers", accountService.getFollowers(currentUser));
    model.addAttribute("followed", accountService.getFollowing(currentUser));

    return "home";
  }

  @ResponseBody
  @GetMapping("/test")
  public String test() {
    return wallPostService.getHomePageWallPosts(accountService.findByProfileString("irami007")).stream()
            .map(wallPost -> {
              String isComment = "No Parent Post";
              WallPost w;
              if((w = wallPost.getParentPost()) != null){
                isComment = w.getAuthor().getUserName();
              };
              return wallPost.getAuthor().getUserName() + " " + isComment;
            }).collect(Collectors.toList()).toString();
  }
}
