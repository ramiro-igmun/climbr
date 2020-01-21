package com.climbRat.controllers;

import com.climbRat.services.HomeService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/home")
public class HomeController {

  private final HomeService homeService;

  public HomeController(HomeService homeService) {
    this.homeService = homeService;
  }

  @GetMapping
  public String climbookHome(Model model) {
    model.addAttribute("currentUser", homeService.getCurrentUserAccount());
    model.addAttribute("wallPosts", homeService.getHomePageWallPosts());
    model.addAttribute("followers",homeService.getFollowers());
    model.addAttribute("followed",homeService.getFollowing());

    return "home";
  }

  @GetMapping(value = "/picture/{pictureId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  @ResponseBody
  public byte[] profilePicture(@PathVariable Long pictureId) {
    return homeService.getPicture(pictureId);
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

  @ResponseBody
  @GetMapping("/test")
  public String test(){
    homeService.getCurrentUserAccount().getProfilePicture().getId();
    return "test";
  }
}
