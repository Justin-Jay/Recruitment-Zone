package za.co.RecruitmentZone.controller.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.Enums.BlogStatus;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;
import za.co.RecruitmentZone.entity.domain.Blog;
import za.co.RecruitmentZone.entity.domain.Employee;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("/")
public class BlogController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(BlogController.class);

    public BlogController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    // Blog
    @GetMapping("/blogs")
    public String blogs(Model model) {
        List<Blog> allBlogs = new ArrayList<>();
        try {
            allBlogs = recruitmentZoneService.getBlogs();
        } catch (Exception e) {
            log.info("Exception trying to retrieve blogs, retrieving all vacancies ");
        }
        model.addAttribute("blogs", allBlogs);
        return "/fragments/blog/blog-page";
    }

    @GetMapping("/add-blog")
    public String showCreateBlogForm(Model model) {
        model.addAttribute("blog", new Blog());
        List<Employee> employees = recruitmentZoneService.getEmployees();
        model.addAttribute("employees", employees);
        return "fragments/blog/add-blog";
    }

    @PostMapping("/view-blog")
    public String showBlog(@RequestParam("blogID") Long blogID, Model model) {
        Blog optionalBlog = recruitmentZoneService.findBlogByID(blogID);
        log.info("Looking for {}", blogID);
        log.info(optionalBlog.toString());
        model.addAttribute("blog", optionalBlog);
        return "fragments/blog/view-blog";
    }
    @PostMapping("/save-blog")
    public String saveBlog(@Valid @ModelAttribute("blog")Blog blog, @RequestParam("name")String name,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/blog/add-blog";
        }
        blog.setStatus(BlogStatus.PENDING);
        recruitmentZoneService.saveNewBlog(name,blog);
        return "redirect:/blog-administration";
    }
    @PostMapping("/update-blog")
    public String updateBlog(@RequestParam("blogID") Long blogID, Model model) {
        Blog blog = recruitmentZoneService.findBlogByID(blogID);
        model.addAttribute("blog", blog);
        model.addAttribute("status", BlogStatus.values());
        return "fragments/blog/update-blog";
    }

    @PostMapping("/save-updated-blog")
    public String saveUpdatedBlog(@Valid @ModelAttribute("blog")Blog blog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/blog/update-blog";
        }
        recruitmentZoneService.saveBlog(blog);
        return "redirect:/blog-administration";
    }

    @GetMapping("/blog-administration")
    public String blogAdministration(Model model) {
        List<Blog> allBlogs = new ArrayList<>();
        try {
            allBlogs = recruitmentZoneService.getBlogs();
        } catch (Exception e) {
            log.info("Exception trying to retrieve blogs, retrieving all vacancies ");
        }
        model.addAttribute("blogs", allBlogs);
        return "/fragments/blog/blog-administration";
    }
}
