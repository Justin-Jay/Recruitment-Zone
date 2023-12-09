package za.co.RecruitmentZone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.entity.Enums.ApplicationStatus;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;
import za.co.RecruitmentZone.entity.domain.*;
import za.co.RecruitmentZone.events.publisher.Applications.ApplicationsEventPublisher;
import za.co.RecruitmentZone.service.domainServices.*;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.time.LocalDate;
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


    /* public void updateExistingEmployee(Long employeeID, EmployeeDTO employeeDTO) {
         log.info("--Attempting updateExistingEmployee ---");
         employeeService.updateExistingEmployee(employeeID, employeeDTO);
     }*/
    // VACANCY
    public void saveNewVacancy(String employeeUserName, Vacancy vacancy) {
        // create vacancy
        vacancy.setStatus(VacancyStatus.PENDING);
    /*    Optional<Employee> op = employeeService.findEmployeeByUserName(employeeUserName);
        op.ifPresent(vacancy::setEmployee);*/
        vacancyService.save(vacancy);
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
