package za.co.recruitmentzone.vacancy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.util.enums.VacancyStatus;
import za.co.recruitmentzone.vacancy.entity.Vacancy;
import za.co.recruitmentzone.vacancy.exception.VacancyException;
import za.co.recruitmentzone.vacancy.exception.VacancyNotFoundException;
import za.co.recruitmentzone.vacancy.repository.VacancyRepository;

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

    public Vacancy findById(Long id) throws VacancyNotFoundException {
        log.info(" <--- VacancyService findById {} ",id);
        Optional<Vacancy> op = vacancyRepository.findById(id);
        return op.orElseThrow(()-> new VacancyNotFoundException("Vacancy not found: "+id));
    }

    public List<Vacancy> findVacanciesByTitle(String title){
        log.info(" <--- VacancyService findVacanciesByTitle {} ",title);
        return vacancyRepository.findVacanciesByJobTitle(title);
    }

    public Vacancy save(Vacancy vacancy){
        log.info(" <--- VacancyService save {} ",vacancy.printVacancy());
        return vacancyRepository.save(vacancy);
    }

    public void deleteVacancy(Long id) {
        log.info(" <--- VacancyService deleteVacancy {} ",id);
        vacancyRepository.deleteById(id);
    }

    public List<Vacancy> getActiveVacancies(VacancyStatus status){
        log.info(" <--- VacancyService getActiveVacancies {} ",status);
        return vacancyRepository.findVacanciesByStatus(status);
    }

    public List<Vacancy> findByClient(Client client){
        log.info(" <--- VacancyService findByClient {} ",client.printClient());
        return vacancyRepository.findByClient(client);
    }


    public Page<Vacancy> findPaginatedVacancies(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return vacancyRepository.findAll(pageable);
    }
}
