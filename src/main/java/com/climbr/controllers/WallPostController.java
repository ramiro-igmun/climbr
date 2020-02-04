package com.climbr.controllers;

import com.climbr.domain.Account;
import com.climbr.domain.WallPost;
import com.climbr.services.AccountService;
import com.climbr.services.PictureService;
import com.climbr.services.WallPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class WallPostController {
  private WallPostService wallPostService;
  private AccountService accountService;
  private PictureService pictureService;

  public WallPostController(WallPostService wallPostService, AccountService accountService, PictureService pictureService) {
    this.wallPostService = wallPostService;
    this.accountService = accountService;
    this.pictureService = pictureService;
  }

  @GetMapping("/{userProfileString}/wallpost/{wallPostId}")
  public String wallPostPage(Model model,
                             @PathVariable("wallPostId") Long wallPostId,
                             @PathVariable("userProfileString") String userProfileString) {
    Account account = accountService.findByProfileString(userProfileString);
    WallPost wallPost = wallPostService.getWallPost(wallPostId);
    model.addAttribute("account", account);
    model.addAttribute("mainWallPost", wallPost);
    model.addAttribute("users", accountService.getAllUsers());
    model.addAttribute("currentUser", accountService.getCurrentUserAccount());
    model.addAttribute("comments", wallPostService.getWallPostComments(wallPost));
    model.addAttribute("followers", accountService.getFollowers(account));
    model.addAttribute("followed", accountService.getFollowing(account));
    return "wallpost";
  }

  @PostMapping("/wallpost/like")
  public String addLike(@RequestParam("likedWallPost") Long likedWallPost, HttpServletRequest httpServletRequest){
    wallPostService.addLikeToWallPost(likedWallPost, accountService.getCurrentUserAccount());
    return "redirect:" + httpServletRequest.getHeader("referer");
  }

  @PostMapping("/wallpost/new")
  public String newWallPost(
          HttpServletRequest httpServletRequest,
          @RequestParam("message") String message,
          @RequestParam("postImage") MultipartFile image,
          @RequestParam("parentPost") Long parentPostId) throws IOException {
    WallPost parentPost;
    if (parentPostId != null){
      parentPost = wallPostService.getParentPost(parentPostId);
    }else{
      parentPost = null;
    }
    WallPost post = new WallPost(accountService.getCurrentUserAccount(), message, parentPost);
    wallPostService.saveWallPost(post);
    if (!image.isEmpty()) {
      pictureService.savePicture(image, post, accountService.getCurrentUserAccount());
    }
    return "redirect:" + httpServletRequest.getHeader("referer");
  }

}
