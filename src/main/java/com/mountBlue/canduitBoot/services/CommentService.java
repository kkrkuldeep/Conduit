package com.mountBlue.canduitBoot.services;

import com.mountBlue.canduitBoot.entity.Comments;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    public List<Comments> findAll();

    public Comments findById(int theId);

    public void save(Comments theComment);

    public void deleteById(int theId);

}
