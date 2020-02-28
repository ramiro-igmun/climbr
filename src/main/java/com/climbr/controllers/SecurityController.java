package com.climbr.controllers;

import com.climbr.domain.Account;
import com.climbr.domain.Validators.AccountCreationValidationGroup;
import com.climbr.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {

  AccountService accountService;

  public SecurityController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("user/registration")
  public String getRegistrationPage(@ModelAttribute("newUser") Account newUser){
    return "registration";
  }

  @PostMapping("user/registration")
  public String createNewUser(@Validated(AccountCreationValidationGroup.class) @ModelAttribute("newUser") Account newUser,
                              BindingResult bindingResult){
    if (bindingResult.hasErrors()){
      return "registration";
    }
    accountService.saveUser(newUser);
    return "redirect:/home";
  }
}
