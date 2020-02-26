package com.climbr.controllers;

import com.climbr.domain.Account;
import com.climbr.services.AccountService;
import com.climbr.services.WallPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserPageController {

  private final AccountService accountService;
  private final WallPostService wallPostService;

  public UserPageController(WallPostService wallPostService, AccountService accountService) {
    this.wallPostService = wallPostService;
    this.accountService = accountService;
  }

  public void addModelAttributes(Model model, Account account, Account currentUser) {
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("account", account);
    model.addAttribute("users", accountService.getAllUsers());
    model.addAttribute("followers", accountService.getFollowers(account));
    model.addAttribute("followed", accountService.getFollowing(account));
    model.addAttribute("isCurrentUserFollowing", accountService.isCurrentUserFollowing(account));
    model.addAttribute("isFollowerOfCurrentUser", accountService.isFollowerOfCurrentUser(account));
  }

  @GetMapping("/{userProfileString}")
  public String getUserPage(Model model, @PathVariable String userProfileString, HttpServletRequest httpServletRequest) {
    System.out.println(httpServletRequest.getRequestURI());
    Account account = accountService.findByProfileString(userProfileString);
    Account currentUser = accountService.getCurrentUserAccountIfAuthenticated();
    if (account == null) {
      return "redirect:/home";
    }
    addModelAttributes(model, account, currentUser);
    model.addAttribute("wallPosts", wallPostService.getAccountWallPosts(account));
    return "userpage";
  }

  @GetMapping("/{userProfileString}/pictures")
  public String getUserPictures(Model model, @PathVariable String userProfileString){
    Account account = accountService.findByProfileString(userProfileString);
    Account currentUser = accountService.getCurrentUserAccountIfAuthenticated();
    if (account == null) {
      return "redirect:/home";
    }
    addModelAttributes(model, account, currentUser);
    model.addAttribute("wallPosts", wallPostService.getAccountPicturesWallPosts(account));
    return "userpage";
  }

  @GetMapping("/{userProfileString}/likes")
  public String getUserLikedWallPosts(Model model, @PathVariable String userProfileString){
    Account account = accountService.findByProfileString(userProfileString);
    Account currentUser = accountService.getCurrentUserAccountIfAuthenticated();
    if (account == null) {
      return "redirect:/home";
    }
    addModelAttributes(model, account, currentUser);
    model.addAttribute("wallPosts", wallPostService.getLikedWallPosts(account));
    return "userpage";
  }

  @GetMapping("/user")
  public String findUserPage(@RequestParam("profileString") String profileString) {

    return "redirect:/" + profileString;
  }

  @PostMapping("/{profileString}/unfollow")
  public String deleteFollowerFollowing(
          @RequestParam("accountId") Long accountId,
          @PathVariable("profileString") String profileString) {
    accountService.deleteFollowerFollowing(accountId, accountService.getCurrentUserAccountIfAuthenticated().getId());
    return "redirect:/" + profileString;
  }

  @PostMapping("/{profileString}/follow")
  public String saveFollowerFollowing(
          @RequestParam("accountId") Long accountId,
          @PathVariable("profileString") String profileString) {
    accountService.startFollowing(accountId);
    return "redirect:/" + profileString;
  }

}