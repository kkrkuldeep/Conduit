package com.mountBlue.canduitBoot.services;

import com.mountBlue.canduitBoot.entity.User;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface UserService {

    public List<User> findAll();

    public User findById(int theId);

    public void save(User theUser);

    public void deleteById(int theId);

    public void registerUser(User user, ModelAndView modelAndView, Errors errors);

    public void confirmUser(String confirmationToken, ModelAndView modelAndView);

    public void resetPassword(User user, ModelAndView modelAndView);

    public void confirmResetPassword(String confirmationToken, ModelAndView modelAndView);

    public void savePassword(User user,ModelAndView modelAndView);

}
