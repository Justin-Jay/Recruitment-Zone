package za.co.recruitmentzone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.candidate.dto.CandidateFileDTO;
import za.co.recruitmentzone.application.dto.NewApplicationDTO;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.application.service.ApplicationService;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.candidate.service.CandidateService;
import za.co.recruitmentzone.client.dto.ClientDTO;
import za.co.recruitmentzone.client.dto.ContactPersonDTO;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.client.entity.ContactPerson;
import za.co.recruitmentzone.client.service.ClientService;
import za.co.recruitmentzone.documents.CandidateFile;
import za.co.recruitmentzone.documents.CandidateFileService;
import za.co.recruitmentzone.employee.entity.Authority;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.service.EmployeeService;
import za.co.recruitmentzone.storage.StorageService;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.util.enums.ROLE;
import za.co.recruitmentzone.util.enums.VacancyStatus;
import za.co.recruitmentzone.application.Events.ApplicationsEventPublisher;
import za.co.recruitmentzone.vacancy.dto.VacancyDTO;
import za.co.recruitmentzone.vacancy.entity.Vacancy;
import za.co.recruitmentzone.vacancy.service.VacancyService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static za.co.recruitmentzone.util.enums.DocumentType.CURRICULUM_VITAE;
import static za.co.recruitmentzone.util.enums.ROLE.*;
import static za.co.recruitmentzone.util.enums.VacancyStatus.*;


