package com.climbr.controllers;

import com.climbr.services.PictureService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PictureController {

  private final PictureService pictureService;

  public PictureController(PictureService pictureService) {
    this.pictureService = pictureService;
  }

  @GetMapping(value = "/picture/{pictureId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  @ResponseBody
  public byte[] profilePicture(@PathVariable Long pictureId) {
    return pictureService.getPicture(pictureId);
  }

  @PostMapping("/picture/{pictureId}")
  public String makeProfilePicture(@PathVariable Long pictureId,
                                   @RequestParam("anchor") int anchor,
                                   HttpServletRequest httpServletRequest){
    pictureService.makeProfilePicture(pictureId);
    return "redirect:" + httpServletRequest.getHeader("referer") + "#" + anchor;
  }
}
