package com.mountBlue.canduitBoot.services;

import com.mountBlue.canduitBoot.DAO.TagsRepository;
import com.mountBlue.canduitBoot.entity.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public List<Tags> findAll() {
        return tagsRepository.findAll();
    }

    @Override
    public Tags findById(int theId) {
        Optional<Tags> result = tagsRepository.findById(theId);

        Tags theTag;

        if(result.isPresent()){
            theTag = result.get();
        }else{
            throw new RuntimeException("Did not find tag id - " + theId);
        }

        return theTag;
    }

    @Override
    public void save(Tags theTags) {
        tagsRepository.save(theTags);
    }

    @Override
    public void deleteById(int theId) {
        tagsRepository.deleteById(theId);
    }
}
