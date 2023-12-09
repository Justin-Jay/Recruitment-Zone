package za.co.RecruitmentZone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.application.entity.Application;
import za.co.RecruitmentZone.application.service.ApplicationService;
import za.co.RecruitmentZone.blog.service.BlogService;
import za.co.RecruitmentZone.candidate.entity.Candidate;
import za.co.RecruitmentZone.candidate.service.CandidateService;
import za.co.RecruitmentZone.client.entity.Client;
import za.co.RecruitmentZone.client.entity.ContactPerson;
import za.co.RecruitmentZone.client.service.ClientService;
import za.co.RecruitmentZone.employee.entity.Employee;
import za.co.RecruitmentZone.employee.service.EmployeeService;
import za.co.RecruitmentZone.storage.StorageService;
import za.co.RecruitmentZone.util.Enums.ApplicationStatus;
import za.co.RecruitmentZone.util.Enums.VacancyStatus;
import za.co.RecruitmentZone.application.Events.ApplicationsEventPublisher;
import za.co.RecruitmentZone.vacancy.dto.VacancyDTO;
import za.co.RecruitmentZone.vacancy.entity.Vacancy;
import za.co.RecruitmentZone.vacancy.service.VacancyService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static za.co.RecruitmentZone.util.Enums.VacancyStatus.*;


