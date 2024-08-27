package za.co.recruitmentzone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.repository.EmployeeRepository;
import za.co.recruitmentzone.vacancy.entity.Vacancy;
import za.co.recruitmentzone.vacancy.exception.VacancyException;
import za.co.recruitmentzone.vacancy.service.VacancyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/RecruitmentZone/api")
public class RecruitmentZoneAPIController {

    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneAPIController.class);

    private final JobLauncher jobLauncher;
    private final Job vacancyJob;

    private final VacancyService vacancyService;
    private final EmployeeRepository employeeRepository;

    public RecruitmentZoneAPIController(JobLauncher jobLauncher, Job vacancyJob, VacancyService vacancyService, EmployeeRepository employeeRepository) {
        this.jobLauncher = jobLauncher;
        this.vacancyJob = vacancyJob;
        this.vacancyService = vacancyService;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/start-batch-job")
    public ResponseEntity<String> batchJobEntry() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("created", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(vacancyJob, jobParameters);
            return new ResponseEntity<>("Batch Started Successfully", HttpStatus.OK);
        } catch (JobInstanceAlreadyCompleteException |
                 JobExecutionAlreadyRunningException |
                 JobParametersInvalidException |
                 JobRestartException
                e) {
            log.info("Failed {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAllVacancies")
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();
        if (!vacancies.isEmpty()) {
            return new ResponseEntity<>(vacancies, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteVacancy/{id}")
    public ResponseEntity<String> deleteVacancy(@PathVariable Long id) {
        try {
            vacancyService.deleteVacancy(id);
            return new ResponseEntity<>("Vacancy Deleted", HttpStatus.CREATED);
        } catch (Exception exception) {
            log.info("Error while deleting vacancy", exception);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/findEmployeeByEmail/")
    public ResponseEntity<Employee> findEmployeeByEmail(String empName) {
        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByUsername(empName);
        if (optionalEmployee.isPresent()) {
            return new ResponseEntity<>(optionalEmployee.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

/*    public ResponseEntity<List<ROLE>> getAuthorities(Employee e) {
        List<ROLE> auths = employeeRepository.findEmployeeAuthorities(e);
        if (auths != null) {
            return new ResponseEntity<>(auths, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
 /*   public ResponseEntity<String> getVacancyName(Long id) {
        String result = recruitmentZoneService.findVacancyTitleByID(id);
        if (result.length() > 0) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else throw new VacancyException("Vacancy id : " + id + " not found");
    }
*/

/*
    public ResponseEntity<List<Application>> getApplications() {
        List<Application> applications = recruitmentZoneService.getApplications();
        if (applications != null) {
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } else {
            throw new ApplicationsNotFoundException("Applications Not Found: please contact system administrator");
        }
    }
*/


/*
    public ResponseEntity<Application> findApplicationByID(Long appID) {
        Application application = recruitmentZoneService.findApplicationByID(appID);
        if (application != null) {
            return new ResponseEntity<>(application, HttpStatus.OK);
        } else {
            throw new ApplicationsNotFoundException("Application Not Found");
        }
    }
*/

  /*  public ResponseEntity<String> createCandidateApplication(NewApplicationDTO newApplicationDTO) {
        log.info("newApplicationDTO : {}", newApplicationDTO);
        Application app = recruitmentZoneService.createCandidateApplication(newApplicationDTO);
        if (app != null) {
            return new ResponseEntity<>("Candidate Application Created: " + app.getApplicationID(), HttpStatus.OK);
        } else {
            throw new CandidateException("Failed to create Candidate Application:");
        }
    }*/

  /*  public ResponseEntity<String> saveUpdatedApplicationStatus(Long appID, ApplicationStatus applicationStatus) {
        Boolean b = recruitmentZoneService.saveUpdatedApplicationStatus(appID, applicationStatus);
        if (b) {
            return new ResponseEntity<>("Application " + applicationStatus + " Saved: " + appID, HttpStatus.OK);
        } else {
            throw new VacancyException("Failed to save status : " + applicationStatus + "For: " + appID);
        }
    }*/

/*    public ResponseEntity<List<Blog>> getBlogs() {
        List<Blog> blogs = recruitmentZoneService.getBlogs();
        if (!blogs.isEmpty()) {
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

 /*   public ResponseEntity<List<Blog>> getActiveBlogs(BlogStatus blogStatus) {
        List<Blog> blogs = recruitmentZoneService.getActiveBlogs(blogStatus);
        if (!blogs.isEmpty()) {
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/



/*    public ResponseEntity<Blog> saveBlog(Blog blog) {
        Blog savedBlog = recruitmentZoneService.saveBlog(blog);
        if (savedBlog != null) {
            return new ResponseEntity<>(savedBlog, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

/*    public ResponseEntity<Blog> findBlogById(Long id) {
        Optional<Blog> optionalBlog = recruitmentZoneService.findBlogById(id);
        if (optionalBlog.isPresent()) {
            return new ResponseEntity<>(optionalBlog.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

 /*   public ResponseEntity<Candidate> findCandidateByID(Long id) {
        Optional<Candidate> optionalCandidate = recruitmentZoneService.findCandidateByID(id);
        if (optionalCandidate.isPresent()) {
            return new ResponseEntity<>(optionalCandidate.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

  /*  public ResponseEntity<CandidateFile> createCandidateFile(CandidateFileDTO candidateFileDTO) {
        CandidateFile optionalCandidate = recruitmentZoneService.createCandidateFile(candidateFileDTO);
        if (optionalCandidate != null) {
            return new ResponseEntity<>(optionalCandidate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

/*    public ResponseEntity<String> saveCandidateFile(CandidateFileDTO candidateFileDTO) {
        String fileUploadResult = recruitmentZoneService.saveFile(candidateFileDTO);
        if (fileUploadResult.equalsIgnoreCase("File uploaded Success")) {
            return new ResponseEntity<>(fileUploadResult, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

/*    public ResponseEntity<Boolean> saveCandidate(Candidate candidate) {
        boolean result = recruitmentZoneService.saveCandidate(candidate);
        if (result) {
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
  /*  public ResponseEntity<Boolean> saveVacancy(VacancyDTO vacancy) {
        Vacancy response = recruitmentZoneService.saveNewVacancy(vacancy);
        if (response!=null) {
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

/*    public ResponseEntity<List<Candidate>> getCandidates() {
        List<Candidate> candidates = recruitmentZoneService.findCandidate();
        if (!candidates.isEmpty()) {
            return new ResponseEntity<>(candidates, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

/*
    public ResponseEntity<Client> findClientByID(Long clientID) {
        Client client = recruitmentZoneService.findClientByID(clientID);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
*/

 /*   public ResponseEntity<Boolean> saveClient(Client client) {
        boolean saved = recruitmentZoneService.saveClient(client);
        if (saved) {
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }
        return new ResponseEntity<>(Boolean.FALSE,HttpStatus.BAD_REQUEST);
    }*/
/*    public ResponseEntity<ContactPerson> saveUpdatedContactPerson(Long contactPersonID, ContactPersonDTO contactPersonDTO){
        ContactPerson contactPerson = recruitmentZoneService.saveUpdatedContactPerson(contactPersonID,contactPersonDTO);
        if (contactPerson!=null) {
            return new ResponseEntity<>(contactPerson, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

/*    public ResponseEntity<List<Client>> getClients(){
        List<Client> clients = recruitmentZoneService.findAllClients();
        if (clients!=null) {
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/


/*    public ResponseEntity<ContactPerson> findContactPersonByID(Long clientID) {
        ContactPerson contactPerson = recruitmentZoneService.findContactPersonByID(clientID);
        if (contactPerson!=null) {
            return new ResponseEntity<>(contactPerson, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
/*    public ResponseEntity<List<ContactPerson>> findContactPersonsByClientID(Long clientID) {
        List<ContactPerson> contactPersonList = recruitmentZoneService.findContactPersonsByClientID(clientID);
        if (!contactPersonList.isEmpty()) {
            return new ResponseEntity<>(contactPersonList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

/*
    public ResponseEntity<Client> saveNewClient(ClientDTO clientDTO) {
        Client client = recruitmentZoneService.saveNewClient(clientDTO);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
*/

/*    public ResponseEntity<Client> saveUpdatedClient(Long clientID,Client client) {
        Client updated = recruitmentZoneService.saveUpdatedClient(clientID,client);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

/*    public ResponseEntity<ContactPerson> addContactToClient(ContactPersonDTO contactPersonDTO) {
        ContactPerson contactPerson = recruitmentZoneService.addContactToClient(contactPersonDTO);
        if (contactPerson != null) {
            return new ResponseEntity<>(contactPerson, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

/*    public ResponseEntity<List<String>> searchFiles(String term) {
        List<String> fileList = recruitmentZoneService.searchFiles(term);
        if (fileList != null) {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

/*    public ResponseEntity<List<String>> searchFileContent(String term) {
        List<String> fileList = recruitmentZoneService.searchFileContent(term);
        if (fileList != null) {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
/*    public ResponseEntity<String> getDocumentLocation(String names) {
        String fileList = recruitmentZoneService.getDocumentLocation(names);
        if (fileList != null) {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/


/*    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> responseList = recruitmentZoneService.findAllEmployees();
        if (responseList != null) {
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
/*    public ResponseEntity<Employee> findEmployeeByID(Long empID) {
        Employee employee = recruitmentZoneService.findEmployeeByID(empID);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
/*    public ResponseEntity<Employee> saveNewEmployee(EmployeeDTO employeeDTO, HttpServletRequest request) throws UserAlreadyExistsException {
        Employee e = recruitmentZoneService.saveNewEmployee(employeeDTO,request);
        if (e != null) {
            return new ResponseEntity<>(e, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
/*
    public ResponseEntity<Employee> saveUpdatedEmployee(Employee employee) {
        Employee e = recruitmentZoneService.saveUpdatedEmployee(employee);
        if (e != null) {
            return new ResponseEntity<>(e, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
*/


   /* public ResponseEntity<Vacancy> findVacancyById(Long appID) {
        Vacancy vacancy = recruitmentZoneService.findVacancyById(appID);
        if (vacancy != null) {
            return new ResponseEntity<>(vacancy, HttpStatus.OK);
        } else {
            throw new ApplicationsNotFoundException("Application Not Found");
        }
    }*/


}
