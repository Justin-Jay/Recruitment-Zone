package za.co.RecruitmentZone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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
import java.util.Objects;
import java.util.Optional;


@Service
public class RecruitmentZoneService {
    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneService.class);
    private final ApplicationService applicationService;
    private final BlogService blogService;
    private final CandidateService candidateService;
    private final EmployeeService employeeService;
    private final VacancyService vacancyService;
    private final CommunicationService communicationService;
    private final ApplicationsEventPublisher applicationsEventPublisher;
    private final StorageService storageService;
    private final EmailEventPublisher emailEventPublisher;
    private final ClientService clientService;


    public RecruitmentZoneService(ApplicationService applicationService, VacancyService vacancyService, BlogService blogService, CandidateService candidateService,
                                  EmployeeService employeeService, CommunicationService communicationService, EmailEventPublisher emailEventPublisher, ApplicationsEventPublisher applicationsEventPublisher, StorageService storageService, ClientService clientService) {
        this.applicationService = applicationService;
        this.vacancyService = vacancyService;
        this.blogService = blogService;
        this.candidateService = candidateService;
        this.employeeService = employeeService;
        this.communicationService = communicationService;
        this.emailEventPublisher = emailEventPublisher;
        this.applicationsEventPublisher = applicationsEventPublisher;
        this.storageService = storageService;
        this.clientService = clientService;
    }

    // BLOGS


    public void saveNewBlog(String name, Blog bg) {
        Optional<Employee> op = employeeService.findByUsername(name);
        op.ifPresent(employee -> bg.setEmployeeID(employee.getEmployeeID()));
        blogService.save(bg);
    }

    public void saveBlog(Blog blog) {
        Optional<Blog> optionalBlog = blogService.findById(blog.getBlogID());
        // title
        if (optionalBlog.isPresent()) {
            Blog b = optionalBlog.get();
            if (!b.getBlog_title().equalsIgnoreCase(blog.getBlog_title())) {
                b.setBlog_title(blog.getBlog_title());
            }
            // description
            if (!b.getBlog_description().equalsIgnoreCase(blog.getBlog_description())) {
                b.setBlog_description(blog.getBlog_description());
            }
            // body
            if (!b.getBody().equalsIgnoreCase(blog.getBody())) {
                b.setBody(blog.getBlog_description());
            }
            // publish date

            if (!Objects.equals(b.getPublish_date(), blog.getPublish_date())) {
                b.setPublish_date(blog.getPublish_date());
            }
            // expiration date
            if (!Objects.equals(b.getEnd_date(), blog.getEnd_date())) {
                b.setEnd_date(blog.getEnd_date());
            }
            if (!Objects.equals(b.getStatus(), blog.getStatus())) {
                b.setStatus(blog.getStatus());
            }
        }

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

    public List<Blog> getActiveBlogs() {
        return blogService.getActiveBlogs(BlogStatus.ACTIVE);
    }

    public List<Blog> getBlogs() {
        return blogService.getBlogs();
    }


    // CANDIDATE

    public List<Candidate> getCandidates() {
        return candidateService.getCandidates();
    }


    // EMPLOYEE


    public void saveNewEmployee(EmployeeDTO employeeDTO) {
        employeeService.createEmployee(employeeDTO);
    }

    public void saveUpdatedEmployee(Employee updated) {
        Employee employee = null;
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByID(updated.getEmployeeID());
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
            if (!updated.getFirst_name().equalsIgnoreCase(employee.getFirst_name())) {
                employee.setFirst_name(updated.getFirst_name());
            }

            if (!updated.getContact_number().equalsIgnoreCase(employee.getContact_number())) {
                employee.setContact_number(updated.getContact_number());
            }

            if (!updated.getLast_name().equalsIgnoreCase(employee.getLast_name())) {
                employee.setLast_name(updated.getLast_name());
            }

        }
        employeeService.save(employee);
    }

    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    public Employee findEmployeeByID(Long employeeID) {
        Employee employee = null;
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByID(employeeID);
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
        }
        return employee;
    }


    /* public void updateExistingEmployee(Long employeeID, EmployeeDTO employeeDTO) {
         log.info("--Attempting updateExistingEmployee ---");
         employeeService.updateExistingEmployee(employeeID, employeeDTO);
     }*/
    // VACANCY
    public void saveNewVacancy(Long employeeID, Vacancy vacancy) {
        // create vacancy
        vacancy.setStatus(VacancyStatus.PENDING);
        Optional<Employee> op = employeeService.findEmployeeByID(employeeID);
        op.ifPresent(employee -> vacancy.setEmployeeID(employee.getEmployeeID()));
        vacancyService.save(vacancy);
    }

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
        Vacancy vacancy = vacancyService.findById(vacancyID);
        if (vacancy != null) {
            return vacancy;
        }
        return vacancy;
    }

    public String getVacancyName(Long id) {
        Vacancy op = vacancyService.findById(id);
        if (op != null) {
            return op.getJob_title();
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


    public List<Vacancy> searchVacancyByTitle(String title) {
        return vacancyService.findVacanciesByTitle(title);
    }


    // APPLICATIONS

    public Long createCandidateApplication(Long vacancyID, Candidate candidate) {
        // create candidate and save
        candidate = candidateService.save(candidate);
        // create application  and link candidate


        Application application = new Application();
        LocalDate date = LocalDate.now();
        application.setDate_received(date.toString());
        application.setSubmission_date(date.toString());
        application.setCandidateID(candidate.getCandidateID());
        application.setStatus(ApplicationStatus.PENDING);

        // link application with vacancy
        application.setVacancyID(vacancyID);

        applicationService.save(application);

        return candidate.getCandidateID();
    }

    public List<Application> getApplications() {
        return applicationService.findApplications();
    }

    public Application getApplicationByID(Long applicationID) {
        Optional<Application> optionalApplication = applicationService.findApplicationByID(applicationID);
        return optionalApplication.orElse(null);


    }

    public boolean saveUpdatedApplicationStatus(Long applicationID, ApplicationStatus applicationStatus) {
        return applicationService.saveUpdatedStatus(applicationID, applicationStatus);
    }

    public boolean saveSubmissionEvent(Candidate candidate, Long vacancyID) {
        return applicationsEventPublisher.publishSaveSubmissionEvent(candidate, vacancyID);
    }

    // COMMUNICATION

    public void websiteQueryReceived(ContactMessage message) {
        // send message using virtual thread
       /* try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            log.info("About to submit");
            executor.submit(() -> {
                // Send website query received notification
                // Perform repo IO operation
              //  communicationService.sendSimpleEmail(message);
                communicationService.sendWebsiteQuery(message);
            });
        } catch (Exception e) {
            log.info("Failed to send Email Query");
        }*/
        // Send Acknowledgment to candidate
        // publish event
        emailEventPublisher.publishWebsiteQueryReceivedEvent(message);

        log.info("Website Query received");
        log.info(message.toString());
    }

    // STORAGE

    public String saveFile(Long vacancyID, MultipartFile file) {
        return storageService.uploadFile(vacancyID, file);
    }

    public String saveTempFile() throws IOException {
        log.info("Entering Test Method");
        return storageService.testMethod();
    }

    public boolean publishFileUploadedEvent(MultipartFile file, Long candidateID, Long vacancyID) {
        return applicationsEventPublisher.publishFileUploadEvent(file, candidateID, vacancyID);
    }
    // update the vacancy status to expired using repository

    // CLIENTS

    public void saveNewClient(Client client, ContactPerson contactPerson) {
        clientService.saveClient(client,contactPerson);
    }

    public List<Client> getClients() {
        return clientService.findAllClients();
    }

    public Client findClientByID(Long clientID) {
        return clientService.findClientByID(clientID);
    }

    public List<ContactPerson> findContactsByClientID(Long clientID) {
        return clientService.findContactsByClientID(clientID);
    }

    public void addContactToClient(Long clientID, ContactPerson contactPerson) {
       // contactPerson.setClientID(clientID);
        clientService.addContactToClient(clientID,contactPerson);
    }
    public void saveUpdatedClient(Client client){
        clientService.saveUpdatedClient(client);
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
