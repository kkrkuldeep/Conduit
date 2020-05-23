package com.mountBlue.canduitBoot.services;

import com.mountBlue.canduitBoot.DAO.AuthoritiesRepository;
import com.mountBlue.canduitBoot.entity.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthoritiesServiceImpl implements AuthoritiesService {

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Override
    public List<Authorities> findAll() {
        return authoritiesRepository.findAll();
    }

    @Override
    public Authorities findById(int theId) {
        Optional<Authorities> result = authoritiesRepository.findById(theId);

        Authorities theAuthorities;

        if(result.isPresent()){
            theAuthorities = result.get();
        }else{
            throw new RuntimeException("Did not find authority id - " + theId);
        }

        return theAuthorities;
    }

    @Override
    public void save(Authorities theAuthorities) {
        authoritiesRepository.save(theAuthorities);
    }

    @Override
    public void deleteById(int theId) {
        authoritiesRepository.deleteById(theId);
    }
}
