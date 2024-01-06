package za.co.recruitmentzone.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.util.enums.BlogStatus;
import za.co.recruitmentzone.blog.entity.Blog;
import za.co.recruitmentzone.blog.repository.BlogRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final Logger log = LoggerFactory.getLogger(BlogService.class);

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> getBlogs() {
        return blogRepository.findAll();
    }

    public List<Blog> getActiveBlogs(BlogStatus status) {
        return blogRepository.findBlogsByStatus(status);
    }

    public Blog save(Blog blog){
/*        Optional<Employee> op = employeeService.findByUsername(name);
        op.ifPresent(employee -> bg.setEmployeeID(employee.getEmployeeID()));*/
        return blogRepository.save(blog);
    }

    public Optional<Blog> findById(Long id){
        return blogRepository.findById(id);
    }

}
