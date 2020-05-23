package com.mountBlue.canduitBoot.services;

import com.mountBlue.canduitBoot.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    public List<Post> findAll();

    public Post findById(int theId);

    public void save(Post thePost);

    public void deleteById(int theId);

    public void addPostIntoAccount(Post post, int userId, String actionPerformed);
}
