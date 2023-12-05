package za.co.RecruitmentZone.controller.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.Enums.BlogStatus;
import za.co.RecruitmentZone.entity.domain.Blog;
import za.co.RecruitmentZone.entity.domain.Employee;
import za.co.RecruitmentZone.service.domainServices.BlogService;
import za.co.RecruitmentZone.service.domainServices.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("/")
public class BlogController {
    private final BlogService blogService;
    private final EmployeeService employeeService;
    private final Logger log = LoggerFactory.getLogger(BlogController.class);

    public BlogController(BlogService blogService, EmployeeService employeeService) {
        this.blogService = blogService;
        this.employeeService = employeeService;
    }

    // Blog
    @GetMapping("/blogs")
    public String blogs(Model model) {
        List<Blog> allBlogs = new ArrayList<>();
        try {
            allBlogs = getActiveBlogs();
        } catch (Exception e) {
            log.info("Exception trying to retrieve blogs, retrieving all vacancies ");
        }
        model.addAttribute("blogs", allBlogs);
        return "/fragments/blog/blog-page";
    }
    @GetMapping("/add-blog")
    public String showCreateBlogForm(Model model) {
        model.addAttribute("blog", new Blog());
        //List<Employee> employees = recruitmentZoneService.getEmployees();
        //model.addAttribute("employeID", employeID);
        return "fragments/blog/add-blog";
    }
    @PostMapping("/view-blog")
    public String showBlog(@RequestParam("blogID") Long blogID, Model model) {
        Blog optionalBlog = findBlogByID(blogID);
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
        saveNewBlog(name,blog);
        return "redirect:/blog-administration";
    }
    @PostMapping("/update-blog")
    public String updateBlog(@RequestParam("blogID") Long blogID, Model model) {
        Blog blog = findBlogByID(blogID);
        model.addAttribute("blog", blog);
        model.addAttribute("status", BlogStatus.values());
        return "fragments/blog/update-blog";
    }
    @PostMapping("/save-updated-blog")
    public String saveUpdatedBlog(@Valid @ModelAttribute("blog")Blog blog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/blog/update-blog";
        }
        saveBlog(blog);
        return "redirect:/blog-administration";
    }
    @GetMapping("/blog-administration")
    public String blogAdministration(Model model) {
        List<Blog> allBlogs = new ArrayList<>();
        try {
            allBlogs = getBlogs();
        } catch (Exception e) {
            log.info("Exception trying to retrieve blogs, retrieving all vacancies ");
        }
        model.addAttribute("blogs", allBlogs);
        return "/fragments/blog/blog-administration";
    }


    public void saveNewBlog(String name, Blog bg) {
        Optional<Employee> op = employeeService.findEmployeeByUserName(name);
        op.ifPresent(bg::setEmployee);
        blogService.save(bg);
        // publish event
    }

    public void saveBlog(Blog blog) {
        Optional<Blog> optionalBlog = blogService.findById(blog.getBlogID());
        // title
        if (optionalBlog.isPresent()) {
            Blog b = optionalBlog.get();
            if (!b.getBlog_title().equalsIgnoreCase(blog.getBlog_title())) {
                b.setBlog_title(blog.getBlog_title());
            }
            // description
            if (!b.getBlog_description().equalsIgnoreCase(blog.getBlog_description())) {
                b.setBlog_description(blog.getBlog_description());
            }
            // body
            if (!b.getBody().equalsIgnoreCase(blog.getBody())) {
                b.setBody(blog.getBlog_description());
            }
            // publish date

            if (!Objects.equals(b.getPublish_date(), blog.getPublish_date())) {
                b.setPublish_date(blog.getPublish_date());
            }
            // expiration date
            if (!Objects.equals(b.getEnd_date(), blog.getEnd_date())) {
                b.setEnd_date(blog.getEnd_date());
            }
            if (!Objects.equals(b.getStatus(), blog.getStatus())) {
                b.setStatus(blog.getStatus());
            }
        }

        blogService.save(blog);
    }

    public Blog findBlogByID(Long blogID) {
        Blog blog = null;
        Optional<Blog> optionalBlog = blogService.findById(blogID);
        if (optionalBlog.isPresent()) {
            blog = optionalBlog.get();
        }
        return blog;
    }

    public List<Blog> getActiveBlogs() {
        return blogService.getActiveBlogs(BlogStatus.ACTIVE);
    }

    public List<Blog> getBlogs() {
        return blogService.getBlogs();
    }


}
