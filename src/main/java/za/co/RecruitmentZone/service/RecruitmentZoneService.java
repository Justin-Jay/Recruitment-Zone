package za.co.RecruitmentZone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.entity.Enums.ApplicationStatus;
import za.co.RecruitmentZone.entity.Enums.BlogStatus;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;
import za.co.RecruitmentZone.entity.domain.*;
import za.co.RecruitmentZone.events.publisher.Applications.ApplicationsEventPublisher;
import za.co.RecruitmentZone.events.publisher.Email.EmailEventPublisher;
import za.co.RecruitmentZone.service.domainServices.*;

import java.io.IOException;
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

    private final ApplicationsEventPublisher applicationsEventPublisher;

    private final StorageService storageService;
    private final EmailEventPublisher emailEventPublisher;

    public RecruitmentZoneService(ApplicationService applicationService, VacancyService vacancyService, BlogService blogService, CandidateService candidateService,
                                  EmployeeService employeeService, CommunicationService communicationService, CandidateApplicationService candidateApplicationService, EmailEventPublisher emailEventPublisher, ApplicationsEventPublisher applicationsEventPublisher, StorageService storageService) {
        this.applicationService = applicationService;
        this.vacancyService = vacancyService;
        this.blogService = blogService;
        this.candidateService = candidateService;
        this.employeeService = employeeService;
        this.communicationService = communicationService;
        this.candidateApplicationService = candidateApplicationService;
        this.emailEventPublisher = emailEventPublisher;
        this.applicationsEventPublisher = applicationsEventPublisher;
        this.storageService = storageService;
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

    public List<Blog> getActiveBlogs(){
        return blogService.getActiveBlogs(BlogStatus.ACTIVE);
    }
    // CANDIDATE

    public List<Candidate> getCandidates() {
        return candidateService.getCandidates();
    }

    // EMPLOYEE

    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    public Employee getEmployeeByID(Long id) {
        Optional<Employee> op = employeeService.findEmployeeByID(2L);
        if(op.isPresent()) {
            return op.get();
        }
        return null;
    }


    // VACANCY
    public List<Vacancy> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    public void saveVacancy(Vacancy vacancy) {
        vacancyService.save(vacancy);
    }

    public List<Vacancy> getActiveVacancies() {
        return vacancyService.getActiveVacancies(VacancyStatus.ACTIVE);
    }

    public Vacancy findVacancyById(Long vacancyID) {  // Changed from Long to Integer
        Vacancy vacancy = null;
        Optional<Vacancy> optionalVacancy = vacancyService.findById(vacancyID);
        if (optionalVacancy.isPresent()) {
            vacancy = optionalVacancy.get();
        }
        return vacancy;
    }

    public String getVacancyName(Long id){
        Optional<Vacancy> op = vacancyService.findById(id);
        if (op.isPresent()){
            return op.get().getJob_title();
        }
        return "";
    }
    public boolean deleteVacancy(Long id) {
        try {
            vacancyService.deleteVacancy(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public List<Vacancy> searchVacancyByTitle(String title){
        return vacancyService.findVacanciesByTitle(title);
    }
    // EMPLOYEES
    //  createEmployee(dto) / getEmployeeDTO(id) updateExistingEmployee(dto)

    public void saveEmployee(EmployeeDTO employeeDTO) {
        employeeService.createEmployee(employeeDTO);
    }

    public void saveEmployee(Employee updated) {
        Employee employee = null;
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByID(updated.getId());
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
            if(!updated.getFirst_name().equalsIgnoreCase(employee.getFirst_name()))
            {
                employee.setFirst_name(updated.getFirst_name());
            }

            if(!updated.getContact_number().equalsIgnoreCase(employee.getContact_number()))
            {
                employee.setContact_number(updated.getContact_number());
            }

            if(!updated.getLast_name().equalsIgnoreCase(employee.getLast_name()))
            {
                employee.setLast_name(updated.getLast_name());
            }

        }
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

    public Application getApplicationByID(Long applicationID) {
        Optional<Application> optionalApplication = applicationService.findApplicationByID(applicationID);
        return optionalApplication.orElse(null);


    }


    public void websiteQueryReceived(ContactMessage message) {
        // send message using virtual thread
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            log.info("About to submit");
            executor.submit(() -> {
                // Perform repo IO operation
                communicationService.sendSimpleEmail(message);
            });
        } catch (Exception e) {
            log.info("Failed to send Email Query");
        }

        // publish event
        emailEventPublisher.publishWebsiteQueryReceivedEvent(message);

        log.info("Website Query received");
        log.info(message.toString());
    }

    public Long saveSubmission(Long vacancyID,Candidate candidate) {
        candidate = candidateService.save(candidate);

        Application application = new Application();
        LocalDate date = LocalDate.now();
        application.setDate_received(date.toString());
        application.setSubmission_date(date.toString());
        application.setCandidateID(candidate.getCandidateID());
        application.setStatus(ApplicationStatus.PENDING);
        application.setVacancyID(vacancyID);

        application = applicationService.save(application);

        CandidateApplication ca = new CandidateApplication();
        ca.setApplicationID(application.getApplicationID());
        ca.setCandidateID(candidate.getCandidateID());

        candidateApplicationService.save(ca);
        return candidate.getCandidateID();
    }

    public boolean saveSubmissionEvent(Candidate candidate, Long vacancyID) {
        return applicationsEventPublisher.publishSaveSubmissionEvent(candidate, vacancyID);
    }

    //
    public boolean saveUpdatedApplication(Application application) {
        Application UpdatedApplication = applicationService.save(application);
        return UpdatedApplication != null;

    }
    public boolean saveUpdatedApplicationStatus(Long applicationID,ApplicationStatus applicationStatus) {
        return applicationService.saveUpdatedStatus(applicationID,applicationStatus);
    }

    // STORAGE

    public String saveFile(Long vacancyID,MultipartFile file){
        return storageService.uploadFile(vacancyID,file);
    }

    public String saveTempFile() throws IOException {
        log.info("Entering Test Method");
        return storageService.testMethod();
    }
    public boolean publishFileUploadedEvent(MultipartFile file,Long candidateID, Long vacancyID) {
        return applicationsEventPublisher.publishFileUploadEvent(file,candidateID, vacancyID);
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
