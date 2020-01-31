package com.climbr.controllers;

import com.climbr.domain.Account;
import com.climbr.domain.WallPost;
import com.climbr.services.AccountService;
import com.climbr.services.PictureService;
import com.climbr.services.WallPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class HomeController {

  private final WallPostService wallPostService;
  private final AccountService accountService;
  private final PictureService pictureService;

  public HomeController(WallPostService wallPostService, AccountService accountService, PictureService pictureService) {
    this.wallPostService = wallPostService;
    this.accountService = accountService;
    this.pictureService = pictureService;
  }

  @GetMapping("/home")
  public String climbookHome(Model model) {
    Account currentUser = accountService.getCurrentUserAccount();
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("wallPosts", wallPostService.getHomePageWallPosts(currentUser));
    model.addAttribute("followers", accountService.getFollowers(currentUser));
    model.addAttribute("followed", accountService.getFollowing(currentUser));

    return "home";
  }

  @PostMapping("/home")
  public String newWallPost(@RequestParam("message") String message, @RequestParam("postImage") MultipartFile image) throws IOException {
    WallPost post = new WallPost(accountService.getCurrentUserAccount(), message);
    wallPostService.saveWallPost(post);
    if (!image.isEmpty()) {
      pictureService.savePicture(image, post, accountService.getCurrentUserAccount());
    }
    return "redirect:/home";
  }

  @ResponseBody
  @GetMapping("/test")
  public String test(HttpSession httpSession) {
    if (httpSession.getAttribute("currentUser") == null) {
      return "anonymous";
    }
    Account currentUser = (Account) httpSession.getAttribute("currentUser");
    return currentUser.getUserName();
  }
}
