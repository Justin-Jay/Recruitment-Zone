package za.co.recruitmentzone.blog.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.blog.entity.Blog;
import za.co.recruitmentzone.blog.dto.BlogDTO;
import za.co.recruitmentzone.util.enums.BlogStatus;

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

    // model = internalServerError , blogs , activeBlogResponse
    @GetMapping("/blogs")
    public String blogs(Model model) {
        try {
            recruitmentZoneService.getActiveBlogs(model);
        } catch (Exception e) {
            log.info("<-- blogs -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "/fragments/blog/blog-page";
    }

    // blog , internalServerError
    @GetMapping("/add-blog")
    public String showCreateBlogForm(Model model) {
        try {
            model.addAttribute("blog", new BlogDTO());
        } catch (Exception e) {
            log.info("<-- showCreateBlogForm -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/blog/add-blog";
    }

    // blog , findBlogResponse , internalServerError
    @PostMapping("/view-blog")
    public String showBlog(@RequestParam("blogID") Long blogID, Model model) {
        try {
            recruitmentZoneService.findBlogById(blogID, model);
        } catch (Exception e) {
            log.info("<-- showBlog -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/blog/view-blog";
    }
    //  redirectAttributes = internalServerError , saveNewBlogResponse
    @PostMapping("/save-blog")
    public String saveBlog(@Valid @ModelAttribute("blog") BlogDTO blog, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasFieldErrors()) {
            log.info("HAS ERRORS");
            return "fragments/blog/add-blog";
        }
        try {
            recruitmentZoneService.saveNewBlog(blog, principal, redirectAttributes);
        } catch (Exception e) {
            log.info("<-- saveExistingBlog -->  Exception \n {}",e.getMessage());
            redirectAttributes.addFlashAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "redirect:/Blog/blog-administration";
    }

    // redirect = saveBlogResponse , internalServerError
    @PostMapping("/save-updated-blog")
    public String saveUpdatedBlog(@Valid @ModelAttribute("blog") Blog blog, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fragments/blog/update-blog";
        }
        try {
            recruitmentZoneService.saveExistingBlog(blog,redirectAttributes);
        } catch (Exception e) {
            log.info("<-- saveUpdatedBlog -->  Exception \n {}",e.getMessage());
            redirectAttributes.addFlashAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "redirect:/Blog/blog-administration";
    }
    // model = findBlogResponse , internalServerError
    @PostMapping("/update-blog")
    public String updateBlog(@RequestParam("blogID") Long blogID, Model model) {
        try {
            recruitmentZoneService.findBlogById(blogID, model);
        } catch (Exception e) {
            log.info("<-- updateBlog -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", e.getMessage());
        }
        return "fragments/blog/update-blog";
    }

    @GetMapping("/blog-administration")
    public String blogAdministration(Model model, @ModelAttribute("saveNewBlogResponse") String saveNewBlogResponse,
                                     @ModelAttribute("saveBlogResponse") String saveBlogResponse,
                                     @ModelAttribute("internalServerError") String internalServerError) {
        try {
            recruitmentZoneService.getBlogs(model);
            model.addAttribute("saveNewBlogResponse", saveNewBlogResponse);
            model.addAttribute("saveBlogResponse", saveBlogResponse);
            model.addAttribute("internalServerError", internalServerError);
        } catch (Exception e) {
            log.info("<-- blogAdministration -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "/fragments/blog/blog-administration";
    }


}
