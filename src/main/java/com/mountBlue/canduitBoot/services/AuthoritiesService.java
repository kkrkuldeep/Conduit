package com.mountBlue.canduitBoot.services;

import com.mountBlue.canduitBoot.entity.Authorities;
import java.util.List;

public interface AuthoritiesService {


    public List<Authorities> findAll();

    public Authorities findById(int theId);

    public void save(Authorities theAuthorities);

    public void deleteById(int theId);
}
