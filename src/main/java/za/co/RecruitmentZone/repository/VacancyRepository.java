package za.co.RecruitmentZone.repository;


import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import za.co.RecruitmentZone.Entity.Vacancy;

@EnableJpaRepositories
public interface VacancyRepository  extends CrudRepository<Vacancy,Integer> {
}
