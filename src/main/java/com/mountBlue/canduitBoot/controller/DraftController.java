package com.mountBlue.canduitBoot.controller;

import com.mountBlue.canduitBoot.DAO.PostRepository;
import com.mountBlue.canduitBoot.entity.Tags;
import com.mountBlue.canduitBoot.services.TagService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DraftController {

    private final PostRepository postRepository;

    private final TagService tagService;

    public DraftController(PostRepository postRepository, TagService tagService) {
        this.postRepository = postRepository;
        this.tagService = tagService;
    }

    @GetMapping("/draft")
    public String showDrafts(Model model, @RequestParam(defaultValue = "0") int page) {

        model.addAttribute("postList", postRepository.findByIs_publishedFalse(PageRequest.of(page, 4)));

        List<Tags> tagsList = tagService.findAll();
        model.addAttribute("tagList", tagsList);

        return "draft";
    }
}
