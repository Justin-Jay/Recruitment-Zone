package za.co.recruitmentzone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tika.Tika;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.recruitmentzone.application.exception.ApplicationsNotFoundException;
import za.co.recruitmentzone.blog.dto.BlogDTO;
import za.co.recruitmentzone.blog.entity.Blog;
import za.co.recruitmentzone.blog.exception.BlogNotFoundException;
import za.co.recruitmentzone.blog.exception.BlogNotSavedException;
import za.co.recruitmentzone.blog.service.BlogService;
import za.co.recruitmentzone.candidate.dto.CandidateFileDTO;
import za.co.recruitmentzone.application.dto.NewApplicationDTO;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.application.service.ApplicationService;
import za.co.recruitmentzone.candidate.dto.CandidateNoteDTO;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.candidate.entity.CandidateNote;
import za.co.recruitmentzone.candidate.exception.CandidateException;
import za.co.recruitmentzone.candidate.exception.CandidateNotFoundException;
import za.co.recruitmentzone.candidate.exception.SaveCandidateException;
import za.co.recruitmentzone.candidate.service.CandidateService;
import za.co.recruitmentzone.client.dto.ClientDTO;
import za.co.recruitmentzone.client.dto.ClientNoteDTO;
import za.co.recruitmentzone.client.dto.ContactPersonDTO;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.client.entity.ClientNote;
import za.co.recruitmentzone.client.entity.ContactPerson;
import za.co.recruitmentzone.client.exception.*;
import za.co.recruitmentzone.client.service.ClientService;
import za.co.recruitmentzone.documents.CandidateFile;
import za.co.recruitmentzone.documents.CandidateFileService;
import za.co.recruitmentzone.documents.SaveFileException;
import za.co.recruitmentzone.documents.SearchDocumentsDTO;
import za.co.recruitmentzone.employee.dto.EmployeeDTO;
import za.co.recruitmentzone.employee.entity.Authority;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.exception.EmployeeNotFoundException;
import za.co.recruitmentzone.employee.exception.RolesNotFoundException;
import za.co.recruitmentzone.employee.exception.UserAlreadyExistsException;
import za.co.recruitmentzone.employee.service.EmployeeService;
import za.co.recruitmentzone.exception.DocumentLocationError;
import za.co.recruitmentzone.exception.NoResultsFoundException;
import za.co.recruitmentzone.storage.StorageService;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.util.enums.BlogStatus;
import za.co.recruitmentzone.util.enums.ROLE;
import za.co.recruitmentzone.util.enums.VacancyStatus;
import za.co.recruitmentzone.application.Events.ApplicationsEventPublisher;
import za.co.recruitmentzone.vacancy.dto.VacancyDTO;
import za.co.recruitmentzone.vacancy.entity.Vacancy;
import za.co.recruitmentzone.vacancy.exception.VacancyException;
import za.co.recruitmentzone.vacancy.service.VacancyService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static za.co.recruitmentzone.util.enums.BlogStatus.ACTIVE;
import static za.co.recruitmentzone.util.enums.BlogStatus.PENDING;
import static za.co.recruitmentzone.util.enums.DocumentType.CURRICULUM_VITAE;
import static za.co.recruitmentzone.util.enums.ROLE.*;

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
    private final BlogService blogService;
    private final CandidateFileService fileService;
    private final PasswordEncoder passwordEncoder;

    @Value("${file.directory}")
    String fileDirectory;

    @Value("${upload.folder}")
    String Folder;

    public RecruitmentZoneService(ApplicationService applicationService, VacancyService vacancyService, CandidateService candidateService,
                                  EmployeeService employeeService, ApplicationsEventPublisher applicationsEventPublisher, StorageService storageService, ClientService clientService, BlogService blogService, CandidateFileService fileService, PasswordEncoder passwordEncoder) {
        this.applicationService = applicationService;
        this.vacancyService = vacancyService;
        this.candidateService = candidateService;
        this.employeeService = employeeService;
        this.applicationsEventPublisher = applicationsEventPublisher;
        this.storageService = storageService;
        this.clientService = clientService;
        this.blogService = blogService;
        this.fileService = fileService;
        this.passwordEncoder = passwordEncoder;
    }

    // APPLICATIONS

    public void createCandidateApplication(NewApplicationDTO newApplicationDTO, Model model, RedirectAttributes redirectAttributes) {
        log.info("<-- createCandidateApplication  newApplicationDTO: {} --> ", newApplicationDTO);
        try {
            Application application = createApplication(newApplicationDTO);
            if (application != null) {
                String modelValue = "Candidate Application Created: " + application.getApplicationID();
                redirectAttributes.addFlashAttribute("createCandidateResponse", modelValue
                );
                log.info("<-- createCandidateApplication  application != null : {} --> ", modelValue);
            }
        } catch (CandidateException e) {
            log.info("<-- createCandidateApplication  CandidateException: {} --> ", e.getMessage());
            model.addAttribute("createCandidateApplication", e.getMessage());
        }
    }

    public void createCandidateApplication(Model model, RedirectAttributes redirectAttributes, NewApplicationDTO newApplicationDTO) {
        log.info("<-- createCandidateApplication  newApplicationDTO: {} --> ", newApplicationDTO);
        try {
            Application application = createApplication(newApplicationDTO);
            if (application != null) {
                String outcome = "Candidate Application Created: " + application.getApplicationID();
                model.addAttribute("createCandidateApplicationResponse", outcome
                );
                log.info("<-- createCandidateApplication  application != null: {} --> ", outcome);
            }
        } catch (CandidateException candidateException) {
            redirectAttributes.addFlashAttribute("createCandidateApplicationResponse",
                    candidateException.getMessage());
            log.info("<-- createCandidateApplication  candidateException: {} --> ", candidateException.getMessage());
        }
    }

    public void saveUpdatedApplicationStatus(RedirectAttributes redirectAttributes, Long applicationID, ApplicationStatus status) {
        log.info("<-- saveUpdatedApplicationStatus  applicationID: {} status {} --> ", applicationID, status);
        try {
            boolean saveResult = applicationService.saveUpdatedStatus(applicationID, status);
            if (saveResult) {
                String outcome = "Application ID "+applicationID+" Status has been updated to "+status;
                redirectAttributes.addFlashAttribute("saveApplicationStatusResponse", outcome);
                log.info("<-- saveUpdatedApplicationStatus  saveResult: {} outcome {} --> ", saveResult, outcome);
            }
        } catch (VacancyException vacancyException) {
            redirectAttributes.addFlashAttribute("saveApplicationStatusResponse", vacancyException.getFailureReason());
            log.info("<-- saveUpdatedApplicationStatus  vacancyException: {} --> ", vacancyException.getFailureReason());
        }
    }


    public void findApplicationByID(Long applicationID, Model model) {
        log.info("<-- findApplicationByID  applicationID: {} --> ", applicationID);
        try {
            Optional<Application> optionalApplication = applicationService.findApplicationByID(applicationID);
            if (optionalApplication.isPresent()) {
                model.addAttribute("vacancyApplication", optionalApplication.get());
                //log.info("<-- findApplicationByID  optionalApplication.isPresent: {} --> ", optionalApplication.get().toString());
            } else throw new ApplicationsNotFoundException("Application not found " + applicationID);
        } catch (ApplicationsNotFoundException applicationsNotFoundException) {
            model.addAttribute("findApplicationByIDResponse", applicationsNotFoundException.getMessage());
            log.info("<-- findApplicationByID  applicationsNotFoundException {} --> ", applicationsNotFoundException.getMessage());
        }
    }

    public void findAllApplications(Model model) {
        log.info("<-- findAllApplications --> ");
        try {
            List<Application> allApplications = applicationService.findApplications();
            if (!allApplications.isEmpty()) {
                model.addAttribute("applications", allApplications);
                log.info("<-- findAllApplications  allApplications.Size {} --> ", allApplications.size());
            }
        } catch (ApplicationsNotFoundException applicationsNotFoundException) {
            model.addAttribute("findAllApplicationsResponse", applicationsNotFoundException.getMessage());
            log.info("<-- findAllApplications  applicationsNotFoundException {} --> ", applicationsNotFoundException.getMessage());
        }
    }

    // CANDIDATES


    public void saveCandidateNote(CandidateNoteDTO candidateNote, Model model) {
        log.info("<-- saveCandidateNote  candidateNote {} --> ", candidateNote);
        Long candidateID = candidateNote.getCandidateID();
        Optional<Candidate> optionalCandidate = candidateService.getcandidateByID(candidateID);
        if (optionalCandidate.isPresent()) {
            log.info("<-- saveCandidateNote  optionalCandidate.isPresent {} --> ", optionalCandidate.get());
            optionalCandidate.get().addNote(candidateNote);
            try {
                Candidate savedCandidate = candidateService.save(optionalCandidate.get());
                if (savedCandidate != null) {
                    model.addAttribute("noteSaved", true);
                    log.info("<-- saveCandidateNote  savedCandidate {} --> ", savedCandidate);
                } else
                    throw new SaveCandidateException("Failed to save candidate : " + optionalCandidate.get().getCandidateID());

            } catch (SaveCandidateException saveCandidateException) {
                model.addAttribute("noteSaved", Boolean.FALSE);
                model.addAttribute("saveCandidateResponse", saveCandidateException.getMessage());
                log.info("<-- saveCandidateNote  noteSaved {} --> ", false);
                log.info("<-- saveCandidateNote  saveCandidateException {} --> ", saveCandidateException.getMessage());
            }
        }
    }

    public void findAllCandidates(Model model) {
        log.info("<-- findAllCandidates  --> ");
        try {
            List<Candidate> responseList = candidateService.getCandidates();
            if (!responseList.isEmpty()) {
                model.addAttribute("candidates", responseList);
                log.info("<-- findAllCandidates responseList size {} --> ", responseList.size());
            } else throw new CandidateNotFoundException("No Candidates Found");
        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("findAllCandidates", candidateNotFoundException.getMessage());
            log.info("<-- findAllCandidates candidateNotFoundException {} --> ", candidateNotFoundException.getMessage());
        }
    }

    public void findCandidateByID(Long candidateID, Model model) {
        log.info("<-- findCandidateByID  {} --> ", candidateID);
        try {
            Optional<Candidate> optionalCandidate = candidateService.getcandidateByID(candidateID);
            if (optionalCandidate.isPresent()) {
                model.addAttribute("candidate", optionalCandidate.get());
                log.info("<-- findCandidateByID optionalCandidate.isPresent  {} --> ", optionalCandidate);
            } else throw new CandidateNotFoundException("Candidate Not Found" + candidateID);
        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("findCandidateByIDResponse", candidateNotFoundException.getMessage());
            log.info("<--  findCandidateByID candidateNotFoundException {} --> ", candidateNotFoundException.getMessage());
        }
    }

