package com.mountBlue.canduitBoot.DAO;

import com.mountBlue.canduitBoot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);

    User findByEmailIgnoreCase(String emailId);
}
