package za.co.RecruitmentZone.service.domainServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.domain.Blog;
import za.co.RecruitmentZone.repository.BlogRepository;

import java.util.List;

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

    public Blog createBlog(Blog blog){
        return blogRepository.save(blog);
    }

}