/*    public void findCandidateDocuments(Model model, Long candidateID) {
        log.info("<-- findCandidateDocuments  {} --> ", candidateID);
        try {
            Optional<Candidate> optionalCandidate = candidateService.getcandidateByID(candidateID);
            if (optionalCandidate.isPresent()) {
                Set<CandidateFile> candidateDocuments = optionalCandidate.get().getDocuments();
                model.addAttribute("existingDocuments", candidateDocuments);
                model.addAttribute("candidate", optionalCandidate.get());
                model.addAttribute("candidateID", candidateID);
                CandidateFileDTO fileDTO = new CandidateFileDTO();
                fileDTO.setCandidateID(candidateID);
                model.addAttribute("candidateFileDTO", fileDTO);

                log.info("<-- findCandidateDocuments optionalCandidate.isPresent {} --> ", optionalCandidate.get());
                log.info("<-- findCandidateDocuments candidateDocuments size {} --> ", candidateDocuments.size());
                log.info("<-- findCandidateDocuments fileDTO {} --> ", fileDTO);
            } else throw new CandidateNotFoundException("Candidate Not Found" + candidateID);
        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("findCandidateDocumentsResponse", candidateNotFoundException.getMessage());
            log.info("<-- findCandidateDocuments candidateNotFoundException {} --> ", candidateNotFoundException.getMessage());
        }
        log.info("<-- findCandidateDocuments model {} --> ", model);
    }*/
