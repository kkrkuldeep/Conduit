package com.mountBlue.canduitBoot.controller;

import com.mountBlue.canduitBoot.entity.User;
import com.mountBlue.canduitBoot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserAccountController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET) // Register page
    public ModelAndView displayRegistration(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView,@Valid User user, Errors errors) {
        userService.registerUser(user, modelAndView, errors);
        return modelAndView;
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView,
                                           @RequestParam("token") String confirmationToken) {
        userService.confirmUser(confirmationToken, modelAndView);
        return modelAndView;
    }

    @GetMapping("/reset")
    public ModelAndView getRestForm(ModelAndView modelAndView) {
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("reset-password");
        return modelAndView;
    }

    @PostMapping("/reset")
    public ModelAndView resetPassword(ModelAndView modelAndView, @ModelAttribute User user) {
        userService.resetPassword(user, modelAndView);
        return modelAndView;
    }

    @GetMapping(value = "/confirm-reset-password")
    public ModelAndView restPassword(ModelAndView modelAndView,
                                     @RequestParam("token") String confirmationToken) {
        userService.confirmResetPassword(confirmationToken, modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "/save-password", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView savePassword(@ModelAttribute User user, ModelAndView modelAndView) {
        System.out.println("User data : " + user);
        userService.savePassword(user, modelAndView);
        return modelAndView;
    }
}