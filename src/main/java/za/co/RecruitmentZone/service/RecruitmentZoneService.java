package za.co.RecruitmentZone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.domain.*;
import za.co.RecruitmentZone.events.publisher.Email.EmailEventPublisher;
import za.co.RecruitmentZone.service.domainServices.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RecruitmentZoneService {
    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneService.class);
    private final ApplicationService applicationService;
    private final BlogService blogService;
    private final CandidateService candidateService;
    private final EmployeeService employeeService;
    private final VacancyService vacancyService;
    private final CommunicationService communicationService;
    private final CandidateApplicationService candidateApplicationService;

private final EmailEventPublisher emailEventPublisher;
    public RecruitmentZoneService(ApplicationService applicationService, VacancyService vacancyService, BlogService blogService, CandidateService candidateService,
                                  EmployeeService employeeService, CommunicationService communicationService, CandidateApplicationService candidateApplicationService, EmailEventPublisher emailEventPublisher) {
        this.applicationService = applicationService;
        this.vacancyService = vacancyService;
        this.blogService = blogService;
        this.candidateService = candidateService;
        this.employeeService = employeeService;
        this.communicationService = communicationService;
        this.candidateApplicationService = candidateApplicationService;
        this.emailEventPublisher = emailEventPublisher;
    }

    // BLOGS
// findBlogByID / saveBlog / findBlogByID
    public List<Blog> getBlogs() {
        return blogService.getBlogs();
    }

    public void saveBlog(Blog blog) {
        blogService.save(blog);
    }

    public Blog findBlogByID(Long blogID) {
        Blog blog = null;
        Optional<Blog> optionalBlog = blogService.findById(blogID);
        if (optionalBlog.isPresent()) {
            blog = optionalBlog.get();
        }
        return blog;
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

    public void saveVacancy(Vacancy vacancy) {
        vacancyService.save(vacancy);
    }

    public List<Vacancy> getActiveVacancies() {
        return vacancyService.getActiveVacancies();
    }

    public Vacancy findVacancyById(Long vacancyID) {  // Changed from Long to Integer
        Vacancy vacancy = null;
        Optional<Vacancy> optionalVacancy = vacancyService.findById(vacancyID);
        if (optionalVacancy.isPresent()) {
            vacancy = optionalVacancy.get();
        }
        return vacancy;
    }

    public boolean deleteVacancy(Long id) {
        try {
            vacancyService.deleteVacancy(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // EMPLOYEES
    //  createEmployee(dto) / getEmployeeDTO(id) updateExistingEmployee(dto)

    public void saveEmployee(EmployeeDTO employeeDTO) {
        employeeService.createEmployee(employeeDTO);
    }

    public void saveEmployee(Employee employee) {
        employeeService.save(employee);
    }

    public EmployeeDTO getEmployeeDTO(Long employeeID) {
        return employeeService.convertToDTO(employeeID);
    }

    public void updateExistingEmployee(Long employeeID, EmployeeDTO employeeDTO) {
        log.info("--Attempting updateExistingEmployee ---");
        employeeService.updateExistingEmployee(employeeID, employeeDTO);
    }

    public Employee findEmployeeByID(Long employeeID) {
        Employee employee = null;
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByID(employeeID);
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
        }
        return employee;
    }

    // APPLICATIONS
    public List<Application> getApplications() {
        return applicationService.findApplications();
    }



    public void websiteQueryReceived(ContactMessage message) {
        // send message using virtual thread
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()){
            log.info("About to submit");
            executor.submit(() -> {
                // Perform repo IO operation
                communicationService.sendSimpleEmail(message);
            });
        } catch (Exception e) {
            log.info("Failed to send Email Query");
        }

        // publish event
        //emailEventPublisher.publishWebsiteQueryReceivedEvent(message);

        log.info("Website Query received");
        log.info(message.toString());
    }

    public boolean saveSubmission(Candidate candidate) {

        candidate = candidateService.save(candidate);

        Application application = new Application();
        LocalDate date = LocalDate.now();
        application.setDate_received(date.toString());
        application = applicationService.save(application);

        CandidateApplication ca = new CandidateApplication();
        ca.setApplicationID(application.getApplicationID());
        ca.setCandidateID(candidate.getCandidateID());
        candidateApplicationService.save(ca);
        return true;
    }
    // update the vacancy status to expired using repository


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
