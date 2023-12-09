package za.co.RecruitmentZone.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.RecruitmentZone.util.Enums.BlogStatus;
import za.co.RecruitmentZone.blog.entity.Blog;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long> {

    List<Blog> findBlogsByStatus(BlogStatus status);

}
