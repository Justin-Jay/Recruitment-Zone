package za.co.RecruitmentZone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;
import za.co.RecruitmentZone.entity.domain.*;
import za.co.RecruitmentZone.service.domainServices.*;

import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentZoneService {
    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneService.class);
    private final ApplicationService applicationService;
    private final BlogService blogService;
    private final CandidateService candidateService;
    private final EmployeeService employeeService;
    private final VacancyService vacancyService;

    public RecruitmentZoneService(ApplicationService applicationService, VacancyService vacancyService,BlogService blogService, CandidateService candidateService,
                                  EmployeeService employeeService) {
        this.applicationService = applicationService;
        this.vacancyService = vacancyService;
        this.blogService = blogService;
        this.candidateService = candidateService;
        this.employeeService = employeeService;
    }

    // BLOGS

    public List<Blog> getBlogs() {
        return blogService.getBlogs();
    }

    public Blog createBlog(Blog blog){
        return blogService.createBlog(blog);
    }

    // CANDIDATE

    public List<Candidate> getCandidates() {
        return candidateService.getCandidates();
    }

    // EMPLOYEE

    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }


    // VACANCY
    public List<Vacancy> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }
    public List<Vacancy> getActiveVacancies(String status) {
        return vacancyService.getActiveVacancies(status);
    }

    public Vacancy saveVacancy(Vacancy vacancy){
        return vacancyService.save(vacancy);
    }


    // APPLICATIONS
    public List<Application> getApplications() {
        return applicationService.findApplications();
    }

    public boolean deleteVacancy(Long id) {
        try {
            vacancyService.deleteVacancy(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Vacancy> getEmployeeVacancies(Integer employee_id) {
        return vacancyService.getEmployeeVacancies(employee_id);
    }

    public Vacancy findVacancyById(Long vacancyID) {  // Changed from Long to Integer
        Vacancy vacancy = null;
        Optional<Vacancy> optionalVacancy = vacancyService.findById(vacancyID);
        if (optionalVacancy.isPresent()){
            vacancy = optionalVacancy.get();
        }
        return vacancy;
    }



    // update the vacancy status to expired using repository

    public boolean setVacancyStatusToExpired(Long vacancyID) {
        Optional<Vacancy> optionalVacancy = vacancyService.findById(vacancyID);
        if (optionalVacancy.isPresent()) {
            Vacancy vacancy = optionalVacancy.get();
            vacancy.setStatus("EXPIRED");
            vacancyService.save(vacancy);
            return true;
        } else {
            // handle the case where the vacancy is not found
            return false;
        }
    }

    public void updateStatus(Long id, String newStatus) throws Exception {
        Vacancy vacancy = vacancyService.findById(id)
                .orElseThrow(() -> new Exception("Vacancy not found"));
        vacancy.setStatus(newStatus);
        vacancyService.save(vacancy);
    }

/*    public boolean createVacancy(Vacancy vacancy) {
        // Create a new Vacancy entity and populate it from the DTO
        Vacancy newVacancy = new Vacancy();
        newVacancy.setJobID(vacancy.getJobID());
        newVacancy.setExpired(vacancy.isExpired());
        newVacancy.setActive(vacancy.isActive());
        newVacancy.setPending(vacancy.isPending());

        try {
            return vacancyService.save(newVacancy);
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
        // Save the new Vacancy entity to the database

    }*/

    /*    public boolean publishVacancyCreateEvent(String json) {
        boolean result = false;
        try {
            Gson gson = new Gson();
            Vacancy newVacancy = gson.fromJson(json, Vacancy.class);
            log.info("User Saved");
            result = vacancyEventPublisher.publishVacancyCreateEvent(newVacancy);

        } catch (Exception e) {
            log.info("Unable to save" + e.getMessage());
        }
        return result;
    }*/
   /*  public boolean publishAmendedVacancyEvent(String json) {
        boolean result = false;
        try {
            Gson gson = new Gson();
            Vacancy newVacancy = gson.fromJson(json, Vacancy.class);
            log.info("User Saved");
            result = vacancyEventPublisher.publishVacancyAmendedEvent(newVacancy);

        } catch (Exception e) {
            log.info("Unable to save" + e.getMessage());
        }
        return result;
    }*/
}
