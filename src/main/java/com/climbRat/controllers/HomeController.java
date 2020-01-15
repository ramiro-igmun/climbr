package com.climbRat.controllers;

import com.climbRat.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/home")
public class HomeController {

  private HomeService homeService;

  @Autowired
  public HomeController(HomeService homeService) {
    this.homeService = homeService;
  }

  @GetMapping
  public String climbookHome(Model model) {
    model.addAttribute("currentUser", homeService.getUserDetails());
    model.addAttribute("wallPosts", homeService.getHomePageWallPosts());
    model.addAttribute("followers",homeService.getFollowers());
    model.addAttribute("followed",homeService.getFollowedAccounts());

    return "home";
  }

  @GetMapping(value = "/profilePicture", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  @ResponseBody
  public byte[] profilePicture() {
    return homeService.getProfilePicture();
  }

  @PostMapping
  public String newWallPost(@RequestParam String message) {
    homeService.saveWallPost(message);
    return "redirect:/home";
  }

  @PostMapping("/like")
  public String addLike(@RequestParam Long likedWallPost){
    homeService.addLikeToWallPost(likedWallPost);
    return "redirect:/home";
  }
}