@Service
public class RecruitmentZoneService {
    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneService.class);
    private final ApplicationService applicationService;
    private final CandidateService candidateService;
    private final EmployeeService employeeService;
    private final VacancyService vacancyService;
    private final ApplicationsEventPublisher applicationsEventPublisher;
    private final StorageService storageService;
    private final ClientService clientService;
    private final CandidateFileService fileService;

    @Value("${file.directory}")
    String fileDirectory;

    @Value("${upload.folder}")
    String Folder;

    public RecruitmentZoneService(ApplicationService applicationService, VacancyService vacancyService, CandidateService candidateService,
                                  EmployeeService employeeService, ApplicationsEventPublisher applicationsEventPublisher, StorageService storageService, ClientService clientService, CandidateFileService fileService) {
        this.applicationService = applicationService;
        this.vacancyService = vacancyService;
        this.candidateService = candidateService;
        this.employeeService = employeeService;
        this.applicationsEventPublisher = applicationsEventPublisher;
        this.storageService = storageService;
        this.clientService = clientService;
        this.fileService = fileService;
    }


    public boolean saveCandidate(Candidate candidate) {
        log.info(" --- Save Candidate ---  \n {}", candidate.toString());
        candidateService.save(candidate);
        return true;
    }

    public List<Candidate> getCandidates() {
        return candidateService.getCandidates();
    }

    public Candidate findCandidateByID(Long id) {
        return candidateService.getcandidateByID(id);
    }


    // EMPLOYEE

    public Employee findEmployeeByEmail(String email) {
        log.info(" findEmployeeByEmail  {}", email);
        Optional<Employee> oe = employeeService.findEmployeeByEmail(email);
        if (oe.isPresent()) {
            log.info("FOUND EMPLOYEE {}", oe.get());
        }

        return oe.orElse(null);
    }

    public Employee findEmployeeByID(Long id) {
        Employee oe = employeeService.findEmployeeByID(id);

        return oe;
    }

    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    public Employee findEmployeeByName(String name) {
        return employeeService.findEmployeeByName(name);
    }

    /* public void updateExistingEmployee(Long employeeID, EmployeeDTO employeeDTO) {
         log.info("--Attempting updateExistingEmployee ---");
         employeeService.updateExistingEmployee(employeeID, employeeDTO);
     }*/
    // VACANCY
    public void saveNewVacancy(VacancyDTO vacancy) {
        // create vacancy
        log.info(" Creating vacancy VacancyDTO \n {}", vacancy);
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

// Get the current date and time
        LocalDateTime now = LocalDateTime.now();

// Truncate seconds and nanoseconds
        LocalDateTime truncated = now.withSecond(0).withNano(0);

// Set the truncated value to the vacancy
        newVacancy.setCreated(truncated);

        LocalDate today = LocalDate.now();

        if (vacancy.getPublish_date().isAfter(today)) {
            newVacancy.setStatus(PENDING);
            log.info(" Vacancy status set to \n {}", PENDING);
        } else {
            newVacancy.setStatus(ACTIVE);
            log.info(" Vacancy status set to \n {}", ACTIVE);
        }


        Client client = clientService.findClientByID(vacancy.getClientID());
        newVacancy.setClient(client);
        log.info(" Employee set to \n {}", client);
        Employee op = employeeService.findEmployeeByID(vacancy.getEmployeeID());
        log.info(" Employee set to \n {}", op);
        newVacancy.setEmployee(op);
        vacancyService.save(newVacancy);
    }

    public List<Vacancy> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    public void saveVacancy(VacancyDTO vacancy) {
        log.info(" saveVacancy \n {}", vacancy.printVacancy());
        // Retrieve the existing Vacancy from the database
        Vacancy existingVacancy = vacancyService.findById(vacancy.getVacancyID());
        log.info(" saveVacancy \n {}", existingVacancy.printVacancy());
        // Check if the existingVacancy is not null (it exists in the database)
        if (existingVacancy != null) {
            // Compare and update fields
            if (existingVacancy.getJob_title() != null && !existingVacancy.getJob_title().equals(vacancy.getJob_title())) {
                existingVacancy.setJob_title(vacancy.getJob_title());
            }
            if (existingVacancy.getJob_description() != null && !existingVacancy.getJob_description().equals(vacancy.getJob_description())) {
                existingVacancy.setJob_description(vacancy.getJob_description());
            }
            if (existingVacancy.getSeniority_level() != null && !existingVacancy.getSeniority_level().equals(vacancy.getSeniority_level())) {
                existingVacancy.setSeniority_level(vacancy.getSeniority_level());
            }
            if (existingVacancy.getRequirements() != null && !existingVacancy.getRequirements()
                    .equals(vacancy.getRequirements())) {
                existingVacancy.setRequirements(vacancy.getRequirements());
            }
            if (existingVacancy.getCategory() != null && !existingVacancy.getCategory().equals(vacancy.getCategory())) {
                existingVacancy.setCategory(vacancy.getCategory());
            } else if (existingVacancy.getCategory() == null) {
                existingVacancy.setCategory(vacancy.getCategory());
            }
            if (existingVacancy.getLocation() != null && !existingVacancy.getLocation().equals(vacancy.getLocation())) {
                existingVacancy.setLocation(vacancy.getLocation());
            }
            if (existingVacancy.getIndustry() != null && !existingVacancy.getIndustry().equals(vacancy.getIndustry())) {
                existingVacancy.setIndustry(vacancy.getIndustry());
            }

            if (vacancy.getPublish_date() != null && vacancy.getPublish_date() != existingVacancy.getPublish_date()) {
                existingVacancy.setPublish_date(vacancy.getPublish_date());

            }
            if (vacancy.getEnd_date() != null && vacancy.getEnd_date() != existingVacancy.getEnd_date()) {
                existingVacancy.setEnd_date(vacancy.getEnd_date());
            }


            if (existingVacancy.getStatus() != null && !existingVacancy.getStatus().equals(vacancy.getStatus())) {
                existingVacancy.setStatus(vacancy.getStatus());
            }
            if (existingVacancy.getJobType() != null && !existingVacancy.getJobType().equals(vacancy.getJobType())) {
                existingVacancy.setJobType(vacancy.getJobType());
            }
            if (existingVacancy.getEmpType() != null && !existingVacancy.getEmpType().equals(vacancy.getEmpType())) {
                existingVacancy.setEmpType(vacancy.getEmpType());
            }

            log.info(" About to save vacancy \n {}", existingVacancy.printVacancy());
            // Save the updated Vacancy
            vacancyService.save(existingVacancy);
        } else {
            log.info(" No existing vacancy found ");

        }
    }

    public List<Vacancy> getActiveVacancies() {
        return vacancyService.getActiveVacancies(VacancyStatus.ACTIVE);
    }

    public Vacancy findVacancyById(Long vacancyID) {
        log.info(" Looking for vacancy: {}", vacancyID);
        Vacancy vacancy = vacancyService.findById(vacancyID);
        if (vacancy != null) {
            log.info(" Found {}", vacancy.printVacancy());
            return vacancy;
        } else {
            log.info(" Vacancy not found ");
        }
        return vacancy;
    }

    public String getVacancyName(Long id) {
        log.info(" Searching for Vacancy Name belonging to {} ", id);
        Vacancy op = vacancyService.findById(id);
        if (op != null) {
            log.info(" Found Vacancy {} ", op.getJob_title());
            return op.getJob_title();
        } else {
            log.info(" Found No vacancy ");
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

// AUTHORITIES

    public List<ROLE> getAuthorities(Employee employee) {
        log.info("Searching for Employee: {}",employee.toString());
        List<Authority> userAuths = employee.getAuthorities();
        log.info("Employee Authorities : \n {}",userAuths);
        Authority admin = new Authority(ROLE_ADMIN);
        admin.setEmployee(employee);
        Authority manager = new Authority(ROLE_MANAGER);
        manager.setEmployee(employee);
        Authority emp = new Authority(ROLE_EMPLOYEE);
        emp.setEmployee(employee);
        List<ROLE> returnList;

        if (userAuths.contains(admin)) {
            returnList = new ArrayList<>();
            returnList.add(ROLE_ADMIN);
            returnList.add(ROLE_MANAGER);
            returnList.add(ROLE_EMPLOYEE);
        } else if (!userAuths.contains(admin) && userAuths.contains(manager)) {
            returnList = new ArrayList<>();
            returnList.add(ROLE_MANAGER);
            returnList.add(ROLE_EMPLOYEE);
        } else {
            returnList = null;
        }

        return returnList;
    }

    // APPLICATIONS
    public void createCandidateApplication(NewApplicationDTO newApplicationDTO) {
        log.info("New Application \n {}",newApplicationDTO);
        Candidate candidate = new Candidate();
        candidate.setFirst_name(newApplicationDTO.getFirst_name());
        candidate.setLast_name(newApplicationDTO.getLast_name());
        candidate.setId_number(newApplicationDTO.getId_number());
        candidate.setEmail_address(newApplicationDTO.getEmail_address());
        candidate.setPhone_number(newApplicationDTO.getPhone_number());
        candidate.setCurrent_province(newApplicationDTO.getCurrent_province());
        candidate.setCurrent_role(newApplicationDTO.getCurrent_role());
        candidate.setCurrent_employer(newApplicationDTO.getCurrent_employer());
        candidate.setSeniority_level(newApplicationDTO.getSeniority_level());
        candidate.setEducation_level(newApplicationDTO.getEducation_level());
        candidate.setRelocation(newApplicationDTO.getRelocation());
        candidate.setCreated(LocalDateTime.now());
        log.info("New Candidate \n {}",candidate);

        // create candidate and save and link with document
        candidate = candidateService.save(candidate);
        log.info("Candidate Saved");
        // create application  and link candidate
        CandidateFileDTO fileDTO = new CandidateFileDTO();
        fileDTO.setCandidateID(candidate.getCandidateID());
        fileDTO.setCandidateIDNumber(newApplicationDTO.getId_number());
        fileDTO.setCvFile(newApplicationDTO.getCvFile());
        fileDTO.setDocumentType(CURRICULUM_VITAE);
        CandidateFile file = null;
        try {
            file = createCandidateFile(fileDTO);
        } catch (Exception e) {
            log.info("Failed to create file \n {}",e.getMessage());
        }

        candidate.AddDocument(file);
        fileService.save(file);


        Application application = new Application();
        LocalDate date = LocalDate.now();
        application.setDate_received(date.toString());
        application.setSubmission_date(date.toString());
        application.setStatus(ApplicationStatus.PENDING);

        // link application with vacancy
        application.setVacancy(vacancyService.findById(newApplicationDTO.getVacancyID()));

        // link candidate with application
        candidate.AddApplication(application);
        log.info("About to Save New Application \n {}",application);
        applicationService.save(application);


    }

    public CandidateFile createCandidateFile(CandidateFileDTO fileDTO) throws IOException {
        log.info("About to create new file {}",fileDTO);
        CandidateFile file = new CandidateFile();
        Candidate candidate = candidateService.getcandidateByID(fileDTO.getCandidateID());
        file.setCandidate(candidate);
        file.setContenttype(fileDTO.getCvFile().getContentType());
        file.setFilename(fileDTO.getCvFile().getOriginalFilename());
        file.setFilesize(Long.toString(fileDTO.getCvFile().getSize()));
        file.setDocumentType(fileDTO.getDocumentType());
        file.setCreated(LocalDateTime.now());
        log.info("New CandidateFile {}",file);
        // Local file storage

        String docType = fileDTO.getDocumentType().toString();
        String candidateIDNumber = candidate.getId_number();
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));

        String fileDirectory = "/recruitment-zone-app/data/file-uploads/";
        String Folder = "CANDIDATE_FILES";
        log.info("fileDirectory/Folder: {},{}",fileDirectory,Folder);
        String directory = fileDirectory + Folder + "/" + candidateIDNumber + "/" + docType + "/" + formattedDate;

        Path storageLocation = null;
        try {
            Path uploadPath = Path.of(directory);
            if (Files.notExists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            storageLocation = uploadPath.resolve(fileDTO.getCvFile().getOriginalFilename());
            Files.copy(fileDTO.getCvFile().getInputStream(), storageLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("File saved storageLocation: {}",storageLocation);
        } catch (Exception e) {
            log.info("Failed to create directory {}", e.getMessage());
        }

        // Google storage
        //file.setDocumentLocation(saveFile(fileDTO));

        file.setDocumentLocation(storageLocation.toString());
        return file;
    }

    public List<Application> getApplications() {
        return applicationService.findApplications();
    }

    public Application findApplicationByID(Long applicationID) {
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
    public void saveCandidateFile(CandidateFile candidateFile) {
        fileService.save(candidateFile);
    }

    public String saveFile(CandidateFileDTO fileDTO) {
        return storageService.uploadFile(fileDTO);
    }

    public String saveTempFile() throws IOException {
        log.info("Entering Test Method");
        return storageService.testMethod();
    }

    public boolean publishFileUploadedEvent(Long candidateID, NewApplicationDTO newApplicationDTO) {
        return applicationsEventPublisher.publishFileUploadEvent(candidateID, newApplicationDTO);
    }

    // CLIENTS

    public void saveNewClient(ClientDTO clientDTO) {
        clientService.saveClient(clientDTO);
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

    public void addContactToClient(ContactPersonDTO contactPersonDTO) {
        clientService.addContactToClient(contactPersonDTO);
    }

    public void saveUpdatedClient(Long clientID, Client updatedClient) {
        Client oc = clientService.findClientByID(clientID);
        if (!oc.getName().equalsIgnoreCase(updatedClient.getName())) {
            oc.setName(updatedClient.getName());
        }

        if (oc.getIndustry() != updatedClient.getIndustry()) {
            oc.setIndustry(updatedClient.getIndustry());
        }
        clientService.saveUpdatedClient(oc);
    }

    public boolean saveClient(Client client) {
        clientService.saveUpdatedClient(client);
        return true;
    }

}
