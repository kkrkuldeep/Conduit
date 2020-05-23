package com.mountBlue.canduitBoot.controller;

import com.mountBlue.canduitBoot.DAO.UserRepository;
import com.mountBlue.canduitBoot.entity.Comments;
import com.mountBlue.canduitBoot.entity.Post;
import com.mountBlue.canduitBoot.entity.User;
import com.mountBlue.canduitBoot.services.CommentService;
import com.mountBlue.canduitBoot.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class CommentController {

    private UserRepository userRepository;

    private PostService postService;

    private CommentService commentService;

    public CommentController(PostService postService, CommentService commentService, UserRepository userRepository) {
        this.postService = postService;
        this.commentService = commentService;
        this.userRepository = userRepository;
    }

    //     save comment
    @PostMapping("/saveComment")
    public String saveComment(@ModelAttribute Comments comments, @RequestParam("postId") int postId,
                              Principal principal) {

        User user = userRepository.findByName(principal.getName());

        Post post = postService.findById(postId);

        comments.setEmail(user.getEmail());
        comments.setName(user.getName());
        comments.setPost(post);
        commentService.save(comments);

        return "redirect:/";
    }
}
