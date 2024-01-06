package za.co.recruitmentzone.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.recruitmentzone.util.enums.BlogStatus;
import za.co.recruitmentzone.blog.entity.Blog;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long> {

    List<Blog> findBlogsByStatus(BlogStatus status);

}
