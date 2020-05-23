package com.mountBlue.canduitBoot.controller;

import com.mountBlue.canduitBoot.DAO.PostRepository;
import com.mountBlue.canduitBoot.DAO.TagsRepository;
import com.mountBlue.canduitBoot.entity.Post;
import com.mountBlue.canduitBoot.entity.Tags;
import com.mountBlue.canduitBoot.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class OrganizeController {

    @Autowired
    private TagsRepository tagsRepository;

    private final PostRepository postRepository;

    private final TagService tagService;

    public OrganizeController(PostRepository postRepository, TagService tagService) {
        this.postRepository = postRepository;
        this.tagService = tagService;
    }

    @GetMapping("/search")
    public String getSearchResult(@RequestParam() Optional<String> title, Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam Optional<String> sortBy) {

        model.addAttribute("postList", postRepository.findBySearchString(title.orElse("_"),
                PageRequest.of(page, 4, Sort.Direction.ASC, sortBy.orElse("id"))));

        List<Tags> tagList = tagService.findAll();
        model.addAttribute("tagList", tagList);

        return "search";
    }

    @GetMapping("/filter")
    public String getFilter(@RequestParam("tagId") int id,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam Optional<String> sortBy, Model model) {

        String getTagName = tagService.findById(id).getName();

        System.out.println("Get tag name: " + getTagName);

        List<Tags> tagList = tagService.findAll();
        model.addAttribute("tagList", tagList);

        model.addAttribute("postList", postRepository.findByTags(getTagName,
                PageRequest.of(page, 4, Sort.Direction.ASC,
                sortBy.orElse("id"))));

        return "search";
    }
}
