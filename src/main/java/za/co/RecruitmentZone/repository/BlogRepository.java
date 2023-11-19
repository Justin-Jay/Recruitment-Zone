package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.RecruitmentZone.entity.domain.Blog;

public interface BlogRepository extends JpaRepository<Blog,Long> {


}