public void findCandidateDocuments(Model model, Long candidateID) {
    log.info("<-- findCandidateDocuments  {} --> ", candidateID);
    try {
        Optional<Candidate> optionalCandidate = candidateService.getcandidateByID(candidateID);
        if (optionalCandidate.isPresent()) {
            List<CandidateFile> candidateDocuments = fileService.getCandidateFiles(optionalCandidate.get().getCandidateID());
            model.addAttribute("existingDocuments", candidateDocuments);
            model.addAttribute("candidate", optionalCandidate.get());
            model.addAttribute("candidateID", candidateID);
            CandidateFileDTO fileDTO = new CandidateFileDTO();
            fileDTO.setCandidateID(candidateID);
            model.addAttribute("candidateFileDTO", fileDTO);

            log.info("<-- findCandidateDocuments optionalCandidate.isPresent {} --> ", optionalCandidate.get());
            log.info("<-- findCandidateDocuments candidateDocuments size {} --> ", candidateDocuments.size());
            log.info("<-- findCandidateDocuments fileDTO {} --> ", fileDTO);
        } else throw new CandidateNotFoundException("Candidate Not Found" + candidateID);
    } catch (CandidateNotFoundException candidateNotFoundException) {
        model.addAttribute("findCandidateDocumentsResponse", candidateNotFoundException.getMessage());
        log.info("<-- findCandidateDocuments candidateNotFoundException {} --> ", candidateNotFoundException.getMessage());
    }
}


    public Application createApplication(NewApplicationDTO newApplicationDTO) {
        log.info("<-- createApplication newApplicationDTO {} --> ", newApplicationDTO);
        try {
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
            log.info("New Candidate \n {}", candidate);

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
            } catch (CandidateNotFoundException e) {
                log.info("Failed to create file \n {}", e.getMessage());
            }



            Application application = new Application();
            LocalDate date = LocalDate.now();
            application.setDate_received(date.toString());
            application.setSubmission_date(date.toString());
            application.setStatus(ApplicationStatus.PENDING);

            // link application with vacancy
            application.setVacancy(vacancyService.findById(newApplicationDTO.getVacancyID()));

            // link candidate with application
            candidate.AddApplication(application);
            log.info("About to Save New Application \n {}", application);
            applicationService.save(application);

            // is it neccesary
//         candidate = candidateService.save(candidate);

            if (application != null) {
                log.info("<-- createApplication application {} --> ", application);
                return application;
            } else throw new CandidateException("Failed to create Candidate Application:");

        } catch (Exception e) {
            log.info("<-- createApplication Exception {} --> ", e.getMessage());
            log.info(e.getMessage());
        }
        return null;
    }

    // EMPLOYEE

    public String applicationURL(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public Employee findEmployeeByEmail(String email) {
        log.info(" findEmployeeByEmail  {}", email);
        Optional<Employee> oe = employeeService.getEmployeeByEmail(email);
        if (oe.isPresent()) {
            log.info("FOUND EMPLOYEE {}", oe.get());
            return oe.get();
        } else throw new EmployeeNotFoundException("Employee not found");
    }

    public void findEmployeeByID(Long employeeID, Model model) {
        try {
            Employee employeeResponse = employeeService.getEmployeeByid(employeeID);
            if (employeeResponse != null) {
                model.addAttribute("employee", employeeResponse);
            } else throw new EmployeeNotFoundException("Employee not found");
        } catch (EmployeeNotFoundException e) {
            model.addAttribute("findEmployeeByIDResponse", e.getMessage());
        }
    }

    public void findAllEmployees(Model model) {
        try {
            List<Employee> responseList = employeeService.getEmployees();
            if (!responseList.isEmpty()) {
                model.addAttribute("employees", responseList);
            } else throw new EmployeeNotFoundException("No employees found. contact system administrator");
        } catch (EmployeeNotFoundException e) {
            model.addAttribute("loadEmployeesResponse", e.getMessage());
        }
    }

    public List<ROLE> findEmployeeAuthorities(Employee employee) {
        List<ROLE> returnList = new ArrayList<>();
        try {
            log.info("Searching for Employee: {}", employee.toString());
            List<Authority> userAuths = employee.getAuthorities();
            log.info("Employee Authorities : \n {}", userAuths);
            Authority admin = new Authority(ROLE_ADMIN);
            admin.setEmployee(employee);
            Authority manager = new Authority(ROLE_MANAGER);
            manager.setEmployee(employee);
            Authority emp = new Authority(ROLE_EMPLOYEE);
            emp.setEmployee(employee);
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
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return returnList;
    }

    public void saveNewEmployee(EmployeeDTO employeeDTO, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        try {
            Employee employeeResponse = employeeService.saveNewEmployee(employeeDTO, applicationURL(request));
            log.info("<--  saveNewEmployee {} -->", employeeResponse);
            if (employeeResponse != null) {
                redirectAttributes.addFlashAttribute("saveNewEmployeeResponse", "New Employee created: " + employeeResponse.getUsername());
            } else throw new UserAlreadyExistsException("User Already Exists");
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            log.info(userAlreadyExistsException.toString());
            model.addAttribute("employeeDTO", new EmployeeDTO());
            loadAuthorities(principal, model);
            model.addAttribute("message", userAlreadyExistsException.getFailureReason());
        }
    }

    public void saveExistingEmployee(Employee employee, RedirectAttributes redirectAttributes) {
        log.info("<--  saveExistingEmployee {} -->", employee);
        try {
            Employee employeeResponse = employeeService.saveUpdatedEmployee(employee);
            log.info("<--  saveExistingEmployee - Employee Saved {} -->", employeeResponse);
            if (employeeResponse != null) {
                redirectAttributes.addAttribute("message", "Employee " + employeeResponse.getFirst_name() + " Updated");
            } else throw new EmployeeNotFoundException("Failed not update employee" + employee.getFirst_name());
        } catch (EmployeeNotFoundException e) {
            redirectAttributes.addAttribute("message", e.getMessage());
            log.info("<--  saveExistingEmployee EmployeeNotFoundException {} -->", e.getMessage());
        }
    }

    // employeeDTO , authorities
    public void loadAuthorities(Principal principal, Model model) {
        log.info("<--  loadAuthorities {} -->", principal);
        try {
            EmployeeDTO newDto = new EmployeeDTO();
            String agent = principal.getName();
            newDto.setAdminAgent(agent);
            model.addAttribute("employeeDTO", newDto);

            log.info("principal email: {}", agent);
            Employee e = employeeService.findEmployeeByName(agent);
            try {
                List<ROLE> authorities = findEmployeeAuthorities(e);
                if (authorities != null) {
                    model.addAttribute("authorities", authorities);
                    log.info("<--  loadAuthorities authorities != null {} -->", authorities);
                } else throw new RolesNotFoundException("Failed to load Authorities");

            } catch (RolesNotFoundException rolesNotFoundException) {
                log.info("<--  loadAuthorities rolesNotFoundException {} -->", rolesNotFoundException.getMessage());
                model.addAttribute("", rolesNotFoundException.getMessage());
            }
        } catch (EmployeeNotFoundException e) {
            model.addAttribute("loadAuthoritiesResponse", e.getMessage());
            log.info("<--  loadAuthorities EmployeeNotFoundException {} -->", e.getMessage());
        }

    }


    // VACANCY

    public Vacancy updateVacancy(VacancyDTO vacancy, RedirectAttributes redirectAttributes) {
        log.info("<--  updateVacancy vacancy {} -->", vacancy.printVacancy());
        // Retrieve the existing Vacancy from the database
        Vacancy existingVacancy = vacancyService.findById(vacancy.getVacancyID());
        log.info(" saveVacancy \n {}", existingVacancy.printVacancy());
        // Check if the existingVacancy is not null (it exists in the database)
        try {
            if (existingVacancy != null) {
                log.info("<--  existingVacancy != null {} -->", existingVacancy.printVacancy());
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


                // Save the updated Vacancy

                vacancyService.save(existingVacancy);
                log.info("<--  existingVacancy Saved -->");
                redirectAttributes.addFlashAttribute("saveVacancyResponse", "Vacancy : " + existingVacancy.getJob_title()+" Updated");
            } else throw new VacancyException("No Existing Vacancy Found");
        } catch (VacancyException vacancyException) {
            log.info("<--  updateVacancy vacancyException {} -->", vacancyException.getFailureReason());
            redirectAttributes.addFlashAttribute("saveVacancyResponse", vacancyException.getFailureReason());
        }
        return null;
    }

    public void saveNewVacancy(VacancyDTO vacancy, RedirectAttributes redirectAttributes) {
        try {
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

            Client client = clientService.findClientByID(vacancy.getClientID());
            if (client != null) {
                newVacancy.setClient(client);
            } else throw new VacancyException("Failed to set client to vacancy");

            Employee op = employeeService.getEmployeeByid(vacancy.getEmployeeID());
            if (op != null) {
                newVacancy.setEmployee(op);
            } else throw new VacancyException("Failed to set employee to vacancy");


            LocalDateTime now = LocalDateTime.now();
            LocalDateTime truncated = now.withSecond(0).withNano(0);
            newVacancy.setCreated(truncated);

            LocalDate today = LocalDate.now();
            if (vacancy.getPublish_date().isAfter(today)) {
                newVacancy.setStatus(VacancyStatus.PENDING);
                log.info(" Vacancy {} status set to {}", newVacancy.getJob_title(), VacancyStatus.PENDING);
            } else {
                newVacancy.setStatus(VacancyStatus.ACTIVE);
                log.info(" Vacancy {} status set to {}", newVacancy.getJob_title(), VacancyStatus.ACTIVE);
            }

            vacancyService.save(newVacancy);

            redirectAttributes.addFlashAttribute("saveVacancyResponse", newVacancy.getJob_title() + " vacancy saved");
        } catch (VacancyException vacancyException) {
            log.info("<-- saveVacancy  vacancyException: {} --> ", vacancyException.getFailureReason());
            redirectAttributes.addFlashAttribute("saveVacancyResponse", vacancyException.getFailureReason());
        }
    }

    public void findVacancyById(Long vacancyID, Model model) {
        log.info("<--  findVacancyById vacancyID  {} -->", vacancyID);
        try {
            Vacancy vacancy = vacancyService.findById(vacancyID);
            if (vacancy != null) {
                model.addAttribute("vacancy", vacancy);
                log.info("<--  findVacancyById vacancy != null  {} -->", vacancy.printVacancy());
            } else throw new VacancyException("Vacancy not found");
        } catch (VacancyException vacancyException) {
            log.info("<-- findVacancyById  vacancyException: {} --> ", vacancyException.getFailureReason());
            model.addAttribute("findVacancyResponse", vacancyException.getFailureReason());
        }
    }

    public void searchVacancyByTitle(String title, Model model) {
        log.info("<--  searchVacancyByTitle title {} -->", title);
        try {
            List<Vacancy> vacancyList = vacancyService.findVacanciesByTitle(title);
            if (vacancyList != null) {
                log.info("<--  searchVacancyByTitle vacancyList size {} -->", vacancyList.size());
                model.addAttribute("totalNumberOfVacancies", vacancyList.size());
                model.addAttribute("vacancies", vacancyList);
            } else throw new VacancyException("Vacancy not found");

        } catch (VacancyException vacancyException) {
            log.info("<-- searchVacancyByTitle  vacancyException: {} --> ", vacancyException.getFailureReason());
            model.addAttribute("searchVacancyByTitleResponse", vacancyException.getFailureReason());
        }
    }

    public List<Vacancy> getAllVacancies(Model model) {
        log.info("<--  getAllVacancies -->");
        try {
            List<Vacancy> responseList = vacancyService.getAllVacancies();
            log.info("<-- getAllVacancies  responseListSize: {} --> ", responseList.size());
            if (responseList != null) {
                log.info("<--  getAllVacancies responseList size-->", responseList.size());
                model.addAttribute("vacancies", responseList);
            } else throw new VacancyException("No Vacancies found");

        } catch (VacancyException vacancyException) {
            log.info("<-- getAllVacancies  vacancyException: {} --> ", vacancyException.getFailureReason());
            model.addAttribute("loadVacanciesResponse", vacancyException.getFailureReason());
        }
        return null;
    }

    public void getActiveVacancies(Model model) {
        log.info("<--  getActiveVacancies -->");
        try {
            List<Vacancy> responseList = vacancyService.getActiveVacancies(VacancyStatus.ACTIVE);
            if (responseList != null) {
                log.info("<--  getActiveVacancies responseList size {}-->", responseList.size());
                //return responseList;
                model.addAttribute("totalNumberOfVacancies", responseList.size());
                model.addAttribute("vacancies", responseList);

            } else throw new VacancyException("No active vacancies found. contact system administrator");
        } catch (VacancyException vacancyException) {
            model.addAttribute("loadVacanciesResponse", vacancyException.getMessage());
            log.info("<--  getActiveVacancies VacancyException {}-->", vacancyException.getFailureReason());
        }

    }

    public boolean deleteVacancy(Long id) {
        try {
            vacancyService.deleteVacancy(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public void vacancyApplicationForm(Model model, Long vacancyID) {
        log.info("<--  vacancyApplicationForm vacancyID {} -->", vacancyID);
        try {
            Vacancy vacancy = vacancyService.findById(vacancyID);
            if (vacancy != null) {
                log.info("<--  vacancyApplicationForm vacancy != null {} -->", vacancy);
                model.addAttribute("vacancy", vacancy);
                model.addAttribute("vacancyName", vacancy.getJob_title());
                model.addAttribute("vacancyID", vacancyID);
                model.addAttribute("newApplicationDTO", new NewApplicationDTO());
            } else throw new VacancyException("No Vacancies found");

        } catch (VacancyException vacancyException) {
            model.addAttribute("vacancyApplicationFormResponse", vacancyException.getFailureReason());
            model.addAttribute("newApplicationDTO", new NewApplicationDTO());
            log.info("<--  vacancyApplicationForm vacancyException {} -->", vacancyException.getFailureReason());
        }
    }

    // BLOG


    public void findBlogById(Long blogID, Model model) {
        log.info("<--  findBlogById blogID {} -->", blogID);
        try {
            Optional<Blog> optionalBlog = blogService.findById(blogID);
            if (optionalBlog.isPresent()) {
                Blog b = optionalBlog.get();
                model.addAttribute("blog", b);
                log.info("<--  findBlogById optionalBlog.isPresent {} -->", b);
            } else throw new BlogNotFoundException("Blog not found :" + blogID);
        } catch (BlogNotFoundException blogNotFoundException) {
            log.info("<--  findBlogById blog is null  -->");
            model.addAttribute("findBlogResponse", blogNotFoundException.getMessage());
            log.info("<--  findBlogById blogNotSavedException {} -->", blogNotFoundException.getMessage());
        }
    }

    public void saveNewBlog(BlogDTO blogDTO, Principal principal, RedirectAttributes redirectAttributes) {
        log.info("Saving new blog \n {}", principal);
        try {
            Blog newBlog = new Blog();
            Employee employee = findEmployeeByEmail(principal.getName());
            if (employee != null) {
                log.info("<--  saveNewBlog employee!=null {} -->", employee);
                newBlog.setEmployee(employee);
                newBlog.setBlog_title(blogDTO.getBlog_title());
                newBlog.setBlog_description(blogDTO.getBlog_description());
                newBlog.setBody(blogDTO.getBody());
                newBlog.setEnd_date(blogDTO.getEnd_date().toString());
                newBlog.setPublish_date(blogDTO.getPublish_date().toString());
                newBlog.setCreated(LocalDateTime.now());
                LocalDate today = LocalDate.now();
                if (blogDTO.getPublish_date().isAfter(today)) {
                    newBlog.setStatus(PENDING);
                } else {
                    newBlog.setStatus(ACTIVE);
                }
                redirectAttributes.addAttribute("saveNewBlogResponse", blogService.save(newBlog));
                log.info("<--  saveNewBlog newBlog {} -->", newBlog);
            } else {
                log.info("<--  saveNewBlog employee is null -->");
                redirectAttributes.addAttribute("saveNewBlogResponse", "Employee Not Set");
            }
        } catch (EmployeeNotFoundException e) {
            log.info("<--  saveNewBlog EmployeeNotFoundException {} -->", e.getMessage());
            redirectAttributes.addFlashAttribute("saveNewBlogResponse", e.getMessage());
        }
    }

    public void saveExistingBlog(Blog blog, RedirectAttributes redirectAttributes) {
        log.info("<--  saveExistingBlog blog {} -->", blog);
        String returnMessage = "";
        try {
            Blog existingBlog = new Blog();
            Optional<Blog> optionalBlog = blogService.findById(blog.getBlogID());
            if (optionalBlog.isPresent()) {
                existingBlog = optionalBlog.get();
                log.info("<--  saveExistingBlog optionalBlog.isPresent {} -->", existingBlog);
                if (existingBlog != null) {
                    if (!existingBlog.getBlog_title().equalsIgnoreCase(blog.getBlog_title())) {
                        existingBlog.setBlog_title(blog.getBlog_title());
                    }
                    // description
                    if (!existingBlog.getBlog_description().equalsIgnoreCase(blog.getBlog_description())) {
                        existingBlog.setBlog_description(blog.getBlog_description());
                    }
                    // body
                    if (!existingBlog.getBody().equalsIgnoreCase(blog.getBody())) {
                        existingBlog.setBody(blog.getBody());
                    }
                    // publish date

                    if (!Objects.equals(existingBlog.getPublish_date(), blog.getPublish_date())) {
                        existingBlog.setPublish_date(blog.getPublish_date());
                    }
                    // expiration date
                    if (!Objects.equals(existingBlog.getEnd_date(), blog.getEnd_date())) {
                        existingBlog.setEnd_date(blog.getEnd_date());
                    }
                    if (blog.getStatus() != null) {
                        // Only set the status if it is provided and different from the current status
                        existingBlog.setStatus(blog.getStatus());
                    }
                    try {
                        Blog savedBlogResponse = blogService.save(existingBlog);
                        if (savedBlogResponse != null) {
                            returnMessage = "Blog Saved " + savedBlogResponse.getBlogID();
                            redirectAttributes.addFlashAttribute("saveBlogResponse", returnMessage);
                            log.info("<--  saveExistingBlog returnMessage {} -->", returnMessage);
                        } else throw new BlogNotSavedException("Failed to save blog");

                    } catch (BlogNotSavedException blogNotSavedException) {
                        log.info("<--  saveExistingBlog blogNotSavedException {} -->", blogNotSavedException.getMessage());
                        redirectAttributes.addFlashAttribute("saveBlogResponse", blogNotSavedException.getMessage());
                    }
                }
            } else throw new BlogNotFoundException("Blog not found: blog ID: " + blog.getBlogID());
        } catch (BlogNotFoundException blogNotFoundException) {
            log.info("blogNotSavedException {}", blogNotFoundException.getMessage());
            redirectAttributes.addFlashAttribute("saveBlogResponse", blogNotFoundException.getMessage());
        }
    }


    public void getBlogs(Model model) {
        log.info("<--  getBlogs -->");
        try {
            List<Blog> responseList = blogService.getBlogs();
            if (responseList != null) {
                log.info("<--  getBlogs {} -->", responseList.size());
                model.addAttribute("blogs", responseList);
            } else throw new BlogNotFoundException("No Blogs");
        } catch (BlogNotFoundException blogNotFoundException) {
            model.addAttribute("getBlogsResponse", blogNotFoundException.getMessage());
            log.info("<--  getBlogs  blogNotFoundException {} -->", blogNotFoundException.getMessage());
        }
    }

    public void getActiveBlogs(Model model) {
        log.info("<--  getActiveBlogs -->");
        try {
            List<Blog> blogResponse = blogService.getActiveBlogs(BlogStatus.ACTIVE);
            if (!blogResponse.isEmpty()) {
                log.info("<--  getActiveBlogs blogResponse {} -->", blogResponse.size());
                model.addAttribute("blogs", blogResponse);
            } else throw new BlogNotFoundException("No Active Blogs");
        } catch (BlogNotFoundException blogNotFoundException) {
            model.addAttribute("activeBlogResponse", blogNotFoundException.getMessage());
            log.info("<--  getActiveBlogs blogNotFoundException {} -->", blogNotFoundException.getMessage());
        }
    }

    // DOCUMENTS

    public boolean validateFile(MultipartFile file, Model model) throws FileUploadException {
        log.info("<--  validateFile -->");
        try {
            if (file.isEmpty()) {
                log.info("<--  validateFile -- Please select a file to upload. -->");
                throw new FileUploadException("Please select a file to upload.");
            }
            // Security check: Ensure the file name is not a path that could be exploited
            else if (file.getOriginalFilename().contains("..")) {
                log.info("<--  validateFile -- Invalid file name. -->");
                throw new FileUploadException("Invalid file name.");
            } else if (file.getSize() > 1024 * 1024 * 25) { // 25MB
                log.info("<--  validateFile -- File size exceeds the maximum limit (25MB). -->");
                throw new FileUploadException("File size exceeds the maximum limit (25MB).");
            }
            // Security check: Ensure the file content is safe (detect content type)
            else if (!isValidContentType(file)) {
                log.info("<--  validateFile -- Please select a file to upload.-->");
                throw new FileUploadException("Please select a file to upload.");
            }
            return true;
        } catch (FileUploadException fileUploadException) {
            model.addAttribute("validateFileResponse", fileUploadException.getMessage());
            log.info("<--  validateFile -- {} -->", fileUploadException.getMessage());
            return false;
        }
    }

    public CandidateFile createCandidateFile(CandidateFileDTO fileDTO) {
        log.info("<--  createCandidateFile  {} -->", fileDTO);
        CandidateFile file = new CandidateFile();
        Optional<Candidate> candidate = candidateService.getcandidateByID(fileDTO.getCandidateID());
        if (candidate.isPresent()) {
            log.info("<--  createCandidateFile candidate.isPresent( {} -->", candidate.get());
            file.setCandidate(candidate.get());

            file.setContent_type(fileDTO.getCvFile().getContentType());
// save bytes to database
            file.setFile_data(null);

            file.setFile_name(fileDTO.getCvFile().getOriginalFilename());
            file.setFile_size(Long.toString(fileDTO.getCvFile().getSize()));
            file.setDocumentType(fileDTO.getDocumentType());
            file.setCreated(LocalDateTime.now());
            log.info("New CandidateFile {}", file);
            // Local file storage

            String docType = fileDTO.getDocumentType().toString();
            String candidateIDNumber = candidate.get().getId_number();
            String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));


            log.info("fileDirectory/Folder: {}{}", fileDirectory, "CANDIDATE_FILES");
            String directory = fileDirectory + "/CANDIDATE_FILES/" + candidateIDNumber + "/" + docType + "/" + formattedDate;


            // Local storage

            Path storageLocation = null;
            try {
                Path uploadPath = Path.of(directory);
                if (Files.notExists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = fileDTO.getCvFile().getOriginalFilename();
                storageLocation = uploadPath.resolve(fileName);
                Files.copy(fileDTO.getCvFile().getInputStream(), storageLocation, StandardCopyOption.REPLACE_EXISTING);
                log.info("File saved storageLocation: {}", storageLocation);
            } catch (Exception e) {
                log.info("<--  createCandidateFile Exception {} -->", e.getMessage());
            }

            file.setDocumentLocation(storageLocation.toString());


            // Google storage
            //  file.setDocumentLocation(uploadToGCloud(fileDTO));

           /* ObjectMapper objectMapper = new ObjectMapper();
            log.info("creating JSON Object ");
            try {
                // Convert CandidateFile to JSON string
                String json = objectMapper.writeValueAsString(file);
                log.info("Json: \n" + json);
            } catch (JsonProcessingException jsonProcessingException) {
                log.info("Exception processing json {}", jsonProcessingException.getMessage());
            }*/
            log.info("DONE CREATING JSON Object ");

            candidate.get().AddDocument(file);

            fileService.save(file);
            return file;

        } else throw new CandidateNotFoundException("Candidate Not Found");
    }





    public String getDocumentLocation(String term) {
        log.info("<--  getDocumentLocation {} -->", term);
        try {
            String filesResponse = fileService.getDocumentLocation(term);
            if (!filesResponse.isEmpty()) {
                log.info("<--  filesResponse is not empty filesResponse {} -->", filesResponse);
                return filesResponse;
            } else throw new FileContentException("Error trying to find term: " + term);
        } catch (FileContentException e) {
            //model.addAttribute("searchFileContentResponse", e.getMessage());
            log.info("<--  getDocumentLocation FileContentException {} -->", e.getMessage());
        }
        return null;
    }

    public String getDocumentLocation(String term, Model model) {
        log.info("<--  getDocumentLocation {} -->", term);
        try {
            String filesResponse = fileService.getDocumentLocation(term);
            if (filesResponse.isEmpty()) {
                log.info("<--  getDocumentLocation filesResponse {} -->", filesResponse);
                return filesResponse;
            } else throw new FileContentException("Error trying to find term" + term);
        } catch (FileContentException e) {
            model.addAttribute("searchFileContentResponse", e.getMessage());
            log.info("<--  getDocumentLocation FileContentException {} -->", e.getMessage());
        }
        return null;
    }

    public void getFilesFromContent(SearchDocumentsDTO searchDocumentsDTO, Model model) {
        log.info("<--  getFilesFromContent -->");
        try {
            List<String> filesResponse = fileService.searchFileContent(searchDocumentsDTO.getTerm());
            if (!filesResponse.isEmpty()) {
                log.info("<--  getFilesFromContent filesResponse size {} -->", filesResponse.size());
                model.addAttribute("resultCount", filesResponse.size());
                model.addAttribute("results", filesResponse);
                model.addAttribute("term", searchDocumentsDTO.getTerm());
            } else throw new FileContentException("Error trying to find term" + searchDocumentsDTO.getTerm());
        } catch (FileContentException e) {
            model.addAttribute("searchFileContentResponse", e.getMessage());
            log.info("<--  getFilesFromContent FileContentException {} -->", e.getMessage());
        }

    }

    public void searchFiles(Model model, SearchDocumentsDTO searchDocumentsDTO) {
        log.info("<--  searchFiles searchDocumentsDTO {} -->", searchDocumentsDTO);
        try {
            List<String> returnList = fileService.searchFiles(searchDocumentsDTO.getTerm());
            if (!returnList.isEmpty()) {
                model.addAttribute("resultCount", returnList.size());
                model.addAttribute("results", returnList);
                model.addAttribute("term", searchDocumentsDTO);
                log.info("<--  searchFiles returnList size {} -->", returnList.size());
            }
        } catch (NoResultsFoundException noResultsFoundException) {
            log.info("<--  searchFiles returnList size {} -->", noResultsFoundException.getMessage());
            model.addAttribute("searchFilesResponse", noResultsFoundException.getMessage());
        }
    }

    public boolean isValidContentType(MultipartFile file) {
        try {
            String detectedContentType = new Tika().detect(file.getInputStream());
            return detectedContentType.equals("application/pdf") || detectedContentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } catch (IOException e) {
            //log.info(e.getMessage());
            return false;
        }
    }

    public String download(String term, Model model) {
        log.info("<--  download  {} -->", term);
        try {
            String filePathResponse = getDocumentLocation(term, model);
            if (filePathResponse != null) {
                log.info("<--  download filePathResponse {} -->", filePathResponse);
                log.info("<--  download model {} -->", model);
                return filePathResponse;
            } else throw new DocumentLocationError("Failed to get document path");
        } catch (DocumentLocationError documentLocationError) {
            log.info("<--  download documentLocationError {} -->", documentLocationError.getMessage());

        }
        return null;
    }


    // STORAGE

    public void saveCandidateFile(CandidateFile candidateFile) {
        fileService.save(candidateFile);
    }

    public String uploadToGCloud(CandidateFileDTO candidateFileDTO) {
        log.info("<--  uploadToGCloud candidateFileDTO {} -->", candidateFileDTO);
        String response = "";
        try {
            String fileResponse = storageService.uploadFile(candidateFileDTO);
            if (fileResponse.contains("File uploaded Success")) {
                response = fileResponse;
                log.info("<--  uploadToGCloud fileResponse {} -->", fileResponse);
            } else
                throw new SaveFileException("Candidate not save File" + fileResponse);
        } catch (SaveFileException saveFileException) {
            log.info(saveFileException.getMessage());
            response = saveFileException.getMessage();
        }
        return response;
    }

    public boolean publishFileUploadedEvent(Long candidateID, NewApplicationDTO newApplicationDTO) {
        return applicationsEventPublisher.publishFileUploadEvent(candidateID, newApplicationDTO);
    }


    // CLIENTS & CONTACT PERSONS


    public void saveNewClient(RedirectAttributes redirectAttributes, ClientDTO clientDTO) {
        log.info("<--  saveNewClient clientDTO {} -->", clientDTO);
        try {
            Client clientResponse = clientService.saveClient(clientDTO);
            if (clientResponse != null) {
                String saveNewClientResponse = "Client Saved: " + clientResponse.getClientID();
                redirectAttributes.addFlashAttribute("saveNewClientResponse", saveNewClientResponse);
                log.info("<--  saveNewClient saveNewClientResponse {} -->", saveNewClientResponse);
            } else throw new SaveClientException("Failed to save new client {}" + clientDTO.getName());

        } catch (SaveClientException saveClientException) {
            log.info("Employee not found {}", saveClientException.getMessage());
            redirectAttributes.addFlashAttribute("saveNewClientResponse", saveClientException.getMessage());
            log.info("<--  saveNewClient saveClientException {} -->", saveClientException.getMessage());
        }
    }

    // client , findClientByIDResponse
    public void findClientByID(Long clientID, Model model) {
        log.info("<--  findClientByID clientID {} -->", clientID);
        try {
            Client clientResponse = clientService.findClientByID(clientID);
            log.info("<--  findClientByID clientResponse {} -->", clientResponse);
            if (clientResponse != null) {
                model.addAttribute("client", clientResponse);
            } else throw new ClientNotFoundException("Client Not Found");

        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findClientByIDResponse", clientNotFoundException.getMessage());
            log.info("<--  findClientByID clientNotFoundException {} -->", model);
        }
    }

    public void saveNewClientNote(ClientNoteDTO clientNoteDTO, Model model) {
        log.info("<--  findClientByID clientNoteDTO {} -->", clientNoteDTO);
        try {
            Client clientResponse = clientService.findClientByID(clientNoteDTO.getclientID());
            if (clientResponse != null) {
                log.info("<--  findClientByID clientResponse {} -->", clientResponse);
                model.addAttribute("client", clientResponse);
                clientResponse.addNote(clientNoteDTO);
                saveNoteToClient(model, clientResponse);
                model.addAttribute("noteSaved", Boolean.TRUE);
                Set<ClientNote> notes = clientResponse.getNotes();
                model.addAttribute("existingNotes", notes);
                ClientNoteDTO dto = new ClientNoteDTO();
                dto.setClientID(clientNoteDTO.getclientID());
                log.info("clientID: {}", clientNoteDTO.getclientID());
                model.addAttribute("clientNote", dto);

            } else throw new ClientNotFoundException("Client Not Found");

        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findClientByIDResponse", clientNotFoundException.getMessage());
            log.info("<--  findClientByID clientNotFoundException {} -->", clientNotFoundException.getMessage());
        }
    }

    public void findClientNotes(Long clientID, Model model) {
        log.info("<--  findClientNotes clientID {} -->", clientID);
        try {
            Client clientResponse = clientService.findClientByID(clientID);
            if (clientResponse != null) {
                Set<ClientNote> notes = clientResponse.getNotes();
                model.addAttribute("existingNotes", notes);
                ClientNoteDTO dto = new ClientNoteDTO();
                dto.setClientID(clientResponse.getClientID());
                model.addAttribute("clientNote", dto);
                log.info("<--  findClientNotes clientResponse {} -->", clientResponse);
            } else throw new ClientNotFoundException("Client Not Found");

        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findClientByIDResponse", clientNotFoundException.getMessage());
            log.info("<--  findClientNotes clientNotFoundException {} -->", clientNotFoundException.getMessage());
        }
    }

    public void findAllClients(Model model) {
        log.info("<--  findAllClients -->");
        try {
            List<Client> clientList = clientService.getAllClients();
            if (clientList != null) {
                model.addAttribute("clients", clientList);
                log.info("<--  findAllClients clientList {} -->", clientList.size());
            } else throw new ClientNotFoundException("No clients found. contact system administrator");
        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findAllClientsResponse", clientNotFoundException.getMessage());
            log.info("<--  findAllClients clientNotFoundException {} -->", clientNotFoundException.getMessage());
        }
    }/*

    public void saveUpdatedClient(Long clientID, Client updatedClient) {
        Client oc = clientService.findClientByID(clientID);
        if (!oc.getName().equalsIgnoreCase(updatedClient.getName())) {
            oc.setName(updatedClient.getName());
        }

        if (oc.getIndustry() != updatedClient.getIndustry()) {
            oc.setIndustry(updatedClient.getIndustry());
        }
        clientService.saveUpdatedClient(oc);
    }*/


    //  saveUpdatedClientResponse
    public void saveUpdatedClient(Client client, RedirectAttributes redirectAttributes) {
        log.info("<--  saveUpdatedClient {} -->", client);

        try {
            Client c = clientService.findClientByID(client.getClientID());
            if (c != null) {
                if (!client.getName().equalsIgnoreCase(c.getName())) {
                    c.setName(client.getName());
                }
                if (client.getIndustry() != c.getIndustry()) {
                    c.setIndustry(client.getIndustry());
                }
                /*      clientService.saveUpdatedClient(c);
                 */
                Client updatedClient = clientService.saveUpdatedClient(c);
                String updateClientResponse = "client updated " + updatedClient.getName();
                redirectAttributes.addFlashAttribute("saveUpdatedClientResponse", updateClientResponse);
                log.info("<--  saveUpdatedClient  updateClientResponse {} -->", updateClientResponse);
                log.info("<--  saveUpdatedClient  updatedClient {} -->", updatedClient);
            } else throw new SaveUpdatedClientException("Failed to save Updated client " + client.getClientID());


        } catch (SaveUpdatedClientException saveUpdatedClientException) {
            log.info("Exception {}", saveUpdatedClientException.getMessage());
            redirectAttributes.addFlashAttribute("saveUpdatedClientResponse", saveUpdatedClientException.getMessage());
        }
    }

    public void saveNoteToClient(Model model, Client client) {
        log.info("<--  saveNoteToClient  client {} -->", client);
        try {
            Client savedClient = clientService.saveUpdatedClient(client);
            if (savedClient != null) {
                model.addAttribute("saveNoteToClientResponse", "Client Note Saved" + client.getClientID());
                log.info("<--  saveNoteToClient  savedClient {} -->", savedClient);
            } else throw new SaveClientException("Failed to save new client {}" + client.getName());

        } catch (SaveClientException saveClientException) {
            log.info("<--  saveNoteToClient  saveClientException {} -->", saveClientException.getMessage());
            model.addAttribute("saveNoteToClientResponse", saveClientException.getMessage());
        }
    }

    public void addContactToClient(ContactPersonDTO contactPersonDTO, RedirectAttributes redirectAttributes) {
        log.info("<--  addContactToClient  contactPersonDTO {} -->", contactPersonDTO);
        try {
            ContactPerson contactPerson = clientService.addContactToClient(contactPersonDTO);
            if (contactPerson != null) {
                String updatedContactResponse = "Contact Person added" + contactPerson.getFirst_name() + " " + contactPerson.getClient().getName();
                redirectAttributes.addFlashAttribute("addContactPersonResponse", updatedContactResponse);
                log.info("<--  addContactToClient  contactPerson {} -->", contactPerson);
                log.info("<--  addContactToClient  addContactPersonResponse {} -->", updatedContactResponse);

            } else throw new AddContactPersonException("Failed to add contact person");
        } catch (AddContactPersonException addContactPersonException) {
            redirectAttributes.addFlashAttribute("addContactPersonResponse", addContactPersonException.getMessage());
        }
    }


    public void saveUpdatedContactPerson(Long contactPersonID, ContactPersonDTO contactPersonDTO, RedirectAttributes redirectAttributes) {
        log.info("<--  saveUpdatedContactPerson  contactPersonID {}  {} -->", redirectAttributes, contactPersonDTO);
        try {
            ContactPerson contactPerson = clientService.findContactsByID(contactPersonID);

            if (contactPerson != null) {
                log.info("<--  saveUpdatedContactPerson contactPerson {} -->", contactPerson);
                if (contactPerson.getFirst_name() != null
                        && !contactPerson.getFirst_name().equalsIgnoreCase(contactPersonDTO.getFirst_name())) {
                    contactPerson.setFirst_name(contactPersonDTO.getFirst_name());
                }


                if (contactPerson.getLast_name() != null &&
                        !contactPerson.getLast_name().equalsIgnoreCase(contactPersonDTO.getLast_name())) {
                    contactPerson.setLast_name(contactPersonDTO.getLast_name());
                }


                if (contactPerson.getLand_line() != null &&
                        !contactPerson.getLand_line().equalsIgnoreCase(contactPersonDTO.getLand_line())) {
                    contactPerson.setLand_line(contactPersonDTO.getLand_line());
                }


                if (contactPerson.getEmail_address() != null &&
                        !contactPerson.getEmail_address().equalsIgnoreCase(contactPersonDTO.getEmail_address())) {
                    contactPerson.setEmail_address(contactPersonDTO.getEmail_address());
                }


                if (contactPerson.getDesignation() != null) {
                    if (!contactPerson.getDesignation().equalsIgnoreCase(contactPersonDTO.getDesignation())) {
                        contactPerson.setDesignation(contactPersonDTO.getDesignation());
                    }
                } else {
                    contactPerson.setDesignation(contactPersonDTO.getDesignation());
                }

                if (contactPerson.getCell_phone() != null &&
                        !contactPerson.getCell_phone().equalsIgnoreCase(contactPersonDTO.getCell_phone())) {
                    contactPerson.setCell_phone(contactPersonDTO.getCell_phone());
                }

                ContactPerson c = clientService.saveUpdatedContact(contactPerson);
                if (c != null) {
                    String response = "Contact Person Saved " + c.getFirst_name();
                    redirectAttributes.addFlashAttribute("saveUpdatedContactResponse", response);
                    log.info("<--  saveUpdatedContactResponse response {} -->", response);
                }

            } else throw new SaveContactPersonException("Failed to save contact person" + contactPersonID);
        } catch (SaveContactPersonException saveContactPersonException) {
            redirectAttributes.addAttribute("saveUpdatedContactResponse", saveContactPersonException.getMessage());
            log.info("<--  saveUpdatedContactPerson saveContactPersonException {} -->", saveContactPersonException.getMessage());
        }
    }

    public void findContactPersonByClientID(Long contactPersonID, Model model) {
        log.info("<--  findContactPersonByClientID contactPersonID {} -->", contactPersonID);
        try {
            List<ContactPerson> contactPersonList = clientService.findContactPersonsByClientID(contactPersonID);
            if (contactPersonList != null) {
                model.addAttribute("clientID", contactPersonID);
                model.addAttribute("contacts", contactPersonList);
                log.info("<--  findContactPersonByClientID contactPersonList {} -->", contactPersonList.size());
            } else throw new ContactNotFoundException("No contacts found" + contactPersonID);
        } catch (ContactNotFoundException contactNotFoundException) {
            model.addAttribute("findContactPersonByClientID", contactNotFoundException.getMessage());
            log.info("<--  findContactPersonByClientID contactNotFoundException {} -->", contactNotFoundException.getMessage());
        }
    }


    public void findContactPersonByID(Model model, Long contactPersonID) {
        log.info("<--  findContactPersonByID contactPersonID {} -->", contactPersonID);
        try {
            ContactPerson contactPerson = clientService.findContactsByID(contactPersonID);
            if (contactPerson != null) {
                model.addAttribute("contactPerson", contactPerson);
                log.info("<--  findContactPersonByID contactPerson {} -->", contactPerson);
            } else throw new ContactNotFoundException("contactPerson not found" + contactPersonID);
        } catch (ContactNotFoundException contactNotFoundException) {
            model.addAttribute("contactPersonResponse", contactNotFoundException.getMessage());
            log.info("<--  findContactPersonByID contactNotFoundException {} -->", contactNotFoundException.getMessage());
        }
    }

    public void saveSubmissionForm(NewApplicationDTO newApplicationDTO, Model model, RedirectAttributes redirectAttributes) {
        log.info("<--  saveSubmissionForm newApplicationDTO {} -->", newApplicationDTO);
        try {
            boolean validFile = validateFile(newApplicationDTO.getCvFile(), model);
            if (validFile) {
                log.info("<--  saveSubmissionForm validFile {} -->", validFile);
                createCandidateApplication(model, redirectAttributes, newApplicationDTO);
            } else {
                log.info("<--  saveSubmissionForm validFile {} -->", validFile);
                model.addAttribute("vacancyName", newApplicationDTO.getVacancyName());
                model.addAttribute("vacancyID", newApplicationDTO.getVacancyID());
            }
        } catch (FileUploadException fileUploadException) {
            redirectAttributes.addAttribute("saveSubmissionFormResponse",
                    fileUploadException.getMessage());
            log.info("<--  saveSubmissionForm fileUploadException {} -->", fileUploadException.getMessage());
            log.info("<--  saveSubmissionForm redirectAttributes {} -->", redirectAttributes);
        }
    }

    public void addCandidateNote(Long candidateID, Model model) {
        log.info("<--  addCandidateNote candidateID {} -->", candidateID);
        try {
            Optional<Candidate> candidate = candidateService.getcandidateByID(candidateID);
            if (candidate.isPresent()) {
                log.info("<--  addCandidateNote candidate.isPresent {} -->", candidate.get());
                CandidateNoteDTO dto = new CandidateNoteDTO();
                dto.setCandidateID(candidateID);
                log.info("CandidateID: {}", candidateID);
                model.addAttribute("candidate", candidate.get());
                model.addAttribute("candidateNote", dto);

            } else throw new CandidateNotFoundException("Candidate Not Found");

        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("addCandidateNoteResponse", candidateNotFoundException.getMessage());
            log.info("<--  addCandidateNote candidateNotFoundException {} -->", candidateNotFoundException.getMessage());
        }
    }

    public void findCandidateNotes(Long candidateID, Model model) {
        log.info("<--  findCandidateNotes candidateID {} -->", candidateID);
        try {
            Optional<Candidate> candidate = candidateService.getcandidateByID(candidateID);
            if (candidate.isPresent()) {
                log.info("<--  findCandidateNotes candidate.isPresent {} -->", candidate.get());
                Set<CandidateNote> notes = candidate.get().getNotes();
                // sort the set according to date
                model.addAttribute("candidate", candidate.get());
                model.addAttribute("existingNotes", notes);
                CandidateNoteDTO dto = new CandidateNoteDTO();
                dto.setCandidateID(candidateID);
                model.addAttribute("candidateNote", dto);
            } else throw new CandidateNotFoundException("Candidate not found ");
        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("findCandidateNotesResponse", candidateNotFoundException.getMessage());
            log.info("<--  findCandidateNotes candidateNotFoundException {} -->", candidateNotFoundException.getMessage());
        }
    }


    // EVENTS
    public boolean saveSubmissionEvent(Candidate candidate, Long vacancyID) {
        return applicationsEventPublisher.publishSaveSubmissionEvent(candidate, vacancyID);
    }
}
