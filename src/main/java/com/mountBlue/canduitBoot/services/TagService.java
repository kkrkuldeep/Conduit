package com.mountBlue.canduitBoot.services;

import com.mountBlue.canduitBoot.entity.Tags;

import java.util.List;

public interface TagService {

    public List<Tags> findAll();

    public Tags findById(int theId);

    public void save(Tags theTags);

    public void deleteById(int theId);
}
