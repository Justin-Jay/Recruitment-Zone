package za.co.recruitmentzone.blog.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.blog.dto.BlogImageDTO;
import za.co.recruitmentzone.blog.dto.BlogStatusDTO;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.blog.entity.Blog;
import za.co.recruitmentzone.blog.dto.BlogDTO;
import za.co.recruitmentzone.vacancy.dto.VacancyStatusDTO;

import java.security.Principal;
import java.util.List;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/Blog")
public class BlogController {

    private final RecruitmentZoneService recruitmentZoneService;

    private final Logger log = LoggerFactory.getLogger(BlogController.class);

    @Value("${blog.image.path}")
    private String BLOG_LOCAL_STORAGE;

    private final int pageSize = 10;


    public BlogController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }


    @GetMapping("/blogs")
    public String blogs(Model model) {
        try {
            recruitmentZoneService.getActiveBlogs(model);

            model.addAttribute("BLOG_VOLUME", BLOG_LOCAL_STORAGE);
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
           // int pageSize = 10;
            recruitmentZoneService.getBlogs(model, 1, pageSize, "created", "desc");
        } catch (Exception e) {
            log.error("<-- blogAdministration -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // blogList , getBlogsResponse , internalServerError
        return "/fragments/blog/blog-administration";
    }

    @GetMapping("/paginatedBlogs/{pageNo}")
    public String findPaginatedBlogs(@PathVariable(value = "pageNo") int pageNo,
                                     @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDirection, Model model) {
        log.info("<--- findPaginatedBlogs: Page number  {} sortField {} sortDirection {} --->",
                pageNo, sortField, sortDirection);
        recruitmentZoneService.getBlogs(model, pageNo, pageSize, sortField, sortDirection);
        return "fragments/blog/blog-administration :: blog-admin-table";
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
            boolean cleanInput;
            cleanInput = recruitmentZoneService.cleanData(blogDTO);
            if (cleanInput) {
                recruitmentZoneService.saveNewBlog(blogDTO, principal, model);
                //  recruitmentZoneService.getBlogs(model,1, pageSize, "created", "desc");
                model.addAttribute("BLOG_VOLUME", BLOG_LOCAL_STORAGE);
                log.info("BLOG_VOLUME = {} ", BLOG_LOCAL_STORAGE);
            } else {
                model.addAttribute("dirtyData", "Failed to sanitize input");
                return "fragments/blog/update-blog";
            } //

        } catch (Exception e) {
            log.error("<-- saveExistingBlog -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //   blog , findBlogResponse , internalServerError ,  saveNewBlogResponse
        return "fragments/blog/view-home-blog";
    }

    @PostMapping("/view-blog")
    public String viewBlog(@RequestParam("blogID") Long blogID, Model model) {
        try {
            recruitmentZoneService.findBlogById(blogID, model);
            model.addAttribute("BLOG_VOLUME", BLOG_LOCAL_STORAGE);
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
            model.addAttribute("BLOG_VOLUME", BLOG_LOCAL_STORAGE);
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
            boolean cleanInput = false;
            cleanInput = recruitmentZoneService.cleanData(blog);
            if (cleanInput) {
                recruitmentZoneService.saveUpdatedBlog(blog, model);
                //  int pageSize = 10;
                // recruitmentZoneService.getBlogs(model,1, pageSize, "created", "desc");
                model.addAttribute("BLOG_VOLUME", BLOG_LOCAL_STORAGE);
                log.info("BLOG_VOLUME = {} ", BLOG_LOCAL_STORAGE);
            } else {
                model.addAttribute("dirtyData", "Failed to sanitize input");
                // return "fragments/blog/update-blog";
            }
        } catch (Exception e) {
            log.error("<-- saveUpdatedBlog -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // blog , findBlogResponse , internalServerError, updateBlogResponse
        // return "fragments/blog/view-blog";
        return "fragments/blog/view-home-blog";
    }

    @PostMapping("/add-blog-image")
    public String addBlogImage(@RequestParam("blogID") Long blogID, Model model) {
        try {
            // blogImageDTO
            model.addAttribute("blogImageDTO", new BlogImageDTO(blogID));
            log.info("addBlogImage LOADED FOR {} ", blogID);
        } catch (Exception e) {
            log.error("<-- addBlogImage -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancy , findVacancyResponse
        return "fragments/blog/blog-image-upload";
    }

    @PostMapping("/save-blog-image")
    public String saveBlogImage(@Valid @ModelAttribute("blogImageDTO") BlogImageDTO blogImageDTO,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            log.info("HAS ERRORS");
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);

            return "fragments/blog/blog-image-upload";
        }
        try {

            boolean validImage = recruitmentZoneService.validateImage(blogImageDTO,model);

            if (validImage) {
                recruitmentZoneService.saveBlogImage(blogImageDTO,model);
                recruitmentZoneService.findBlogById(blogImageDTO.getBlogID(), model);
            }
            else {
                recruitmentZoneService.findBlogById(blogImageDTO.getBlogID(), model);
                return "fragments/blog/blog-image-upload";
            }
        } catch (Exception e) {
            log.info("exception uploading image", e);
        }

        return "fragments/blog/view-blog";
    }

    @PostMapping("/update-blog-status")
    public String updateBlogStatus(@RequestParam("blogID") Long blogID, Model model) {
        try {
            recruitmentZoneService.findBlogStatus(blogID, model);
        } catch (Exception e) {
            log.error("<-- updateVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancy , findVacancyResponse
        return "fragments/blog/update-blog-status";
    }

    @PostMapping("/save-updated-blog-status")
    public String saveUpdatedStatus(@Valid @ModelAttribute("blogStatusDTO") BlogStatusDTO blogStatusDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {

            return "fragments/vacancy/update-vacancy-status";
        }
        try {
            recruitmentZoneService.saveNewBlogStatus(blogStatusDTO, model);
            recruitmentZoneService.findBlogStatus(blogStatusDTO.getBlogID(), model);
        } catch (Exception e) {
            log.error("<-- updateVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancy , findVacancyResponse
        return "fragments/blog/update-blog-status";
    }


}
