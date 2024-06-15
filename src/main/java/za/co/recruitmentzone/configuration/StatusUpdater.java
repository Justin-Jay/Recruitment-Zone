package za.co.recruitmentzone.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import za.co.recruitmentzone.blog.entity.Blog;
import za.co.recruitmentzone.blog.service.BlogService;
import za.co.recruitmentzone.util.enums.BlogStatus;
import za.co.recruitmentzone.util.enums.VacancyStatus;
import za.co.recruitmentzone.vacancy.entity.Vacancy;
import za.co.recruitmentzone.vacancy.service.VacancyService;

import java.time.LocalDate;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@EnableScheduling
public class StatusUpdater {
    private final Logger log = LoggerFactory.getLogger(StatusUpdater.class);

    private final VacancyService vacancyService;

    private final BlogService blogService;

    public StatusUpdater(VacancyService vacancyService, BlogService blogService) {
        this.vacancyService = vacancyService;
        this.blogService = blogService;
    }

    // This method will be executed every 5 minutes
    //@Scheduled(cron = "0 */5 * * * *")
    // This method executes every minute
    //@Scheduled(cron = "0 * * * * ?")
    // This method will be executed every day at 1 AM
   // @Scheduled(cron = "0 0 1 * * ?")
    // This method will execute daily at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void runUpdates(){
        runBlogUpdates();
        runVacancyUpdates();
    }

    @EventListener(ApplicationStartedEvent.class)
    public void checkStatuses(){
        runBlogUpdates();
        runVacancyUpdates();
    }


    public void runVacancyUpdates(){
        log.info("About to process vacancy updates ");
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()){
            executor.submit(this::processVacancies); 
        } catch (Exception e) {
            log.debug("Failed to process vacancy updates ",e);
        }

    }

    public void runBlogUpdates() {
        log.info("About to process blog updates ");
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(this::processBlogs); 
        } catch (Exception e) {
            log.debug("Failed to process blog updates: " + e);
        }
    }



    // This method will be executed every 5 minutes
    //@Scheduled(cron = "0 */5 * * * *")
    public void processVacancies() {
        log.info("<--- Updater processVacancies --->");
        log.debug("processVacancies on thread: " + Thread.currentThread());
        List<Vacancy> vacancies =  vacancyService.getAllVacancies();
        log.debug("<--- processVacancies total vacancies  {}  --->", vacancies.size());
        for (Vacancy vacancy : vacancies) {

            log.debug(" vacancy in flight \n {}", vacancy.printVacancy());

            LocalDate today = LocalDate.now();
            LocalDate vacancyPublishDate = vacancy.getPublish_date();
            LocalDate vacancyExpirationDate = vacancy.getEnd_date();

            //  check publish date, if publish date is before today, or equal to today, should be active
            if (vacancy.getStatus() == VacancyStatus.PENDING) {

                if (vacancyPublishDate.isBefore(today) || vacancyPublishDate.isEqual(today)) {
                    vacancy.setStatus(VacancyStatus.ACTIVE);
                    vacancyService.save(vacancy);
                    log.info(" vacancy updated to ACTIVE {} ", vacancy.printVacancy() );
                }
            }
            //  check expiration date, if expiration date is before today, should be expired
            else if (vacancy.getStatus() == VacancyStatus.ACTIVE) {
                if (vacancyExpirationDate.isBefore(today)) {
                    vacancy.setStatus(VacancyStatus.EXPIRED);
                    vacancyService.save(vacancy);
                    log.info(" vacancy updated to EXPIRED {} ", vacancy.printVacancy() );
                }
            }
            else {
                log.debug("vacancy status is EXPIRED... skipping {} ", vacancy.printVacancy() );
            }
        }
    }

    // This method will be executed every 5 minutes
    //@Scheduled(cron = "0 */5 * * * *")
    public void processBlogs() {
        log.info("<--- BlogUpdater processBlogs --->");
        log.debug("processBlogs on thread: " + Thread.currentThread());
        List<Blog> blogs =  blogService.getBlogs();
        log.debug("<--- BlogUpdater total blogs  {}  --->", blogs.size());
        for (Blog blog : blogs) {

            log.debug(" processBlogs in flight \n {}", blog.printBlog());

            LocalDate today = LocalDate.now();
            LocalDate blogPublishDate = blog.getPublish_date();
            LocalDate blogExpirationDate = blog.getEnd_date();

            //  check publish date, if publish date is before today, or equal to today, should be active
            if (blog.getStatus() == BlogStatus.PENDING) {

                if (blogPublishDate.isBefore(today) || blogPublishDate.isEqual(today)) {
                    blog.setStatus(BlogStatus.ACTIVE);
                    blogService.save(blog);
                    log.info(" blog updated to ACTIVE {} ", blog.printBlog() );
                }
            }

            //  check expiration date, if expiration date is before today, should be expired
            else if (blog.getStatus() == BlogStatus.ACTIVE) {
                if (blogExpirationDate.isBefore(today)) {
                    blog.setStatus(BlogStatus.EXPIRED);
                    blogService.save(blog);
                    log.info(" blog updated to EXPIRED {} ", blog.printBlog() );
                }
            }
            else {
                log.debug("blog status is EXPIRED... skipping {} ", blog.printBlog() );
            }
        }




    }


}
