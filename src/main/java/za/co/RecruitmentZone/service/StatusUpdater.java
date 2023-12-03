package za.co.RecruitmentZone.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.entity.Enums.BlogStatus;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;
import za.co.RecruitmentZone.entity.domain.Blog;
import za.co.RecruitmentZone.entity.domain.Vacancy;
import za.co.RecruitmentZone.repository.BlogRepository;
import za.co.RecruitmentZone.repository.VacancyRepository;

import java.time.LocalDate;
import java.util.List;



@Component
@EnableScheduling
public class StatusUpdater {
    private final Logger log = LoggerFactory.getLogger(StatusUpdater.class);

    private final VacancyRepository vacancyRepository;
    private final BlogRepository blogRepository;

    private final ApplicationEventPublisher eventPublisher;

    public StatusUpdater(VacancyRepository vacancyRepository, BlogRepository blogRepository, ApplicationEventPublisher eventPublisher) {
        this.vacancyRepository = vacancyRepository;
        this.blogRepository = blogRepository;
        this.eventPublisher = eventPublisher;
    }

     //@Scheduled(fixedRate = 120000) // once every two minutes
       // once at midnight
   // @Scheduled(fixedRate = 3000)
    // @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateVacancyStatus() {
        log.info("<-----Vacancy Status Updater---->");
        LocalDate currentDate = LocalDate.now();

        List<Vacancy> vacancies = (List<Vacancy>) vacancyRepository.findAll();

        for (Vacancy vacancy : vacancies) {
            log.info("<-----Found {} Vacancies---->", vacancies.size());
            LocalDate vacancyPublishDate = LocalDate.parse(vacancy.getPublish_date());
            LocalDate vacancyExpirationDate = LocalDate.parse(vacancy.getEnd_date());


            if (vacancyPublishDate.isBefore(currentDate) && vacancy.getStatus() != VacancyStatus.EXPIRED) {
                if (vacancy.getStatus() != VacancyStatus.ACTIVE) {
                    vacancy.setStatus(VacancyStatus.ACTIVE);
                    // save
                    vacancyRepository.save(vacancy);
                }
            }

            if (vacancyExpirationDate.isBefore(currentDate) && vacancy.getStatus() != VacancyStatus.PENDING) {
                if (vacancy.getStatus() != VacancyStatus.EXPIRED) {
                    vacancy.setStatus(VacancyStatus.EXPIRED);
                    // save
                    vacancyRepository.save(vacancy);
                }
            }

            if (vacancy.getStatus() == VacancyStatus.PENDING && vacancyPublishDate.isBefore(currentDate))  {
                vacancy.setStatus(VacancyStatus.ACTIVE);
                // save
                vacancyRepository.save(vacancy);
            }

            if (vacancy.getStatus() == VacancyStatus.EXPIRED && vacancyPublishDate.isBefore(currentDate) && !vacancyExpirationDate.isBefore(currentDate))  {
                    vacancy.setStatus(VacancyStatus.ACTIVE);
                // save
                vacancyRepository.save(vacancy);
            }

        }
        log.info("<-----Vacancy Status Updater----DONE--->");
    }

    //@Scheduled(fixedRate = 120000) // once every two minutes
    // once at midnight
    // @Scheduled(fixedRate = 3000)
    // @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateBlogStatus() {
        log.info("<-----Blog Status Updater---->");
        LocalDate currentDate = LocalDate.now();

        List<Blog> blogs = (List<Blog>) blogRepository.findAll();

        for (Blog blog : blogs) {
            log.info("<-----Found {} Vacancies---->", blogs.size());
            LocalDate blogPublishDate = LocalDate.parse(blog.getPublish_date());
            LocalDate blogExpirationDate = LocalDate.parse(blog.getEnd_date());


            if (blogPublishDate.isBefore(currentDate) && blog.getStatus() != BlogStatus.EXPIRED) {
                if (blog.getStatus() != BlogStatus.ACTIVE) {
                    blog.setStatus(BlogStatus.ACTIVE);
                    // save
                    blogRepository.save(blog);
                }
            }

            if (blogExpirationDate.isBefore(currentDate) && blog.getStatus() != BlogStatus.PENDING) {
                if (blog.getStatus() != BlogStatus.EXPIRED) {
                    blog.setStatus(BlogStatus.EXPIRED);
                    // save
                    blogRepository.save(blog);
                }
            }

            if (blog.getStatus() == BlogStatus.PENDING && blogExpirationDate.isBefore(currentDate))  {
                blog.setStatus(BlogStatus.ACTIVE);
                // save
                blogRepository.save(blog);
            }

            if (blog.getStatus() == BlogStatus.EXPIRED && blogPublishDate.isBefore(currentDate) && !blogExpirationDate.isBefore(currentDate))  {
                blog.setStatus(BlogStatus.ACTIVE);
                // save
                blogRepository.save(blog);
            }

        }
        log.info("<-----Blog Status Updater----DONE--->");
    }

   /*  @Scheduled(fixedRate = 3000)
    @Transactional
    public void loadEmployeeVacancies() {
        log.info("<-----Blog Status Updater---->");
        LocalDate currentDate = LocalDate.now();

        List<Blog> blogs = (List<Blog>) blogRepository.findAll();

        for (Blog blog : blogs) {
            log.info("<-----Found {} Vacancies---->", blogs.size());
            LocalDate blogPublishDate = LocalDate.parse(blog.getPublish_date());
            LocalDate blogExpirationDate = LocalDate.parse(blog.getEnd_date());


            if (blogPublishDate.isBefore(currentDate) && blog.getStatus() != BlogStatus.EXPIRED) {
                if (blog.getStatus() != BlogStatus.ACTIVE) {
                    blog.setStatus(BlogStatus.ACTIVE);
                    // save
                    blogRepository.save(blog);
                }
            }

            if (blogExpirationDate.isBefore(currentDate) && blog.getStatus() != BlogStatus.PENDING) {
                if (blog.getStatus() != BlogStatus.EXPIRED) {
                    blog.setStatus(BlogStatus.EXPIRED);
                    // save
                    blogRepository.save(blog);
                }
            }

            if (blog.getStatus() == BlogStatus.PENDING && blogExpirationDate.isBefore(currentDate))  {
                blog.setStatus(BlogStatus.ACTIVE);
                // save
                blogRepository.save(blog);
            }

            if (blog.getStatus() == BlogStatus.EXPIRED && blogPublishDate.isBefore(currentDate) && !blogExpirationDate.isBefore(currentDate))  {
                blog.setStatus(BlogStatus.ACTIVE);
                // save
                blogRepository.save(blog);
            }

        }
        log.info("<-----Blog Status Updater----DONE--->");
    }*/
}




