package com.mountBlue.canduitBoot.controller;

import com.mountBlue.canduitBoot.DAO.AuthoritiesRepository;
import com.mountBlue.canduitBoot.DAO.CommentRepository;
import com.mountBlue.canduitBoot.DAO.PostRepository;
import com.mountBlue.canduitBoot.DAO.UserRepository;
import com.mountBlue.canduitBoot.entity.*;
import com.mountBlue.canduitBoot.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.*;

@Controller
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final UserRepository userRepository;

    private final AuthoritiesRepository authoritiesRepository;

    private final AuthoritiesService authoritiesService;

    private final PostService postService;

    private final UserService userService;

    private final TagService tagService;

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public IndexController(PostService postService, UserService userService, TagService tagService,
                           CommentRepository commentRepository,
                           PostRepository postRepository, UserRepository userRepository,
                           AuthoritiesRepository authoritiesRepository, AuthoritiesService authoritiesService) {
        this.postService = postService;
        this.userService = userService;
        this.tagService = tagService;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.authoritiesService = authoritiesService;
    }

    @PostConstruct
    public void addBasicRole() {
        Authorities user = new Authorities("ROLE_USER");
        Authorities admin = new Authorities("ROLE_ADMIN");
        if (authoritiesRepository.findByRoleName(user.getRoleName()) == null) {
            authoritiesService.save(user);
        }
        if (authoritiesRepository.findByRoleName(admin.getRoleName()) == null) {
            authoritiesService.save(admin);
        }
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {

        model.addAttribute("postList", postRepository.findByIs_publishedTrue(PageRequest.of(page, 4)));

        List<Tags> tagsList = tagService.findAll();
        model.addAttribute("tagList", tagsList);

        return "index";
    }

    @GetMapping("/signin")
    public String showSignIn() {

        return "signin";
    }

    @GetMapping("/signup")
    public String showSignUp() {

        return "signup";
    }

    @GetMapping("/newArticle")
    public String newArticle(Model model, Principal principal) {

        User user = userRepository.findByName(principal.getName());
        Post post = new Post();

        model.addAttribute("post", post);
        model.addAttribute("user", user);

        return "newArticle";
    }

    // Create
    @PostMapping("/addArticle")
    public String addPost(@ModelAttribute("post") Post post,
                          @ModelAttribute User user,
                          @RequestParam(name = "actionPerformed") String actionPerformed,Principal principal) {

        int userId = userRepository.findByName(principal.getName()).getId();
        postService.addPostIntoAccount(post, userId, actionPerformed);
        return "redirect:/";
    }

    @GetMapping("/publish")
    public String showPost(@RequestParam("postId") Post postParam, Model model) {

        int theId = postParam.getId();

        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);

        User user = userService.findById(postParam.getUser().getId());
        model.addAttribute("user", user);

        model.addAttribute("post", postParam);

        Comments comments = new Comments();
        model.addAttribute("comment", comments);

        List<Comments> commentsList = commentRepository.findByPostId(theId);
        model.addAttribute("commentList", commentsList);

        for (Comments com : commentsList) {
            System.out.println(com);
        }

        return "publish";
    }

    //Update
    @GetMapping("/updateArticle")
    public String updateArticle(@RequestParam("postId") Post postParam, Model model) {

        logger.info("Retrieve a post to update id: " + postParam.getId());

        int theId = postParam.getId();
        Post post = postService.findById(theId);

        int userId = post.getUser().getId();

        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);

        model.addAttribute("user", userId);

        model.addAttribute("post", post);

        return "newArticle";
    }

    // Delete
    @GetMapping("/deleteArticle")
    public String deleteArticle(@RequestParam("postId") int theId) {

        postService.deleteById(theId);
        return "redirect:/";
    }
}