package com.mountBlue.canduitBoot.DAO;

import com.mountBlue.canduitBoot.entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {

    public Authorities findByRoleName(String Role);
}
