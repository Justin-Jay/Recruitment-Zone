package za.co.recruitmentzone.blog.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.blog.entity.Blog;
import za.co.recruitmentzone.blog.dto.BlogDTO;

import java.security.Principal;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/Blog")
public class BlogController {

    private final RecruitmentZoneService recruitmentZoneService;

    private final Logger log = LoggerFactory.getLogger(BlogController.class);

    public BlogController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }


    @GetMapping("/blogs")
    public String blogs(Model model) {
        try {
            recruitmentZoneService.getActiveBlogs(model);
        } catch (Exception e) {
            log.error("<-- blogs -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // blogs , activeBlogResponse , internalServerError
        return "/fragments/blog/blog-page";
    }

    @GetMapping("/blog-administration")
    public String blogAdministration(Model model) {
        try {
            recruitmentZoneService.getBlogs(model);

        } catch (Exception e) {
            log.error("<-- blogAdministration -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // blogList , getBlogsResponse , internalServerError
        return "/fragments/blog/blog-administration";
    }

    @PostMapping("/add-blog")
    public String showCreateBlogForm(Model model) {
        try {
            model.addAttribute("blogDTO", new BlogDTO());
        } catch (Exception e) {
            log.error("<-- showCreateBlogForm -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // blogDTO , internalServerError
        return "fragments/blog/add-blog";
    }

    @PostMapping("/save-blog")
    public String saveBlog(@Valid @ModelAttribute("blogDTO") BlogDTO blogDTO, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasFieldErrors()) {
            log.info("HAS ERRORS");
            return "fragments/blog/add-blog";
        }
        try {
            log.info("<--- saveUpdatedBlog BODY: ---> \n {} ", blogDTO.getBody());
            boolean cleanInput ;
            cleanInput = recruitmentZoneService.cleanData(blogDTO);
            if (cleanInput){
                recruitmentZoneService.saveNewBlog(blogDTO, principal, model);
            }
            else {
                model.addAttribute("dirtyData","Failed to sanitize input");
                return "fragments/blog/update-blog";
            }

        } catch (Exception e) {
            log.error("<-- saveExistingBlog -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //   blog , findBlogResponse , internalServerError ,  saveNewBlogResponse
        //return "fragments/blog/view-blog";
        return "fragments/blog/view-home-blog";
    }

    @PostMapping("/view-blog")
    public String viewBlog(@RequestParam("blogID") Long blogID, Model model) {
        try {
            recruitmentZoneService.findBlogById(blogID, model);
        } catch (Exception e) {
            log.error("<-- showBlog -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //   blog , findBlogResponse , internalServerError
        return "fragments/blog/view-blog";
    }

    @PostMapping("/view-home-blog")
    public String showBlog(@RequestParam("blogID") Long blogID, Model model) {
        try {
            recruitmentZoneService.findBlogById(blogID, model);
        } catch (Exception e) {
            log.error("<-- showBlog -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //   blog , findBlogResponse , internalServerError
        return "fragments/blog/view-home-blog";
    }


    @PostMapping("/update-blog")
    public String updateBlog(@RequestParam("blogID") Long blogID, Model model) {
        try {
            recruitmentZoneService.findBlogById(blogID, model);
        } catch (Exception e) {
            log.error("<-- updateBlog -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", e.getMessage());
        }
        //   blog , findBlogResponse , internalServerError
        return "fragments/blog/update-blog";
    }

    @PostMapping("/save-updated-blog")
    public String saveUpdatedBlog(@Valid @ModelAttribute("blog") Blog blog,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "fragments/blog/update-blog";
        }
        try {
            log.info("<--- saveUpdatedBlog BODY: ---> \n {} ", blog.printBlog());
            boolean cleanInput ;
            cleanInput = recruitmentZoneService.cleanData(blog);
            if (cleanInput){
                recruitmentZoneService.saveUpdatedBlog(blog, model);
            }
            else {
                model.addAttribute("dirtyData","Failed to sanitize input");
                return "fragments/blog/update-blog";
            }
        } catch (Exception e) {
            log.error("<-- saveUpdatedBlog -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // blog , findBlogResponse , internalServerError, updateBlogResponse
       // return "fragments/blog/view-blog";
        return "fragments/blog/view-home-blog";
    }


}
