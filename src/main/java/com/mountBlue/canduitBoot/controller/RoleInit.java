package com.mountBlue.canduitBoot.controller;

import com.mountBlue.canduitBoot.DAO.AuthoritiesRepository;
import com.mountBlue.canduitBoot.DAO.UserRepository;
import com.mountBlue.canduitBoot.entity.Authorities;
import com.mountBlue.canduitBoot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RoleInit {

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void addBasicRole() {
        User checkUserExist = userRepository.findByEmailIgnoreCase("kkrkuldeepkumar@gmail.com");

        Authorities user = new Authorities("ROLE_USER");
        Authorities admin = new Authorities("ROLE_ADMIN");
        if (authoritiesRepository.findByRoleName(user.getRoleName()) == null) {
            authoritiesRepository.save(admin);
        }
        if (authoritiesRepository.findByRoleName(admin.getRoleName()) == null) {
            authoritiesRepository.save(user);
        }
        if (checkUserExist == null) {
            User createUser = new User("Kuldeep", "kkrkuldeepkumar@gmail.com",
                    "$2a$10$n23xM47CABU3IgIpt6GJVO8np1MzDZU/jD/mDjpm8owh2YIggmo5.", true, admin);
        }
    }
}
