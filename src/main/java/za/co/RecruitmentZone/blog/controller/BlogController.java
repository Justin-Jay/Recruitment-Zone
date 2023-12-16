package za.co.RecruitmentZone.blog.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.util.Enums.BlogStatus;
import za.co.RecruitmentZone.blog.entity.Blog;
import za.co.RecruitmentZone.blog.dto.BlogDTO;
import za.co.RecruitmentZone.employee.entity.Employee;
import za.co.RecruitmentZone.blog.service.BlogService;
import za.co.RecruitmentZone.employee.service.EmployeeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static za.co.RecruitmentZone.util.Enums.BlogStatus.ACTIVE;
import static za.co.RecruitmentZone.util.Enums.BlogStatus.PENDING;

@Controller
@RequestMapping("/Blog")
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
        model.addAttribute("blog", new BlogDTO());
        return "fragments/blog/add-blog";
    }

    @PostMapping("/view-blog")
    public String showBlog(@RequestParam("blogID") Long blogID, Model model) {
        Blog optionalBlog = findBlogByID(blogID);
        log.info("Looking for {}", blogID);
        log.info(optionalBlog.toString());
        model.addAttribute("blog", optionalBlog);
        // model.addAttribute("author", optionalBlog.getEmployee().getFirst_name());
        return "fragments/blog/view-blog";
    }

    @PostMapping("/save-blog")
    public String saveBlog(@Valid @ModelAttribute("blog")BlogDTO blog, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            log.info("HAS ERRORS");
            return "fragments/blog/add-blog";
        }
        saveNewBlog(blog);
        return "redirect:/Blog/blog-administration";
    }

    @PostMapping("/save-updated-blog")
    public String saveUpdatedBlog(@Valid @ModelAttribute("blog") Blog blog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/blog/update-blog";
        }
        saveBlog(blog);
        return "redirect:/Blog/blog-administration";
    }

    @PostMapping("/update-blog")
    public String updateBlog(@RequestParam("blogID") Long blogID, Model model) {
        Blog blog = findBlogByID(blogID);
        model.addAttribute("blog", blog);
        model.addAttribute("status", BlogStatus.values());
        return "fragments/blog/update-blog";
    }

    @GetMapping("/blog-administration")
    public String blogAdministration(Model model) {
        model.addAttribute("blogs", getBlogs());
        return "/fragments/blog/blog-administration";
    }


    public void saveNewBlog(BlogDTO blogDTO) {
        //Optional<Employee> op = employeeService.findEmployeeByUserName(blogDTO.getEmployee());
        Optional<Employee> op = employeeService.findEmployeeByUserName("Justin.Maboshego@kiunga.co.za");
        Blog newBlog = new Blog();
        newBlog.setBlog_title(blogDTO.getBlog_title());
        newBlog.setBlog_description(blogDTO.getBlog_description());
        newBlog.setBody(blogDTO.getBody());
        newBlog.setEnd_date(blogDTO.getEnd_date().toString());
        newBlog.setPublish_date(blogDTO.getPublish_date().toString());
        LocalDate today = LocalDate.now();
        if(blogDTO.getPublish_date().isAfter(today))
        {
            newBlog.setStatus(PENDING);
        }
        else {
            newBlog.setStatus(ACTIVE);
        }
        op.ifPresent(newBlog::setEmployee);
        blogService.save(newBlog);
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
                b.setBody(blog.getBody());
            }
            // publish date

            if (!Objects.equals(b.getPublish_date(), blog.getPublish_date())) {
                b.setPublish_date(blog.getPublish_date());
            }
            // expiration date
            if (!Objects.equals(b.getEnd_date(), blog.getEnd_date())) {
                b.setEnd_date(blog.getEnd_date());
            }
            if (blog.getStatus() != null) {
                // Only set the status if it is provided and different from the current status
                b.setStatus(blog.getStatus());
            }
            blogService.save(b);
        }


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
