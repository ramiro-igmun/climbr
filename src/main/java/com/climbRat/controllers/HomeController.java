package com.climbRat.controllers;

import com.climbRat.domain.Account;
import com.climbRat.services.AccountService;
import com.climbRat.services.HomeService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/home")
public class HomeController {

  private final HomeService homeService;
  private final AccountService accountService;

  public HomeController(HomeService homeService, AccountService accountService) {
    this.homeService = homeService;
    this.accountService = accountService;
  }

  @GetMapping
  public String climbookHome(Model model) {
    Account currentUser = accountService.getCurrentUserAccount();
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("wallPosts", homeService.getHomePageWallPosts(currentUser));
    model.addAttribute("followers",accountService.getFollowers(currentUser));
    model.addAttribute("followed",accountService.getFollowing(currentUser));

    return "home";
  }

  @GetMapping(value = "/picture/{pictureId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  @ResponseBody
  public byte[] profilePicture(@PathVariable Long pictureId) {
    return homeService.getPicture(pictureId);
  }

  @PostMapping
  public String newWallPost(@RequestParam String message) {
    homeService.saveWallPost(message, accountService.getCurrentUserAccount());
    return "redirect:/home";
  }

  @PostMapping("/like")
  public String addLike(@RequestParam Long likedWallPost){
    homeService.addLikeToWallPost(likedWallPost, accountService.getCurrentUserAccount());
    return "redirect:/home";
  }

  @ResponseBody
  @GetMapping("/test")
  public String test(){
    accountService.getCurrentUserAccount().getProfilePicture().getId();
    return "test";
  }
}
