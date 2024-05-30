package za.co.recruitmentzone.blog.events;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import za.co.recruitmentzone.blog.entity.Blog;
import za.co.recruitmentzone.blog.service.BlogService;
import za.co.recruitmentzone.util.enums.BlogStatus;

import java.time.LocalDate;
import java.util.List;


@EnableScheduling
public class BlogStatusUpdater {

    private final BlogService blogService;

    public BlogStatusUpdater(BlogService blogService) {
        this.blogService = blogService;
    }


   // @Scheduled(cron = "0 0 0 * * ?")
    public void activateBlogStatus() {

        List<Blog> blogList = blogService.getBlogs();
        LocalDate today = LocalDate.now();

        for (Blog blog : blogList) {

            if (blog.getStatus() == BlogStatus.ACTIVE){
                // check what should be EXPIRED
                if (blog.getEnd_date().isBefore(today)) {
                    blog.setStatus(BlogStatus.EXPIRED);
                    blogService.save(blog);
                }
            }


            if (blog.getStatus() == BlogStatus.PENDING) {
                // check what should be ACTIVE
                if (blog.getPublish_date().isAfter(today)
                        || blog.getPublish_date().isEqual(today)) {
                    blog.setStatus(BlogStatus.ACTIVE);
                    blogService.save(blog);
                }
            }


        }

    }


}