@Service
public class RecruitmentZoneService {
    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneService.class);
    private final ApplicationService applicationService;
    private final BlogService blogService;
    private final CandidateService candidateService;
    private final EmployeeService employeeService;
    private final VacancyService vacancyService;
    private final ApplicationsEventPublisher applicationsEventPublisher;
    private final StorageService storageService;
    private final ClientService clientService;

    public RecruitmentZoneService(ApplicationService applicationService, VacancyService vacancyService, BlogService blogService, CandidateService candidateService,
                                  EmployeeService employeeService, ApplicationsEventPublisher applicationsEventPublisher, StorageService storageService, ClientService clientService) {
        this.applicationService = applicationService;
        this.vacancyService = vacancyService;
        this.blogService = blogService;
        this.candidateService = candidateService;
        this.employeeService = employeeService;
        this.applicationsEventPublisher = applicationsEventPublisher;
        this.storageService = storageService;
        this.clientService = clientService;
    }



    public List<Candidate> getCandidates() {
        return candidateService.getCandidates();
    }

    public Candidate getCandidateById(Long id) {
        return candidateService.getcandidateByID(id);
    }


    // EMPLOYEE

    public Employee findEmployeeByUserName(String name){
        Optional<Employee> oe = employeeService.findEmployeeByUserName(name);
        if (oe.isPresent()){
            return oe.get();
        }
      return null;
    }

    public Employee findEmployeeByID(Long id){
        Optional<Employee> oe = employeeService.findEmployeeByID(id);
        if (oe.isPresent()){
            return oe.get();
        }
        return null;
    }

    public List<Employee> getEmployees(){
        return employeeService.getEmployees();
    }


    /* public void updateExistingEmployee(Long employeeID, EmployeeDTO employeeDTO) {
         log.info("--Attempting updateExistingEmployee ---");
         employeeService.updateExistingEmployee(employeeID, employeeDTO);
     }*/
    // VACANCY
    public void saveNewVacancy(VacancyDTO vacancy) {
        // create vacancy
        Vacancy newVacancy = new Vacancy();
        newVacancy.setJob_title(vacancy.getJob_title());
        newVacancy.setJob_description(vacancy.getJob_description());
        newVacancy.setSeniority_level(vacancy.getSeniority_level());
        newVacancy.setRequirements(vacancy.getRequirements());
        newVacancy.setLocation(vacancy.getLocation());
        newVacancy.setIndustry(vacancy.getIndustry());
        newVacancy.setPublish_date(vacancy.getPublish_date());
        newVacancy.setEnd_date(vacancy.getEnd_date());
        newVacancy.setJobType(vacancy.getJobType());
        newVacancy.setEmpType(vacancy.getEmpType());
        newVacancy.setCategory(vacancy.getCategory());
        LocalDate today = LocalDate.now();

        if(vacancy.getPublish_date().isAfter(today))
        {
            newVacancy.setStatus(PENDING);
        }
        else {
            newVacancy.setStatus(ACTIVE);
        }


        //Client client = clientService.findClientByID(vacancy.getClientid());
        //newVacancy.setClient(client);

       // Optional<Employee> op = employeeService.findEmployeeByID(vacancy.getEmployeeID());
        //op.ifPresent(newVacancy::setEmployee);

        vacancyService.save(newVacancy);
    }

    public List<Vacancy> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    public void saveVacancy(Vacancy vacancy) {
        // Retrieve the existing Vacancy from the database
        Vacancy existingVacancy = vacancyService.findById(vacancy.getVacancyID());

        // Check if the existingVacancy is not null (it exists in the database)
        if (existingVacancy != null) {
            // Compare and update fields


            if(existingVacancy.getJob_title() !=null && !existingVacancy.getJob_title().equals(vacancy.getJob_title())){
                existingVacancy.setJob_title(vacancy.getJob_title());
            }
            if(existingVacancy.getJob_description() !=null && !existingVacancy.getJob_description().equals(vacancy.getJob_description())){
                existingVacancy.setJob_description(vacancy.getJob_description());
            }
            if(existingVacancy.getSeniority_level() !=null &&!existingVacancy.getSeniority_level().equals(vacancy.getSeniority_level())){
                existingVacancy.setSeniority_level(vacancy.getSeniority_level());
            }
            if(existingVacancy.getRequirements() !=null && !existingVacancy.getRequirements()
                    .equals(vacancy.getRequirements())){
                existingVacancy.setRequirements(vacancy.getRequirements());
            }
            if(existingVacancy.getLocation() !=null && !existingVacancy.getLocation().equals(vacancy.getLocation())){
                existingVacancy.setLocation(vacancy.getLocation());
            }
            if(existingVacancy.getIndustry() !=null &&!existingVacancy.getIndustry().equals(vacancy.getIndustry())){
                existingVacancy.setIndustry(vacancy.getIndustry());
            }
            if(existingVacancy.getPublish_date() !=null &&!existingVacancy.getPublish_date().equals(vacancy.getPublish_date())){
                existingVacancy.setPublish_date(vacancy.getPublish_date());
            }
            if(existingVacancy.getEnd_date() !=null &&!existingVacancy.getEnd_date().equals(vacancy.getEnd_date())){
                existingVacancy.setEnd_date(vacancy.getEnd_date());
            }
            if(existingVacancy.getStatus() !=null &&!existingVacancy.getStatus().equals(vacancy.getStatus())){
                existingVacancy.setStatus(vacancy.getStatus());
            }
            if(existingVacancy.getJobType() !=null &&!existingVacancy.getJobType().equals(vacancy.getJobType())){
                existingVacancy.setJobType(vacancy.getJobType());
            }
            if(existingVacancy.getEmpType() !=null &&!existingVacancy.getEmpType().equals(vacancy.getEmpType())){
                existingVacancy.setEmpType(vacancy.getEmpType());
            }
            // Repeat this process for other fields...

            // Save the updated Vacancy
            vacancyService.save(existingVacancy);
        } else {
            // Handle the case where the Vacancy does not exist in the database
            // You might want to throw an exception, log a message, or handle it based on your requirements
        }
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
        application.setCandidate(candidate);
        application.setStatus(ApplicationStatus.PENDING);

        // link application with vacancy
        application.setVacancy(vacancyService.findById(vacancyID));

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





//  getVacancyApplications

    public boolean saveUpdatedApplicationStatus(Long applicationID, ApplicationStatus applicationStatus) {
        return applicationService.saveUpdatedStatus(applicationID, applicationStatus);
    }

    public boolean saveSubmissionEvent(Candidate candidate, Long vacancyID) {
        return applicationsEventPublisher.publishSaveSubmissionEvent(candidate, vacancyID);
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
        clientService.addContactToClient(clientID,contactPerson);
    }
    public void saveUpdatedClient(Long clientID,Client updatedClient){
        Client oc = clientService.findClientByID(clientID);
        if(!oc.getName().equalsIgnoreCase(updatedClient.getName())){
            oc.setName(updatedClient.getName());
        }

        if(!oc.getIndustry().equalsIgnoreCase(updatedClient.getIndustry())){
            oc.setIndustry(updatedClient.getIndustry());
        }
        clientService.saveUpdatedClient(oc);
    }



}
