package com.climbr.controllers;

import com.climbr.domain.Account;
import com.climbr.domain.Validators.ProfileEditValidator;
import com.climbr.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class EditProfileController {

  private AccountService accountService;

  public EditProfileController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/{profileString}/profile")
  public String editProfilePage(@ModelAttribute("editedProfile") Account editedProfile, Model model, HttpSession httpSession){
    model.addAttribute("currentUser",(Account) httpSession.getAttribute("currentUser"));
    return "editProfile";
  }

  @PostMapping("/{profileString}/profile")
  public String updateUserProfile(@Validated(ProfileEditValidator.class) @ModelAttribute("editedProfile") Account editedProfile,
                                  BindingResult bindingResult,Model model,HttpSession httpSession){
    if (bindingResult.hasErrors()){
      model.addAttribute("currentUser",(Account) httpSession.getAttribute("currentUser"));
      return "editProfile";
    }
    accountService.updateUserProfile(editedProfile.getUserName(),editedProfile.getProfileString());
    return "redirect:/" + editedProfile.getProfileString();
  }
}
