package com.mountBlue.canduitBoot.services;

import com.mountBlue.canduitBoot.DAO.AuthoritiesRepository;
import com.mountBlue.canduitBoot.DAO.ConfirmationTokenRepository;
import com.mountBlue.canduitBoot.DAO.UserRepository;
import com.mountBlue.canduitBoot.constant.Constant;
import com.mountBlue.canduitBoot.entity.Authorities;
import com.mountBlue.canduitBoot.entity.ConfirmationToken;
import com.mountBlue.canduitBoot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int theId) {

        Optional<User> result = userRepository.findById(theId);

        User theUser;

        if (result.isPresent()) {
            theUser = result.get();
        } else {
            throw new RuntimeException("Did not find user id - " + theId);
        }

        return theUser;
    }

    @Override
    public void save(User theUser) {
        userRepository.save(theUser);
    }

    @Override
    public void deleteById(int theId) {
        userRepository.deleteById(theId);
    }

    @Override
    public void registerUser(User user, ModelAndView modelAndView, Errors errors) {


        User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail()); // null false

        if (errors.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            if (existingUser != null) {
                modelAndView.addObject("message", "This email already exists!");
                modelAndView.setViewName("error");
            } else {

                user.setEnabled(false);
                userRepository.save(user);

                ConfirmationToken confirmationToken = new ConfirmationToken(user);
                confirmationTokenRepository.save(confirmationToken);

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Complete Registration!");
                mailMessage.setFrom("kkrkuldeepkumar@gmail.com");
                mailMessage.setText("To confirm your account, please click here : " + Constant.LOCAL_HOST_LINK
                        + "/confirm-account?token=" + confirmationToken.getConfirmationToken());

                emailSenderService.sendEmail(mailMessage);
                modelAndView.addObject("emailId", user.getEmail());
                modelAndView.setViewName("successfulRegisteration");
            }
        }
    }

    @Override
    public void confirmUser(String confirmationToken, ModelAndView modelAndView) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            Authorities authorities = authoritiesService.findById(Constant.ROLE_USER);
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAuthorities(authorities);
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.setViewName("accountVerified");
        } else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
    }

    @Override
    public void resetPassword(User user, ModelAndView modelAndView) {

        User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail()); // It have my user
        System.out.println(user.getEmail() + " :Emial");
        System.out.println("existingUser: " + existingUser);

        if (existingUser != null) {

            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);
            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Reset Password");
            mailMessage.setFrom("kkrkuldeepkumar@gmail.com");
            mailMessage.setText("To confirm your account, please click here : " + Constant.LOCAL_HOST_LINK
                    + "/confirm-reset-password?token=" + confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("emailId", user.getEmail());
            modelAndView.setViewName("successfulRestPassword");
        } else {
            modelAndView.addObject("message", "This email not exists!");
            modelAndView.setViewName("error");
        }
    }

    @Override
    public void confirmResetPassword(String confirmationToken, ModelAndView modelAndView) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User existingUser = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            existingUser.setPassword(Constant.DEFAULT_PASSWORD);
            modelAndView.addObject("user", existingUser);
            modelAndView.setViewName("new-password");
        } else {
            modelAndView.setViewName("error");
        }
    }

    @Override
    public void savePassword(User user, ModelAndView modelAndView) {

        System.out.println("User save method : " + user);

        User updateUser = userRepository.findByEmailIgnoreCase(user.getEmail());

        String role = updateUser.getAuthorities().getRoleName();
        Authorities authorities = authoritiesRepository.findByRoleName(role);

        if (user.getEmail() != null) {
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
            updateUser.setAuthorities(authorities);
            userRepository.save(updateUser);
            modelAndView.setViewName("success");
        } else {
            modelAndView.setViewName("error");
        }
    }


}
