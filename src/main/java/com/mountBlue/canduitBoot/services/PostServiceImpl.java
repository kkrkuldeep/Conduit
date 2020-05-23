package com.mountBlue.canduitBoot.services;

import com.mountBlue.canduitBoot.DAO.PostRepository;
import com.mountBlue.canduitBoot.DAO.TagsRepository;
import com.mountBlue.canduitBoot.entity.Post;
import com.mountBlue.canduitBoot.entity.Tags;
import com.mountBlue.canduitBoot.entity.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;

import java.util.*;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    public PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAll() {
        logger.info("Get all post");
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public Post findById(int theId) {

        Optional<Post> result = postRepository.findById(theId);

        Post thePost;

        if (result.isPresent()) {
            thePost = result.get();
            logger.info("Retrieve post id: " + theId);
        } else {
            throw new RuntimeException("Did not find post id - " + theId);
        }
        return thePost;

    }

    @Override
    public void save(Post thePost) {
        postRepository.save(thePost);
    }

    @Override
    public void deleteById(int theId) {
        postRepository.deleteById(theId);
        logger.info("Delete the post id : " + theId);
    }

    @Override
    public void addPostIntoAccount(Post post, int userId, String actionPerformed) {

        if (post.getId() != 0) {
            User user = userService.findById(userId);
            Date date = new Date();
            post.setAuthor(user.getName());

            if (actionPerformed.equals("draft")) {
                post.setIs_published(false);
            } else {
                post.setIs_published(true);
            }

            List<String> postTags = Arrays.asList(post.getTags().split("#"));
            Set<String> uniqueTagSet = new HashSet<>();
            for (String s : postTags) {
                if (s.length() > 1) {
                    uniqueTagSet.add(s.trim());
                }
            }
            List<Tags> tagsList = tagService.findAll();
            boolean flag;
            for (String str : uniqueTagSet) {
                flag = true;
                for (Tags tags : tagsList) {
                    if (tags.getName().equals(str)) {
                        flag = false;
                        post.getTagList().add(tags);
                        tags.getPostList().add(post);
                    }
                }
                if (flag) {
                    Tags tag = new Tags();
                    tag.setName(str);
                    tag.setCreatedAt(date.toString());
                    tag.setUpdatedAt(date.toString());
                    tag.getPostList().add(post);
                    post.getTagList().add(tag);
                }
            }
            post.setUser(user);
            postService.save(post);
        } else {
            User user = userService.findById(userId);
            List<String> postEntryTags = Arrays.asList(post.getTags().split("#"));
            Set<String> getUniqueSetCollection = new HashSet<>();
            for (String inputTag : postEntryTags) {
                if (inputTag.length() > 1) {
                    getUniqueSetCollection.add(inputTag.trim());
                }
            }
            List<Tags> tagsList = tagService.findAll();
            Date date = new Date();
            boolean flag;

            for (String tagName : getUniqueSetCollection) {
                flag = true;
                for (Tags tag : tagsList) {
                    if (tag.getName().equals(tagName)) {
                        flag = false;
                        post.getTagList().add(tag);
                        tag.getPostList().add(post);
                    }
                }
                if (flag == true) {
                    Tags tag = new Tags();
                    tag.setName(tagName);
                    tag.setCreatedAt(date.toString());
                    tag.setUpdatedAt(date.toString());
                    tag.getPostList().add(post);
                    post.getTagList().add(tag);
                }
            }

            if (actionPerformed.equals("draft")) {
                post.setIs_published(false);
            } else {
                post.setIs_published(true);
            }

            post.setAuthor(user.getName());
            post.setCreated_at(date.toString());
            post.setPublish_at(date.toString());
            post.setUpdated_at(date.toString());
            post.setUser(user);
            postService.save(post);
        }
    }

}
