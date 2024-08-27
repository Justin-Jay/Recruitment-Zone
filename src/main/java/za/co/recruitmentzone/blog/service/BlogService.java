package za.co.recruitmentzone.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.blog.exception.BlogNotFoundException;
import za.co.recruitmentzone.candidate.entity.Candidate;
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

    public List<Blog> getBlogsByStatus(BlogStatus status) {

        return blogRepository.findBlogsByStatus(status);
    }

    public Blog save(Blog blog){
/*        Optional<Employee> op = employeeService.findByUsername(name);
        op.ifPresent(employee -> bg.setEmployeeID(employee.getEmployeeID()));*/
        return blogRepository.save(blog);
    }

    public Blog findById(Long id) throws BlogNotFoundException {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.orElseThrow(()-> new BlogNotFoundException("Blog not found: "+id));    }


    public Page<Blog> findPaginatedBlogs(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return blogRepository.findAll(pageable);
    }

}
