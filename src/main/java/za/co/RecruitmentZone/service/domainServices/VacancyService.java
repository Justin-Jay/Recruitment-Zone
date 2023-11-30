package za.co.RecruitmentZone.service.domainServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;
import za.co.RecruitmentZone.entity.domain.Vacancy;
import za.co.RecruitmentZone.repository.VacancyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final Logger log = LoggerFactory.getLogger(VacancyService.class);

    public VacancyService(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    public List<Vacancy> getAllVacancies(){
        return vacancyRepository.findAll();
    }

    public Optional<Vacancy> findById(Long id){
        return vacancyRepository.findById(id);
    }

    public List<Vacancy> findVacanciesByTitle(String title){
        return vacancyRepository.findVacanciesByJobTitle(title);
    }

    public Vacancy save(Vacancy vacancy){
        return vacancyRepository.save(vacancy);
    }

    public void deleteVacancy(Long id){
        vacancyRepository.deleteById(id);
    }

    public List<Vacancy> getActiveVacancies(VacancyStatus status){
        return vacancyRepository.findVacanciesByStatus(status);
    }

}
