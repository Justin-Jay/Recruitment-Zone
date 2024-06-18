package za.co.recruitmentzone.service;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tika.Tika;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import za.co.recruitmentzone.application.dto.ApplicationDTO;
import za.co.recruitmentzone.application.exception.ApplicationsNotFoundException;
import za.co.recruitmentzone.blog.dto.BlogDTO;
import za.co.recruitmentzone.blog.dto.BlogImageDTO;
import za.co.recruitmentzone.blog.dto.BlogStatusDTO;
import za.co.recruitmentzone.blog.entity.Blog;
import za.co.recruitmentzone.blog.exception.BlogNotFoundException;
import za.co.recruitmentzone.blog.exception.BlogNotSavedException;
import za.co.recruitmentzone.blog.service.BlogService;
import za.co.recruitmentzone.candidate.entity.CandidateFile;
import za.co.recruitmentzone.candidate.events.CandidateEventPublisher;
import za.co.recruitmentzone.candidate.service.CandidateFileService;
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
import za.co.recruitmentzone.client.dto.*;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.client.entity.ClientFile;
import za.co.recruitmentzone.client.entity.ClientNote;
import za.co.recruitmentzone.client.entity.ContactPerson;
import za.co.recruitmentzone.client.events.ClientEventPublisher;
import za.co.recruitmentzone.client.exception.*;
import za.co.recruitmentzone.client.repository.ClientNoteRepository;
import za.co.recruitmentzone.client.service.ClientFileService;
import za.co.recruitmentzone.client.service.ClientService;
import za.co.recruitmentzone.client.service.ContactPersonService;
import za.co.recruitmentzone.documents.*;
import za.co.recruitmentzone.employee.dto.EmployeeDTO;
import za.co.recruitmentzone.employee.entity.Authority;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.exception.*;
import za.co.recruitmentzone.employee.service.EmployeeService;
import za.co.recruitmentzone.employee.service.TokenVerificationService;
import za.co.recruitmentzone.exception.NoResultsFoundException;
import za.co.recruitmentzone.util.Constants;
import za.co.recruitmentzone.util.enums.*;
import za.co.recruitmentzone.vacancy.dto.VacancyDTO;
import za.co.recruitmentzone.vacancy.dto.VacancyStatusDTO;
import za.co.recruitmentzone.vacancy.entity.Vacancy;
import za.co.recruitmentzone.vacancy.exception.VacancyException;
import za.co.recruitmentzone.vacancy.service.VacancyService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static za.co.recruitmentzone.util.enums.BlogStatus.ACTIVE;
import static za.co.recruitmentzone.util.enums.BlogStatus.PENDING;
import static za.co.recruitmentzone.util.enums.CandidateDocumentType.CURRICULUM_VITAE;
import static za.co.recruitmentzone.util.enums.ROLE.*;

@Service
public class RecruitmentZoneService {

    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneService.class);
    private final ApplicationService applicationService;
    private final CandidateService candidateService;
    private final EmployeeService employeeService;
    private final VacancyService vacancyService;
    private final ClientService clientService;
    private final BlogService blogService;
    private final CandidateFileService candidateFileService;
    private final PasswordEncoder passwordEncoder;
    private final CandidateEventPublisher candidateEventPublisher;
    private final ClientFileService clientFileService;
    private final ClientEventPublisher clientEventPublisher;
    private final TokenVerificationService tokenVerificationService;
    private final ContactPersonService contactPersonService;
    private final ClientNoteRepository clientNoteRepository;


    @Value("${file.directory}")
    String fileDirectory;

    @Value("${upload.folder}")
    String Folder;

   // private final String BLOG_LOCAL_FULL_PATH = "/blog-images/";
    private final String BLOG_VOLUME_FULL_PATH = "/home/jenkins/RecruitmentZoneApplication/BlogImages/";


    public RecruitmentZoneService(ApplicationService applicationService, VacancyService vacancyService, CandidateService candidateService,
                                  EmployeeService employeeService, ClientService clientService, BlogService blogService, CandidateFileService candidateFileService, PasswordEncoder passwordEncoder, CandidateEventPublisher candidateEventPublisher, ClientFileService clientFileService, ClientEventPublisher clientEventPublisher, TokenVerificationService tokenVerificationService, ContactPersonService contactPersonService, ClientNoteRepository clientNoteRepository) {
        this.applicationService = applicationService;
        this.vacancyService = vacancyService;
        this.candidateService = candidateService;
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.blogService = blogService;
        this.candidateFileService = candidateFileService;
        this.passwordEncoder = passwordEncoder;
        this.candidateEventPublisher = candidateEventPublisher;
        this.clientFileService = clientFileService;
        this.clientEventPublisher = clientEventPublisher;
        this.tokenVerificationService = tokenVerificationService;
        this.contactPersonService = contactPersonService;
        this.clientNoteRepository = clientNoteRepository;
    }

    // APPLICATIONS


    public void findAllApplications(Model model, int pageNo, int pageSize, String sortField, String sortDirection) {
        log.info("<-- findAllApplications -->");
        try {
            //List<Application> allApplications = applicationService.findApplications();
            Page<Application> allApplications = applicationService.findPaginatedApplications(pageNo, pageSize, sortField,
                    sortDirection);

            if (!allApplications.isEmpty()) {
                log.info("<-- findAllApplications  allApplications.getTotalPages {} -->", allApplications.getTotalPages());
                log.info("<-- findAllApplications  allApplications.getTotalElements {} -->", allApplications.getTotalElements());

                model.addAttribute("applicationsList", allApplications);
                model.addAttribute("totalPages", allApplications.getTotalPages());
                model.addAttribute("totalItems", allApplications.getTotalElements());
                model.addAttribute("currentPage", pageNo);
                model.addAttribute("sortField", sortField);
                model.addAttribute("sortDir", sortDirection);
                model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

            } else throw new ApplicationsNotFoundException("No applications found");
        } catch (ApplicationsNotFoundException applicationsNotFoundException) {
            model.addAttribute("findAllApplicationsResponse", applicationsNotFoundException.getMessage());
            log.info("<-- findAllApplications  applicationsNotFoundException {} -->", applicationsNotFoundException.getMessage());
        }
    }

/*    public void findAllApplications(Model model) {
        log.info("<-- findAllApplications -->");
        try {
            List<Application> allApplications = applicationService.findApplications();
            if (!allApplications.isEmpty()) {
                model.addAttribute("applicationsList", allApplications);
                log.info("<-- findAllApplications  allApplications.Size {} -->", allApplications.size());
            } else throw new ApplicationsNotFoundException("No applications found");
        } catch (ApplicationsNotFoundException applicationsNotFoundException) {
            model.addAttribute("findAllApplicationsResponse", applicationsNotFoundException.getMessage());
            log.info("<-- findAllApplications  applicationsNotFoundException {} -->", applicationsNotFoundException.getMessage());
        }
    }*/

    public void vacancyApplicationForm(Model model, Long vacancyID) {
        log.info("<--  vacancyApplicationForm vacancyID {} -->", vacancyID);
        try {
            Vacancy vacancy = vacancyService.findById(vacancyID);
            if (vacancy != null) {
                log.info("<--  vacancyApplicationForm vacancy != null {} -->", vacancy.printVacancy());
                // model.addAttribute("vacancy", vacancy);
                // model.addAttribute("vacancyName", vacancy.getJobTitle());
                //model.addAttribute("vacancyID", vacancyID);
                model.addAttribute("newApplicationDTO", new NewApplicationDTO(vacancyID, vacancy.getJobTitle()));
            } else throw new VacancyException("Vacancy found");

        } catch (VacancyException vacancyException) {
            model.addAttribute("vacancyNotFound", vacancyException.getMessage());
            model.addAttribute("newApplicationDTO", new NewApplicationDTO());
            log.info("<-- vacancyApplicationForm vacancyException {} -->", vacancyException.getMessage());
        }
    }

    public void saveApplication(NewApplicationDTO newApplicationDTO, Model model) {
        log.info("<-- saveSubmissionForm newApplicationDTO {} -->", newApplicationDTO);
        try {

            boolean validFile = validateFile(newApplicationDTO.getDocumentAttachment());
            if (validFile) {
                log.info("<-- saveSubmissionForm validFile {} -->", validFile);
                submitCandidateApplication(newApplicationDTO, model);
            }
        } catch (FileUploadException fileUploadException) {
            model.addAttribute("invalidFileException",
                    fileUploadException.getMessage());
            log.info("<-- saveSubmissionForm fileUploadException {} -->", fileUploadException.getMessage());
        }

    }

    public void findApplicationByID(Long applicationID, Model model) {
        log.info("<-- findApplicationByID  applicationID: {} -->", applicationID);
        try {
            Application optionalApplication = applicationService.findApplicationByID(applicationID);
            log.info("<-- findApplicationByID Loading application to view \n {} -->", optionalApplication.printApplication());
            ApplicationDTO applicationDTO = new ApplicationDTO();

            applicationDTO.setApplicationID(optionalApplication.getApplicationID());
            applicationDTO.setCandidateName(optionalApplication.getCandidate().getFirst_name());
            applicationDTO.setVacancyName(optionalApplication.getVacancy().getJobTitle());
            applicationDTO.setCreated(optionalApplication.getCreated());
            applicationDTO.setDate_received(optionalApplication.getDate_received());
            applicationDTO.setSubmission_date(optionalApplication.getSubmission_date());
            applicationDTO.setStatus(optionalApplication.getStatus());

            model.addAttribute("vacancyApplication", applicationDTO);
            log.info("<-- findApplicationByID DTO LOADED {} -->", applicationDTO.printApplicationDTO());
        } catch (ApplicationsNotFoundException applicationsNotFoundException) {
            model.addAttribute("findApplicationByIDResponse", applicationsNotFoundException.getMessage());
            log.info("<-- findApplicationByID applicationsNotFoundException {} -->", applicationsNotFoundException.getMessage());
        }
    }

    public void saveUpdatedApplicationStatus(Model model, ApplicationDTO applicationDTO) {
        log.info("<-- saveUpdatedApplicationStatus  applicationDTO: {}  ", applicationDTO.printApplicationDTO());
        try {
            boolean saveResult = applicationService.saveUpdatedStatus(applicationDTO);
            if (saveResult) {
                String outcome = "Application ID " + applicationDTO.getApplicationID() + " Status has been updated to " + applicationDTO.printStatus();
                model.addAttribute("saveApplicationStatusResponse", outcome);
                log.info("<-- saveUpdatedApplicationStatus  saveResult: {} outcome {} -->", saveResult, outcome);

           /*     List<Application> allApplications = applicationService.findApplications();
                if (!allApplications.isEmpty()) {
                    model.addAttribute("applicationsList", allApplications);
                    log.info("<-- findAllApplications  allApplications.Size {} -->", allApplications.size());
                } else throw new ApplicationsNotFoundException("No applications found");
*/

            }
        } catch (ApplicationsNotFoundException applicationsNotFoundException) {
            model.addAttribute("saveApplicationStatusResponse", applicationsNotFoundException.getMessage());
            log.info("<-- saveUpdatedApplicationStatus  applicationsNotFoundException: {} -->", applicationsNotFoundException.getMessage());
        }
    }


    // CANDIDATES

    public void findCandidate(long candidateID, Model model) {
        try {
            log.info("<--  findCandidate candidateID {} -->", candidateID);
            Candidate candidate = candidateService.getcandidateByID(candidateID);

       /* CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setFirst_name(candidate.getFirst_name());
        candidateDTO.setLast_name(candidate.getLast_name());
        candidateDTO.setId_number(candidate.getId_number());
        candidateDTO.setEmail_address(candidate.getEmail_address());
        candidateDTO.setPhone_number(candidate.getPhone_number());
        candidateDTO.setCurrent_province(candidate.getCurrent_province());
        candidateDTO.setCurrent_role(candidate.getCurrent_role());
        candidateDTO.setCurrent_employer(candidate.getCurrent_employer());
        candidateDTO.setSeniority_level(candidate.getSeniority_level());
        candidateDTO.setEducation_level(candidate.getEducation_level());
        candidateDTO.setRelocation(candidate.getRelocation());
*/
            model.addAttribute("candidate", candidate);

        } catch (CandidateNotFoundException candidateNotFoundException) {
            log.info("<--  findCandidate candidateNotFoundException {} -->", candidateNotFoundException.getMessage());
            model.addAttribute("findCandidateResponse", candidateNotFoundException.getMessage());
        }

    }


    public void saveUpdatedCandidate(Candidate candidate, Model model) {
        log.info("<--  saveUpdatedCandidate candidate {} -->", candidate.printCandidate());

        // CandidateNotFoundException
        try {
            Candidate existingCandidate = candidateService.getcandidateByID(candidate.getCandidateID());
            if (existingCandidate != null) {
                log.info("<--  saveUpdatedCandidate - existingCandidate {} -->", existingCandidate.printCandidate());

                if (!candidate.getFirst_name().equalsIgnoreCase(existingCandidate.getFirst_name())) {
                    existingCandidate.setFirst_name(candidate.getFirst_name());
                }

                if (!candidate.getLast_name().equalsIgnoreCase(existingCandidate.getLast_name())) {
                    existingCandidate.setLast_name(candidate.getLast_name());
                }

                if (!candidate.getId_number().equalsIgnoreCase(existingCandidate.getId_number())) {
                    existingCandidate.setLast_name(candidate.getLast_name());
                }

                if (!candidate.getEmail_address().equalsIgnoreCase(existingCandidate.getEmail_address())) {
                    existingCandidate.setEmail_address(candidate.getEmail_address());
                }

                if (!candidate.getPhone_number().equalsIgnoreCase(existingCandidate.getPhone_number())) {
                    existingCandidate.setPhone_number(candidate.getPhone_number());
                }
                if (candidate.getCurrent_province() != existingCandidate.getCurrent_province()) {
                    existingCandidate.setCurrent_province(candidate.getCurrent_province());
                }

                if (!candidate.getCurrent_role().equalsIgnoreCase(existingCandidate.getCurrent_role())) {
                    existingCandidate.setLast_name(candidate.getLast_name());
                }
                if (!candidate.getCurrent_employer().equalsIgnoreCase(existingCandidate.getCurrent_employer())) {
                    existingCandidate.setCurrent_employer(candidate.getCurrent_employer());
                }

                if (!candidate.getSeniority_level().equalsIgnoreCase(existingCandidate.getSeniority_level())) {
                    existingCandidate.setSeniority_level(candidate.getSeniority_level());
                }

                if (candidate.getEducation_level() != existingCandidate.getEducation_level()) {
                    existingCandidate.setEducation_level(candidate.getEducation_level());
                }

                if (candidate.getRelocation() != existingCandidate.getRelocation()) {
                    existingCandidate.setRelocation(candidate.getRelocation());
                }


                candidateService.save(existingCandidate);

                model.addAttribute("saveUpdatedCandidateResponse", "Candidate Updated " + candidate.getFirst_name());
            }
        } catch (CandidateNotFoundException candidateNotFoundException) {

            log.info("<--  saveUpdatedCandidate candidateNotFoundException {} -->", candidateNotFoundException.getMessage());
            model.addAttribute("saveUpdatedCandidateResponse", candidateNotFoundException.getMessage());
        }


    }

    //  candidate ,existingNotes , candidateNoteDTO
    public void addCandidateNote(Model model, Long candidateID, int pageSize) {
        log.info("<--  addCandidateNote candidateID {} -->", candidateID);
        try {
            Candidate candidate = candidateService.getcandidateByID(candidateID);
            log.info("<--  addCandidateNote candidate.isPresent {} -->", candidate.printCandidate());
            CandidateNoteDTO candidateNoteDTO = new CandidateNoteDTO();
            candidateNoteDTO.setCandidateID(candidateID);
            log.info("<-- addCandidateNote CandidateID: {} -->", candidateID);
            model.addAttribute("candidate", candidate);

            // List<CandidateNote> notes = candidate.getNotes();
            Page<CandidateNote> notes = candidateService.findPaginatedCandidateNotes(1, pageSize, "dateCaptured",
                    "desc", candidate);
            log.info("<-- addCandidateNote notes.getTotalPages {} -->", notes.getTotalPages());
            log.info("<-- addCandidateNote notes.getTotalElements {} -->", notes.getTotalElements());
            if (notes != null) {
                model.addAttribute("existingNotes", notes);
                model.addAttribute("totalPages", notes.getTotalPages());
                model.addAttribute("totalItems", notes.getTotalElements());
                model.addAttribute("currentPage", 1);
                model.addAttribute("sortField", "dateCaptured");
                model.addAttribute("sortDir", "desc");
                model.addAttribute("reverseSortDir", "asc".equals("asc") ? "desc" : "asc");
                model.addAttribute("candidateNoteDTO", candidateNoteDTO);

            } else throw new ClientNotFoundException("No clients found. contact system administrator");

        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("addCandidateNoteResponse", candidateNotFoundException.getMessage());
            log.info("<--  addCandidateNote candidateNotFoundException {} -->", candidateNotFoundException.getMessage());
        }
    }

    public void reloadCandidateNote(Long candidateID, Model model) {
        log.info("<--  reloadCandidateNote candidateID {} -->", candidateID);
        try {
            Candidate candidate = candidateService.getcandidateByID(candidateID);
            log.info("<--  reloadCandidateNote candidate.isPresent {} -->", candidate.printCandidate());

            log.info("<-- reloadCandidateNote CandidateID: {} --> ", candidateID);
            model.addAttribute("candidate", candidate);
            List<CandidateNote> notes = candidate.getNotes();
            model.addAttribute("existingNotes", notes);
        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("addCandidateNoteResponse", candidateNotFoundException.getMessage());
            log.info("<--  reloadCandidateNote candidateNotFoundException {} -->", candidateNotFoundException.getMessage());
        }
    }
    // noteSaved , saveCandidateResponse

    public void saveCandidateNote(CandidateNoteDTO candidateNote, Model model) {
        log.info("<-- saveCandidateNote  candidateNote {} -->", candidateNote.printCandidateNote());
        Long candidateID = candidateNote.getCandidateID();
        try {
            Candidate candidate = candidateService.getcandidateByID(candidateID);
            log.info("<-- saveCandidateNote  candidate {} -->", candidate.printCandidate());
            //candidateNote.setDateCaptured(LocalDateTime.now());
            CandidateNote note = candidate.addNote(candidateNote);
            candidateService.saveCandidateNote(note);
            try {
                Candidate savedCandidate = candidateService.save(candidate);
                if (savedCandidate != null) {
                    model.addAttribute("noteSaved", "Note Added");
                    int pageSize = 5;
                    //
                    addCandidateNote(model, savedCandidate.getCandidateID(), pageSize);
                    log.info("<-- saveCandidateNote  savedCandidate {} -->", savedCandidate.printCandidate());
                } else
                    throw new SaveCandidateException("Failed to save candidate : " + candidate.getCandidateID());

            } catch (SaveCandidateException saveCandidateException) {
                model.addAttribute("noteSaved", Boolean.FALSE);
                model.addAttribute("saveCandidateResponse", saveCandidateException.getMessage());
                log.info("<-- saveCandidateNote  noteSaved {} -->", false);
                log.info("<-- saveCandidateNote  saveCandidateException {} -->", saveCandidateException.getMessage());
            }
        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("noteSaved", Boolean.FALSE);
            model.addAttribute("saveCandidateResponse", candidateNotFoundException.getMessage());
            log.info("<-- saveCandidateNote  saveCandidateException {} -->", candidateNotFoundException.getMessage());
        }
    }

    public void findCandidateNotes(Long candidateID, Model model) {
        log.info("<--  findCandidateNotes candidateID {} -->", candidateID);
        try {

            Candidate candidate = candidateService.getcandidateByID(candidateID);
            log.info("<--  findCandidateNotes candidate != null {} -->", candidate.printCandidate());
            List<CandidateNote> notes = candidate.getNotes();
            // sort the set according to date
            model.addAttribute("candidate", candidate);
            model.addAttribute("existingNotes", notes);

        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("findCandidateNotesResponse", candidateNotFoundException.getMessage());
            log.info("<--  findCandidateNotes candidateNotFoundException {} -->", candidateNotFoundException.getMessage());
        }
    }

    public void findCandidateNotes(Model model, long candidateID, int pageNo, int pageSize, String sortField, String sortDirection) {
        log.info("<--  findCandidateNotes -->");
        try {
            Candidate candidate = candidateService.getcandidateByID(candidateID);
            if (candidate != null) {

                // List<ClientNote> notes = clientResponse.getNotes();
                Page<CandidateNote> notes = candidateService.findPaginatedCandidateNotes(pageNo, pageSize, sortField,
                        sortDirection, candidate);

                model.addAttribute("existingNotes", notes);
                model.addAttribute("candidate", candidate);

                if (notes.isEmpty() && notes != null) {
                    model.addAttribute("noNotesFound", Boolean.TRUE);
                }

                if (notes != null) {
                    //model.addAttribute("clientList", notes);
                    model.addAttribute("totalPages", notes.getTotalPages());
                    model.addAttribute("totalItems", notes.getTotalElements());
                    model.addAttribute("currentPage", pageNo);
                    model.addAttribute("sortField", sortField);
                    model.addAttribute("sortDir", sortDirection);
                    model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

                } else throw new CandidateNotFoundException("No clients found. contact system administrator");


            } else throw new ClientNotFoundException("Client Note Found");

        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("findCandidateNotes", candidateNotFoundException.getMessage());
            log.info("<--  findAllClients clientNotFoundException {} -->", candidateNotFoundException.getMessage());
        }
    }

    public void findCandidateDocuments(Model model, Long candidateID) {
        log.info("<-- findCandidateDocuments  {} -->", candidateID);
        try {

            Candidate candidate = candidateService.getcandidateByID(candidateID);
            //List<CandidateFile> candidateDocuments = candidateFileService.getFiles(candidate.getCandidateID());
            model.addAttribute("candidate", candidate);
            model.addAttribute("existingDocuments", candidate.getDocuments());
            //model.addAttribute("candidateID", candidate.getCandidateID());
            CandidateFileDTO fileDTO = new CandidateFileDTO();
            fileDTO.setCandidateID(candidate.getCandidateID());
            model.addAttribute("candidateFileDTO", fileDTO);

            log.info("<-- findCandidateDocuments candidate {} -->", candidate.printCandidate());
            log.info("<-- findCandidateDocuments candidateDocuments size {} -->", candidate.getDocuments().size());

        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("findCandidateDocumentsResponse", candidateNotFoundException.getMessage());
            log.info("<-- findCandidateDocuments candidateNotFoundException {} -->", candidateNotFoundException.getMessage());
        }
    }

    public void findCandidateDocuments(Model model, Long candidateID, int pageNo, int pageSize, String sortField, String sortDirection) {
        log.info("<-- findCandidateDocuments  {} -->", candidateID);
        try {

            Candidate candidate = candidateService.getcandidateByID(candidateID);
            //List<CandidateFile> candidateDocuments = candidateFileService.getFiles(candidate.getCandidateID());
            model.addAttribute("candidate", candidate);

            Page<CandidateFile> docs = candidateFileService.findPaginatedCandidateDocs(pageNo, pageSize, sortField,
                    sortDirection, candidate);


            model.addAttribute("candidate", candidate);

            if (docs.isEmpty() && docs != null) {
                model.addAttribute("noDocsFound", Boolean.TRUE);
            }

            if (docs != null) {
                model.addAttribute("existingDocuments", docs);
                model.addAttribute("totalPages", docs.getTotalPages());
                model.addAttribute("totalItems", docs.getTotalElements());
                model.addAttribute("currentPage", pageNo);
                model.addAttribute("sortField", sortField);
                model.addAttribute("sortDir", sortDirection);
                model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

            } else throw new CandidateNotFoundException("No clients found. contact system administrator");


            //model.addAttribute("candidateID", candidate.getCandidateID());
            CandidateFileDTO fileDTO = new CandidateFileDTO();
            fileDTO.setCandidateID(candidate.getCandidateID());
            model.addAttribute("candidateFileDTO", fileDTO);

            log.info("<-- findCandidateDocuments candidate {} -->", candidate.printCandidate());
            log.info("<-- findCandidateDocuments candidateDocuments size {} -->", candidate.getDocuments().size());

        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("findCandidateDocumentsResponse", candidateNotFoundException.getMessage());
            log.info("<-- findCandidateDocuments candidateNotFoundException {} -->", candidateNotFoundException.getMessage());
        }
    }

    public void reloadCandidateDocuments(Model model, Long candidateID) {
        log.info("<-- findCandidateDocuments  {} -->", candidateID);
        try {
            Candidate candidate = candidateService.getcandidateByID(candidateID);
            List<CandidateFile> candidateDocuments = candidateFileService.getFiles(candidate.getCandidateID());
            model.addAttribute("candidate", candidate);
            model.addAttribute("existingDocuments", candidateDocuments);
            log.info("<-- findCandidateDocuments candidate {} -->", candidate.printCandidate());
            log.info("<-- findCandidateDocuments candidateDocuments size {} -->", candidateDocuments.size());

        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("findCandidateDocumentsResponse", candidateNotFoundException.getMessage());
            log.info("<-- findCandidateDocuments candidateNotFoundException {} -->", candidateNotFoundException.getMessage());
        }
    }

/*    public void findAllCandidates(Model model) {
        log.info("<-- findAllCandidates  -->");
        try {
            List<Candidate> responseList = candidateService.getCandidates();
            if (!responseList.isEmpty()) {
                model.addAttribute("candidateList", responseList);
                log.info("<-- findAllCandidates responseList size {} -->", responseList.size());
            } else throw new CandidateNotFoundException("No Candidates Found");
        } catch (CandidateNotFoundException candidateNotFoundException) {
            model.addAttribute("findAllCandidatesResponse", candidateNotFoundException.getMessage());
            log.info("<-- findAllCandidates candidateNotFoundException {} -->", candidateNotFoundException.getMessage());
        }
    }*/

    public void findAllCandidates(Model model, int pageNo, int pageSize, String sortField, String sortDirection) {
        log.info("<--  findAllClients Paginated -->");
        try {
            // List<ClientNote> notes = clientResponse.getNotes();
            Page<Candidate> responseList = candidateService.findPaginatedCandidates(pageNo, pageSize, sortField,
                    sortDirection);
            if (responseList != null) {
                model.addAttribute("candidateList", responseList);
                model.addAttribute("totalPages", responseList.getTotalPages());
                model.addAttribute("totalItems", responseList.getTotalElements());
                model.addAttribute("currentPage", pageNo);
                model.addAttribute("sortField", sortField);
                model.addAttribute("sortDir", sortDirection);
                model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

            } else throw new ClientNotFoundException("No clients found. contact system administrator");

        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findAllClientsResponse", clientNotFoundException.getMessage());
            log.info("<--  findAllClients clientNotFoundException {} -->", clientNotFoundException.getMessage());
        }
    }

    public void submitCandidateApplication(NewApplicationDTO newApplicationDTO, Model model) {
        log.info("<-- createCandidateApplication  newApplicationDTO: {} -->", newApplicationDTO.printNewApplicationDTO());
        try {
            Application application = createApplication(newApplicationDTO);
            if (application != null) {
                String outcome = "Application submitted successfully. Agent will be in touch";
                // Application submitted successfully. Agent will be in touch
                model.addAttribute("applicationOutcome", outcome
                );
                log.info("<-- createCandidateApplication  application != null : {} -->", outcome);
            }
        } catch (CandidateException e) {
            log.info("<-- createCandidateApplication  CandidateException: {} -->", e.getMessage());
            model.addAttribute("createCandidateApplicationResponse", "Failed to create application... Please try again.");
        }
    }

    public void createCandidateApplication(NewApplicationDTO newApplicationDTO, Model model) {
        log.info("<-- createCandidateApplication  newApplicationDTO: {} -->", newApplicationDTO.printNewApplicationDTO());
        try {
            Application application = createApplication(newApplicationDTO);
            if (application != null) {
                String outcome = "Candidate created successfully.";
                // Application submitted successfully. Agent will be in touch
                model.addAttribute("creationOutcome", outcome
                );
                // reload candidate
                findCandidateNotes(application.getCandidate().getCandidateID(), model);

                Candidate candidate = application.getCandidate();
                model.addAttribute("candidate", candidate);
                model.addAttribute("existingNotes", candidate.getNotes());
                CandidateNoteDTO candidateNoteDTO = new CandidateNoteDTO();
                candidateNoteDTO.setCandidateID(candidate.getCandidateID());
                model.addAttribute("candidateNoteDTO", candidateNoteDTO);
                log.info("<-- createCandidateApplication  application != null : {} -->", outcome);
            }
        } catch (CandidateException e) {
            log.info("<-- createCandidateApplication  CandidateException: {} -->", e.getMessage());
            model.addAttribute("createCandidateApplicationResponse", "Failed to create application");
        }
    }

    public Application createApplication(NewApplicationDTO newApplicationDTO) {
        log.info("<-- createApplication newApplicationDTO {} -->", newApplicationDTO.printNewApplicationDTO());

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
        //candidate.setCreated(LocalDateTime.now());
        log.info("New Candidate \n {}", candidate.printCandidate());

        // create candidate and save and link with document
        candidate = candidateService.save(candidate);
        log.info("Candidate Saved");
        // create application  and link candidate
        CandidateFileDTO fileDTO = new CandidateFileDTO();
        fileDTO.setCandidateID(candidate.getCandidateID());
        fileDTO.setCandidateIDNumber(newApplicationDTO.getId_number());
        fileDTO.setDocumentAttachment(newApplicationDTO.getDocumentAttachment());
        fileDTO.setDocumentType(CURRICULUM_VITAE);
        CandidateFile file = new CandidateFile();
        try {
            file = createCandidateFile(fileDTO);
        } catch (SaveFileException e) {
            log.info("Failed to create file \n {}", e.getMessage());
        }
        Application application = new Application();
        // link application with vacancy
        Vacancy v = vacancyService.findById(newApplicationDTO.getVacancyID());
        v.addApplication(application);
        vacancyService.save(v);
        // link candidate with application
        candidate.AddApplication(application);
        candidateService.save(candidate);
        log.info("About to Save New Application \n {}", application.printApplication());

        application = applicationService.save(application);
        if (application.getApplicationID() != null) {
            log.info("<-- createApplication application {} -->", application.printApplication());

            candidateEventPublisher.publishCandidateGoogleFileEvent(file.getFileID(), file);

            return application;
        } else throw new CandidateException("Failed to create Candidate Application:");
    }

    public void findClientDocuments(Model model, Long clientID) {
        log.info("<-- findClientDocuments  {} -->", clientID);
        try {
            Client client = clientService.findClientByID(clientID);
            // List<ClientFile> clientFiles = clientFileService.getFiles(client.getClientID());
            model.addAttribute("existingDocuments", client.getDocuments());
            model.addAttribute("client", client);
            model.addAttribute("vacancyID", clientID);
            ClientFileDTO fileDTO = new ClientFileDTO();
            fileDTO.setClientID(clientID);
            model.addAttribute("clientFileDTO", fileDTO);

            log.info("<-- findClientDocuments client {} -->", client.printClient());
            log.info("<-- findClientDocuments candidateDocuments size {} -->", client.getDocuments().size());

        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findClientDocuments", clientNotFoundException.getMessage());
            log.info("<-- findClientDocuments clientNotFoundException {} -->", clientNotFoundException.getMessage());
        }
    }

    public void findVacancyDocuments(Model model, Long vacancyID, int pageNo, int pageSize, String sortField, String sortDirection) {
        log.info("<-- findVacancyDocuments vacancyID = {} -->", vacancyID);
        try {
            Vacancy v = vacancyService.findById(vacancyID);
//            List<ClientFile> vacancyDocs = clientFileService.loadVacancyDocs(vacancyID);
            Page<ClientFile> vacancyDocs = clientFileService.findPaginatedVacancyDocs(pageNo, pageSize, sortField,
                    sortDirection, v);

            ClientFileDTO fileDTO = new ClientFileDTO();

            fileDTO.setVacancyID(v.getVacancyID());
            fileDTO.setClientID(v.getTheClientID());
            model.addAttribute("vacancy", v);

            model.addAttribute("existingDocuments", vacancyDocs);
            model.addAttribute("totalPages", vacancyDocs.getTotalPages());
            model.addAttribute("totalItems", vacancyDocs.getTotalElements());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDirection);
            model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");


            model.addAttribute("clientFileDTO", fileDTO);

            log.info("<-- findVacancyDocuments getTotalPages {} -->", vacancyDocs.getTotalPages());
        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findVacancyDocumentsResponse", clientNotFoundException.getMessage());
        }
    }

    public void reloadVacancyDocuments(Model model, Long vacancyID, int pageNo, int pageSize, String sortField, String sortDirection) {
        log.info("<-- findVacancyDocuments vacancyID = {} -->", vacancyID);
        try {
            Vacancy v = vacancyService.findById(vacancyID);
//            List<ClientFile> vacancyDocs = clientFileService.loadVacancyDocs(vacancyID);
            Page<ClientFile> vacancyDocs = clientFileService.findPaginatedVacancyDocs(pageNo, pageSize, sortField,
                    sortDirection, v);

            model.addAttribute("vacancy", v);

            model.addAttribute("existingDocuments", vacancyDocs);
            model.addAttribute("totalPages", vacancyDocs.getTotalPages());
            model.addAttribute("totalItems", vacancyDocs.getTotalElements());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDirection);
            model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");


            log.info("<-- findVacancyDocuments getTotalPages {} -->", vacancyDocs.getTotalPages());
        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findVacancyDocumentsResponse", clientNotFoundException.getMessage());
        }
    }

    // EMPLOYEE

    public void findEmployeeByID(Long employeeID, Model model) {
        try {
            Employee employeeResponse = employeeService.getEmployeeByid(employeeID);
            model.addAttribute("employee", employeeResponse);
        } catch (EmployeeNotFoundException e) {
            model.addAttribute("findEmployeeByIDResponse", e.getMessage());
        }
    }

    public void findAllEmployees(Model model) {
        try {
            List<Employee> responseList = employeeService.getEmployees();
            if (!responseList.isEmpty()) {
                model.addAttribute("employeeList", responseList);
            } else throw new EmployeeNotFoundException("No employees found. contact system administrator");
        } catch (EmployeeNotFoundException e) {
            model.addAttribute("loadEmployeesResponse", e.getMessage());
        }
    }

    public void saveNewEmployee(EmployeeDTO employeeDTO, HttpServletRequest request, Model model, Principal principal) {
        employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        try {
            Employee employeeResponse = employeeService.saveNewEmployee(employeeDTO, Constants.applicationURL(request));
            if (employeeResponse != null) {
                log.info("<--  saveNewEmployee {} -->", employeeResponse.printEmployee());
                model.addAttribute("saveNewEmployeeResponse", "New Employee created: " + employeeResponse.getUsername());
                loadAuthorities(principal, model);
                findAllEmployees(model);
            } else throw new UserAlreadyExistsException("User Already Exists");
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            log.info(userAlreadyExistsException.getMessage());
            model.addAttribute("employeeDTO", new EmployeeDTO());
            loadAuthorities(principal, model);
            findAllEmployees(model);
            model.addAttribute("saveNewEmployeeResponse", userAlreadyExistsException.getMessage());
        }
    }

    public void saveExistingEmployee(Principal principal, Employee employee, Model model) {
        log.info("<--  saveExistingEmployee {} -->", employee.printEmployee());
        try {
            boolean employeeResponse = employeeService.saveUpdatedEmployee(employee);
            log.info("<--  saveExistingEmployee - Employee Saved {} -->", employeeResponse);
            if (employeeResponse) {
                model.addAttribute("saveExistingEmployeeResponse", "Employee updated");
                loadAuthorities(principal, model);
                findAllEmployees(model);
            } else throw new EmployeeNotFoundException("Failed to update employee");
        } catch (EmployeeNotFoundException | EmployeeNotSavedException e) {
            loadAuthorities(principal, model);
            findAllEmployees(model);
            model.addAttribute("saveExistingEmployeeResponse", e.getMessage());
            log.info("<--  saveExistingEmployee EmployeeNotFoundException {} -->", e.getMessage());
        }
    }

    public void enableEmployee(Principal principal, Employee employee, Model model) {
        log.info("<--  saveExistingEmployee {} -->", employee.printEmployee());
        try {
            boolean employeeResponse = employeeService.enableEmployee(employee);
            log.info("<--  saveExistingEmployee - Employee Saved {} -->", employeeResponse);
            if (employeeResponse) {
                model.addAttribute("enableEmployeeResponse", "Employee activated");
                loadAuthorities(principal, model);
                findAllEmployees(model);
            } else throw new EmployeeNotFoundException("Failed to update employee");
        } catch (EmployeeNotFoundException | EmployeeNotSavedException e) {
            loadAuthorities(principal, model);
            findAllEmployees(model);
            model.addAttribute("enableEmployeeResponse", e.getMessage());
            log.info("<--  enableEmployee EmployeeNotFoundException | EmployeeNotSavedException {} -->", e.getMessage());
        }
    }

    public void loadAuthorities(Principal principal, Model model) {
        log.info("<--  loadAuthorities for principal: {} -->", principal.getName());

        try {
            EmployeeDTO newDto = new EmployeeDTO();
            String agent = principal.getName();
            newDto.setAdminAgent(agent);
            model.addAttribute("employeeDTO", newDto);

            log.info("<-- principal email: {} -->", agent);
            Employee e = employeeService.findEmployeeByUsername(agent);
            try {
                List<ROLE> authorities = findEmployeeAuthorities(e);
                //authorities = findAllAuthorities();
                if (authorities != null) {
                    model.addAttribute("authorities", authorities);
                    log.info("<--  loadAuthorities authorities != null {} -->", authorities);
                } else throw new RolesNotFoundException("Failed to load Authorities");

            } catch (RolesNotFoundException rolesNotFoundException) {
                log.info("<--  loadAuthorities rolesNotFoundException {} -->", rolesNotFoundException.getMessage());
                model.addAttribute("loadAuthoritiesResponse", rolesNotFoundException.getMessage());
            }
        } catch (EmployeeNotFoundException e) {
            model.addAttribute("loadAuthoritiesResponse", e.getMessage());
            log.info("<--  loadAuthorities EmployeeNotFoundException {} -->", e.getMessage());
        }

    }

    public void reloadAuthorities(Principal principal, Model model) {
        log.info("<--  reloadAuthorities for principal: {} -->", principal.getName());
        try {
            String agent = principal.getName();
            log.info("<--  principal email: {} -->", agent);
            Employee e = employeeService.findEmployeeByUsername(agent);
            try {
                List<ROLE> authorities = findEmployeeAuthorities(e);
                // authorities = findAllAuthorities();
                if (authorities != null) {
                    model.addAttribute("authorities", authorities);
                    log.info("<--  reloadAuthorities authorities != null {} -->", authorities);
                } else throw new RolesNotFoundException("Failed to load Authorities");

            } catch (RolesNotFoundException rolesNotFoundException) {
                log.info("<--  reloadAuthorities rolesNotFoundException {} -->", rolesNotFoundException.getMessage());
                model.addAttribute("loadAuthoritiesResponse", rolesNotFoundException.getMessage());
            }
        } catch (EmployeeNotFoundException e) {
            model.addAttribute("loadAuthoritiesResponse", e.getMessage());
            log.info("<--  reloadAuthorities EmployeeNotFoundException {} -->", e.getMessage());
        }

    }

    public Employee findEmployeeByEmail(String email) {
        log.info(" findEmployeeByEmail  {}", email);
        Employee oe = employeeService.getEmployeeByEmail(email);
        if (oe != null) {
            log.info("FOUND EMPLOYEE {}", oe);
            return oe;
        } else throw new EmployeeNotFoundException("Employee not found");
    }

    public void employeeVerification(String token, Model model) {
        log.info("<-- employeeVerification  -->");
        try {
            String verificationResult = tokenVerificationService.verifyToken(token);
            log.info("<-- verificationResult {} -->", verificationResult);
            model.addAttribute("employeeVerificationResponse", verificationResult);
        } catch (TokenTimeOutException tokenTimeOutException) {
            log.info("<-- employeeVerification Token Time out exception -->{}", tokenTimeOutException.getMessage());
            model.addAttribute("employeeVerificationResponse", tokenTimeOutException.getMessage());
        }
    }

    public List<ROLE> findEmployeeAuthorities(Employee employee) {
        List<ROLE> returnList = new ArrayList<>();
        try {
            log.info("Searching for Employee: {}", employee.printEmployee());
            List<Authority> userAuths = employee.getAuthorities();
            log.info("Employee Authority list size: \n {}", userAuths.size());
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


    // VACANCY

    public void addVacancy(Model model) {
        findAllClients(model);
        findAllEmployees(model);
        model.addAttribute("vacancyDTO", new VacancyDTO());
    }

    public void reloadCreateVacancy(Model model) {
        findAllClients(model);
        findAllEmployees(model);
    }

    public void updateVacancy(VacancyDTO vacancy, Model model) {
        log.info("<--  updateVacancy vacancy {} -->", vacancy.printVacancy());
        // Retrieve the existing Vacancy from the database
        Vacancy existingVacancy = vacancyService.findById(vacancy.getVacancyID());
        log.info(" updateVacancy \n {}", existingVacancy.printVacancy());
        // Check if the existingVacancy is not null (it exists in the database)
        try {
            if (existingVacancy != null) {
                log.info("<--  existingVacancy != null {} -->", existingVacancy.printVacancy());
                // Compare and update fields
                if (existingVacancy.getJobTitle() != null && !existingVacancy.getJobTitle().equals(vacancy.getJobTitle())) {
                    existingVacancy.setJobTitle(vacancy.getJobTitle());
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
                Vacancy updatedVacancy = vacancyService.save(existingVacancy);
                VacancyDTO vacancyDTO = convertVacancy(updatedVacancy);
                model.addAttribute("vacancy", vacancyDTO);
                model.addAttribute("employeeName", updatedVacancy.getEmployee().getFirst_name());
                log.info("<-- updateVacancy existingVacancy Saved -->");
                model.addAttribute("saveVacancyResponse", "Vacancy : " + existingVacancy.getJobTitle() + " Updated");
            } else throw new VacancyException("No Existing Vacancy Found");
        } catch (VacancyException vacancyException) {
            log.info("<--  updateVacancy vacancyException {} -->", vacancyException.getMessage());
            model.addAttribute("updateVacancyResponse", vacancyException.getMessage());
        }
    }

    public void saveNewVacancy(VacancyDTO vacancy, Model model) {
        try {
            // create vacancy
            log.info("<-- saveNewVacancy Creating vacancy VacancyDTO {} -->", vacancy.printVacancy());
            Vacancy newVacancy = new Vacancy();

            newVacancy.setJobTitle(vacancy.getJobTitle());
            newVacancy.setJob_description(vacancy.getJob_description());
            newVacancy.setSeniority_level(vacancy.getSeniority_level());
            newVacancy.setRequirements(vacancy.getRequirements());
            newVacancy.setLocation(vacancy.getLocation());

            newVacancy.setPublish_date(vacancy.getPublish_date());
            newVacancy.setEnd_date(vacancy.getEnd_date());
            newVacancy.setJobType(vacancy.getJobType());
            newVacancy.setEmpType(vacancy.getEmpType());

            Client client = clientService.findClientByID(vacancy.getClientID());
            if (client != null) {
                //newVacancy.setClient(client);
                client.addVacancy(newVacancy);
                clientService.saveExistingClient(client);
            } else throw new VacancyException("Failed to set client to vacancy");

            Employee op = employeeService.getEmployeeByid(vacancy.getEmployeeID());
            if (op != null) {
                // newVacancy.setEmployee(op);
                op.addVacancy(newVacancy);
                employeeService.save(op);
            } else throw new VacancyException("Failed to set employee to vacancy");

            newVacancy.setIndustry(client.getIndustry());
            newVacancy.setCreated(LocalDateTime.now());

            LocalDate today = LocalDate.now();
            if (vacancy.getPublish_date().isAfter(today)) {
                newVacancy.setStatus(VacancyStatus.PENDING);
                log.info("<-- saveNewVacancy Vacancy {} status set to {} -->", newVacancy.getJobTitle(), VacancyStatus.PENDING);

                // Add Batch job to activate vacancies at midnight daily, batch must also deactivate expired vacancies
                // Add Batch job to activate blogs at midnight daily, batch must also deactivate expired blogs

            } else {
                newVacancy.setStatus(VacancyStatus.ACTIVE);
                log.info("<-- saveNewVacancy Vacancy {} status set to {} -->", newVacancy.getJobTitle(), VacancyStatus.ACTIVE);
            }

            Vacancy saved = vacancyService.save(newVacancy);
            // findVacancy(newVacancy.getVacancyID(), model);
            VacancyDTO vacancyDTO = convertVacancy(saved);
            model.addAttribute("vacancy", vacancyDTO);
            model.addAttribute("employeeName", saved.getEmployee().getFirst_name());
            model.addAttribute("saveVacancyResponse", newVacancy.getJobTitle() + " vacancy saved");
        } catch (VacancyException vacancyException) {
            log.info("<-- saveVacancy  vacancyException: {} -->", vacancyException.getMessage());
            model.addAttribute("saveVacancyResponse", vacancyException.getMessage());
        }
    }

    public void findVacancy(Long vacancyID, Model model) {
        log.info("<--  findVacancy vacancyID  {} -->", vacancyID);
        try {
            Vacancy vacancy = vacancyService.findById(vacancyID);

            VacancyDTO vacancyDTO = convertVacancy(vacancy);

            // clientName , vacancyName = jobTitle
            model.addAttribute("vacancy", vacancyDTO);
            model.addAttribute("employeeName", vacancy.getEmployee().getFirst_name());
            model.addAttribute("clientFileDTO", new ClientFileDTO());

            log.info("<--  findVacancy vacancy != null  {} -->", vacancy.printVacancy());
        } catch (VacancyException vacancyException) {

            log.info("<-- findVacancy  vacancyException: {} -->", vacancyException.getMessage());
            model.addAttribute("findVacancyResponse", vacancyException.getMessage());
        }
    }

    public VacancyDTO convertVacancy(Vacancy vacancy) {
        VacancyDTO vacancyDTO = new VacancyDTO();
        vacancyDTO.setJobTitle(vacancy.getJobTitle());
        vacancyDTO.setJob_description(vacancy.getJob_description());
        vacancyDTO.setSeniority_level(vacancy.getSeniority_level());
        vacancyDTO.setRequirements(vacancy.getRequirements());
        vacancyDTO.setLocation(vacancy.getLocation());
        vacancyDTO.setIndustry(vacancy.getIndustry());
        vacancyDTO.setPublish_date(vacancy.getPublish_date());
        vacancyDTO.setEnd_date(vacancy.getEnd_date());
        vacancyDTO.setStatus(vacancy.getStatus());
        vacancyDTO.setJobType(vacancy.getJobType());
        vacancyDTO.setEmpType(vacancy.getEmpType());
        vacancyDTO.setClientID(vacancy.getTheClientID());
        vacancyDTO.setVacancyID(vacancy.getVacancyID());
        vacancyDTO.setEmployeeID(vacancy.getTheEmpID());
        vacancyDTO.setApplicationCount(vacancy.getApplicationsSize());
        vacancyDTO.setClientName(vacancy.getClient().getName());
        return vacancyDTO;
    }

    public void findVacancySubmissions(Long vacancyID, Model model, int pageNo, int pageSize, String sortField, String sortDirection
    ) {
        log.info("<--  findVacancySubmissions vacancyID  {} -->", vacancyID);
        try {
            Vacancy vacancy = vacancyService.findById(vacancyID);
            VacancyDTO vacancyDTO = convertVacancy(vacancy);
            model.addAttribute("vacancy", vacancyDTO);
//            List<Application> vacancyApplicationList = vacancy.getApplications();

            Page<Application> vacancyApplicationList = applicationService.findPaginatedApplications(pageNo, pageSize, sortField,
                    sortDirection, vacancy);
            log.info("<--  findVacancySubmissions vacancyApplicationList.getTotalPages  {} -->", vacancyApplicationList.getTotalPages());
            log.info("<--  findVacancySubmissions vacancyApplicationList.getTotalElements  {} -->", vacancyApplicationList.getTotalElements());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", vacancyApplicationList.getTotalPages());
            model.addAttribute("totalItems", vacancyApplicationList.getTotalElements());
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDirection);
            model.addAttribute("vacancyID", vacancyID);

            model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

            log.info("<--  findVacancySubmissions Application Size {} -->", vacancyApplicationList.getTotalPages());
            model.addAttribute("vacancyApplications", vacancyApplicationList);

            log.info("<--  findVacancyById vacancy != null  {} -->", vacancy.printVacancy());
        } catch (VacancyException vacancyException) {
            log.info("<-- findVacancyById  vacancyException: {} -->", vacancyException.getMessage());
            model.addAttribute("findVacancyResponse", vacancyException.getMessage());
        }
    }

    public void getAllVacancies(Model model, int pageNo, int pageSize, String sortField, String sortDirection) {
        log.info("<--  getAllVacancies -->");
        try {
            Page<Vacancy> vacancyApplicationList = vacancyService.findPaginatedVacancies(pageNo, pageSize, sortField,
                    sortDirection);
            log.info("<--  getAllVacancies vacancyApplicationList.getTotalElements {} -->", vacancyApplicationList.getTotalElements());
            log.info("<--  getAllVacancies vacancyApplicationList.getTotalPages {} -->", vacancyApplicationList.getTotalPages());

            model.addAttribute("vacancyList", vacancyApplicationList);

            model.addAttribute("totalPages", vacancyApplicationList.getTotalPages());
            model.addAttribute("totalItems", vacancyApplicationList.getTotalElements());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDirection);
            model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        } catch (VacancyException vacancyException) {
            log.info("<-- findVacancyById  vacancyException: {} -->", vacancyException.getMessage());
            model.addAttribute("findVacancyResponse", vacancyException.getMessage());
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
            log.info("<-- searchVacancyByTitle  vacancyException: {} -->", vacancyException.getMessage());
            model.addAttribute("searchVacancyByTitleResponse", vacancyException.getMessage());
        }
    }

    public void getAllVacancies(Model model) {
        log.info("<--  getAllVacancies -->");
        try {
            List<Vacancy> responseList = vacancyService.getAllVacancies();
            log.info("<-- getAllVacancies  responseListSize: {} -->", responseList.size());
            if (responseList != null) {
                log.info("<--  getAllVacancies responseList size-->", responseList.size());
                model.addAttribute("vacancyList", responseList);
            } else throw new VacancyException("No Vacancies found");

        } catch (VacancyException vacancyException) {
            log.info("<-- getAllVacancies  vacancyException: {} -->", vacancyException.getMessage());
            model.addAttribute("loadVacanciesResponse", vacancyException.getMessage());
        }
    }

/*    public List<Vacancy>  getAllVacancies() {
        log.info("<--  getAllVacancies -->");
        List<Vacancy> responseList = new ArrayList<>();
        try {
            responseList = vacancyService.getAllVacancies();
            log.info("<-- getAllVacancies  responseListSize: {} -->", responseList.size());

        } catch (VacancyException vacancyException) {
            log.info("<-- getAllVacancies  vacancyException: {} -->", vacancyException.getFailureReason());
        }
        return responseList;
    }*/

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
            log.info("<--  getActiveVacancies VacancyException {}-->", vacancyException.getMessage());
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

    public void saveNewVacancyStatus(VacancyStatusDTO vacancyStatusDTO, Model model) {
        log.info("<-- saveNewVacancyStatus vacancyStatusDTO {} -->", vacancyStatusDTO.printVacancyStatusDTO());
        try {
            Vacancy vacancy = vacancyService.findById(vacancyStatusDTO.getVacancyID());
            vacancy.setStatus(vacancyStatusDTO.getStatus());
            vacancyService.save(vacancy);
            String response = "Vacancy Status updated";
            model.addAttribute("saveNewVacancyStatusResponse", response);
        } catch (VacancyException vacancyException) {
            log.info("<-- saveNewVacancyStatus vacancyException {} -->", vacancyException.getMessage());
            model.addAttribute("saveNewVacancyStatusResponse", vacancyException.getMessage());
        }
    }

    public void findVacancyStatus(Long vacancyID, Model model) {
        log.info("<-- findVacancyStatus vacancyID {} -->", vacancyID);
        try {
            Vacancy vacancy = vacancyService.findById(vacancyID);

            VacancyStatusDTO status = new VacancyStatusDTO();
            status.setVacancyID(vacancy.getVacancyID());
            status.setStatus(vacancy.getStatus());
            status.setJob_title(vacancy.getJobTitle());
            status.setPublish_date(vacancy.getPublish_date().toString());
            status.setEnd_date(vacancy.getEnd_date().toString());
            status.setCreated(vacancy.getCreated().toString());
            status.setClientName(vacancy.getClient().getName());
            status.setEmployeeName(vacancy.getEmployee().getFirst_name());
            status.setTotalApplicationCount(vacancy.getApplicationsSize());
            model.addAttribute("vacancyStatusDTO", status);

        } catch (VacancyException vacancyException) {
            log.info("<-- findVacancyStatus vacancyException {} -->", vacancyException.getMessage());
            model.addAttribute("findVacancyStatusResponse", vacancyException.getMessage());
        }
    }

    // BLOG

    public void getActiveBlogs(Model model) {
        log.info("<--  getActiveBlogs -->");
        try {
            List<Blog> blogResponse = blogService.getActiveBlogs(ACTIVE);
            if (!blogResponse.isEmpty()) {
                log.info("<--  getActiveBlogs blogResponse {} -->", blogResponse.size());
                model.addAttribute("blogs", blogResponse);
            } else throw new BlogNotFoundException("No Active Blogs");
        } catch (BlogNotFoundException blogNotFoundException) {
            model.addAttribute("activeBlogResponse", blogNotFoundException.getMessage());
            log.info("<--  getActiveBlogs blogNotFoundException {} -->", blogNotFoundException.getMessage());
        }
    }

/*    public void getBlogs(Model model) {
        log.info("<--  getBlogs -->");
        try {
            List<Blog> responseList = blogService.getBlogs();
            if (responseList != null) {
                log.info("<--  getBlogs {} -->", responseList.size());
                model.addAttribute("blogList", responseList);
            } else throw new BlogNotFoundException("No Blogs");
        } catch (BlogNotFoundException blogNotFoundException) {
            model.addAttribute("getBlogsResponse", blogNotFoundException.getMessage());
            log.info("<--  getBlogs  blogNotFoundException {} -->", blogNotFoundException.getMessage());
        }
    }*/

    public void getBlogs(Model model, int pageNo, int pageSize, String sortField, String sortDirection) {
        log.info("<--  getBlogs -->");
        try {
            //List<Blog> responseList = blogService.getBlogs();
            Page<Blog> responseList = blogService.findPaginatedBlogs(pageNo, pageSize, sortField,
                    sortDirection);
            if (responseList != null) {
                log.info("<--  getBlogs getTotalPages {} -->", responseList.getTotalPages());
                log.info("<--  getBlogs getTotalElements {} -->", responseList.getTotalElements());
                model.addAttribute("blogList", responseList);
                model.addAttribute("totalPages", responseList.getTotalPages());
                model.addAttribute("totalItems", responseList.getTotalElements());
                model.addAttribute("currentPage", pageNo);
                model.addAttribute("sortField", sortField);
                model.addAttribute("sortDir", sortDirection);
                model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

            } else throw new BlogNotFoundException("No Blogs");
        } catch (BlogNotFoundException blogNotFoundException) {
            model.addAttribute("getBlogsResponse", blogNotFoundException.getMessage());
            log.info("<--  getBlogs  blogNotFoundException {} -->", blogNotFoundException.getMessage());
        }
    }

    public void findBlogById(Long blogID, Model model) {
        log.info("<--  findBlogById blogID {} -->", blogID);
        try {
            Blog blog = blogService.findById(blogID);
            model.addAttribute("blog", blog);
            log.info("<--  findBlogById blog {} -->", blog.printBlog());
        } catch (BlogNotFoundException blogNotFoundException) {
            log.info("<--  findBlogById blog is null  -->");
            model.addAttribute("findBlogResponse", blogNotFoundException.getMessage());
            log.info("<--  findBlogById blogNotSavedException {} -->", blogNotFoundException.getMessage());
        }
    }


    public void saveNewBlog(BlogDTO blogDTO, Principal principal, Model model) {
        log.info("<-- saveNewBlog Saving new blog for principal : {} -->", principal.getName());
        try {
            Blog newBlog = new Blog();
            Employee employee = findEmployeeByEmail(principal.getName());
            if (employee != null) {
                log.info("<--  saveNewBlog employee!=null {} -->", employee.printEmployee());
                newBlog.setEmployee(employee);
                employee.addBlog(newBlog);
                newBlog.setBlog_title(blogDTO.getBlog_title());
                newBlog.setBlog_description(blogDTO.getBlog_description());
                newBlog.setBody(blogDTO.getBody());
                newBlog.setEnd_date(blogDTO.getEnd_date());
                newBlog.setPublish_date(blogDTO.getPublish_date());
                newBlog.setCreated(LocalDateTime.now());
                LocalDate today = LocalDate.now();
                if (blogDTO.getPublish_date().isAfter(today)) {
                    newBlog.setStatus(PENDING);
                } else {
                    newBlog.setStatus(ACTIVE);
                }
                Blog blog = blogService.save(newBlog);
                employeeService.save(employee);
                model.addAttribute("saveNewBlogResponse", "New Blog created " + blog.getBlogID());
                model.addAttribute("blog", blog);
                log.info("<--  saveNewBlog newBlog {} -->", newBlog.printBlog());
            } else {
                log.info("<--  saveNewBlog employee is null -->");
                model.addAttribute("saveNewBlogResponse", "Employee Not Set");
            }
        } catch (EmployeeNotFoundException e) {
            log.info("<--  saveNewBlog EmployeeNotFoundException {} -->", e.getMessage());
            model.addAttribute("saveNewBlogResponse", e.getMessage());
        }
    }


    public void saveUpdatedBlog(Blog updatedBlog, Model model) {
        log.info("<--  saveExistingBlog blog {} -->", updatedBlog.printBlog());
        String returnMessage = "";
        try {
            Blog optionalBlog = blogService.findById(updatedBlog.getBlogID());
            log.info("<--  optionalBlog {} -->", optionalBlog.printBlog());
            if (optionalBlog != null) {

                log.info("<--  saveExistingBlog optionalBlog.isPresent {} -->", optionalBlog.printBlog());
                if (!updatedBlog.getBlog_title().equalsIgnoreCase(optionalBlog.getBlog_title())) {
                    optionalBlog.setBlog_title(updatedBlog.getBlog_title());
                }
                // description
                if (!updatedBlog.getBlog_description().equalsIgnoreCase(optionalBlog.getBlog_description())) {
                    optionalBlog.setBlog_description(updatedBlog.getBlog_description());
                }
                // body
                if (!updatedBlog.getBody().equalsIgnoreCase(optionalBlog.getBody())) {
                    optionalBlog.setBody(updatedBlog.getBody());
                }
                // publish date

                if (!Objects.equals(updatedBlog.getPublish_date(), optionalBlog.getPublish_date())) {
                    optionalBlog.setPublish_date(updatedBlog.getPublish_date());

                }
                // expiration date
                if (!Objects.equals(updatedBlog.getEnd_date(), optionalBlog.getEnd_date())) {
                    optionalBlog.setEnd_date(updatedBlog.getEnd_date());
                }

                if (updatedBlog.getPublish_date() != null) {
                    if (!updatedBlog.getPublish_date().isBefore(LocalDate.now())) {
                        optionalBlog.setStatus(ACTIVE);
                    }
                }

                try {
                    //optionalBlog.setEmployee(updatedBlog.getEmployee());
                    Blog savedBlog = blogService.save(optionalBlog);
                    if (savedBlog != null) {
                        returnMessage = "Blog Updated";

                        model.addAttribute("savedBlogResponse", returnMessage);
                        log.info(" < --- savedBlog {} -->", savedBlog.printBlog());
                        model.addAttribute("blog", savedBlog);
                        log.info("<--  saveExistingBlog returnMessage {} -->", returnMessage);
                    } else throw new BlogNotSavedException("Failed to save blog");

                } catch (BlogNotSavedException blogNotSavedException) {
                    log.info("<--  saveExistingBlog blogNotSavedException {} -->", blogNotSavedException.getMessage());
                    model.addAttribute("updateBlogResponse", blogNotSavedException.getMessage());
                }
            } else throw new BlogNotFoundException("Blog not found: blog ID: " + updatedBlog.getBlogID());
        } catch (BlogNotFoundException blogNotFoundException) {
            log.error("blogNotSavedException {}", blogNotFoundException.getMessage());
            model.addAttribute("updateBlogResponse", blogNotFoundException.getMessage());
        }
    }

    public void saveBlogImage(BlogImageDTO blogImageDTO, Model model) {
        log.info("<--  saveBlogImage blogImageDTO {} -->", blogImageDTO.printBlogImageDTO());
        String returnMessage = "";

        try {
            Blog optionalBlog = blogService.findById(blogImageDTO.getBlogID());
            log.info("<--  saveBlogImage existingBlog {} -->", optionalBlog.printBlog());
            if (optionalBlog != null) {

                if (blogImageDTO.getBlogImage() != null && !blogImageDTO.getBlogImage().isEmpty()) {

                    switch (blogImageDTO.getBlogImageType()) {
                        case HEADER -> {
                            log.info("<-- saveBlogImage to data volume blogHeaderImage submitted for {}", optionalBlog.getBlog_title());
//                            String blogHeaderPath = uploadBlogImage(blogImageDTO.getBlogImage(), optionalBlog.getBlogID(), blogImageDTO.getBlogImageType().toString());
                            String blogHeaderPath = uploadBlogImage(blogImageDTO.getBlogImage());
                            log.info("<-- saveBlogImage blogHeaderImage blogHeaderPath {}", blogHeaderPath);
                            optionalBlog.setHeadImagePath(blogHeaderPath);

                        }
                        case CONTENT_IMAGE_ONE -> {
                            log.info("<-- saveBlogImage to data volume CONTENT_IMAGE_ONE submitted for {}", optionalBlog.getBlog_title());
//                            String blogContentOnePath = uploadBlogImage(blogImageDTO.getBlogImage(), optionalBlog.getBlogID(), blogImageDTO.getBlogImageType().toString());
                            String blogContentOnePath = uploadBlogImage(blogImageDTO.getBlogImage());
                            log.info("<-- saveBlogImage blogHeaderImage blogContentOnePath {}", blogContentOnePath);
                            optionalBlog.setContentImageOnePath(blogContentOnePath);

                        }
                    }

                    try {

                        Blog savedBlog = blogService.save(optionalBlog);
                        if (savedBlog != null) {
                            returnMessage = "Blog Image Added";

                            model.addAttribute("addImageResponse", returnMessage);
                            log.info(" < --- saveBlogImage {} -->", savedBlog.printBlog());
                            model.addAttribute("blog", savedBlog);
                            log.info("<--  saveBlogImage returnMessage {} -->", returnMessage);
                        } else throw new BlogNotSavedException("Failed to save blog image");

                    } catch (BlogNotSavedException blogNotSavedException) {
                        log.info("<--  saveExistingBlog blogNotSavedException {} -->", blogNotSavedException.getMessage());
                        model.addAttribute("addImageResponse", blogNotSavedException.getMessage());
                    }

                } else {
                    log.info("<-- NO IMAGE SUBMITTED ");
                    returnMessage = "No images added";
                    model.addAttribute("addImageResponse", returnMessage);
                }

            } else throw new BlogNotFoundException("Blog not found: blog ID: " + blogImageDTO.getBlogID());
        } catch (BlogNotFoundException blogNotFoundException) {
            log.error("blogNotSavedException {}", blogNotFoundException.getMessage());
            model.addAttribute("addImageResponse", blogNotFoundException.getMessage());
        }
    }

    private String uploadBlogImage(MultipartFile file) {
        log.info("<-- saveBlogImage -->");
        try {
            //Path currentRelativePath = Paths.get("");
            //String absolutePath = currentRelativePath.toAbsolutePath().toString();

          /*  String uploadDir = absolutePath + BLOG_LOCAL_FULL_PATH;


            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                log.info("<-- saveBlogImage image saved -->");
            }*/

            // volume storage
            //    /RecruitmentZoneApplication/BlogImages/{blogID}/{imageType}/

            //String volumeDirectory = "/home/justin/RecruitmentZoneApplication/" + "BlogImages/";
            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get(BLOG_VOLUME_FULL_PATH);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                log.info("<-- saveBlogImage image saved to data volume -->");
            }

            return fileName;
        } catch (IOException e) {
            log.info("<-- saveBlogImage \n", e);
            return null;
        }
    }

    public void findBlogStatus(Long blogID, Model model) {
        log.info("<-- findBlogStatus blogID {} -->", blogID);
        Blog blog = blogService.findById(blogID);
        BlogStatusDTO status = new BlogStatusDTO();
        log.info("<-- findBlogStatus new status {} -->", status.printBlogStatusDTO());
        status.setBlogID(blogID);
        log.info("<-- findBlogStatus new blogID {} -->", blogID);
        status.setStatus(blog.getStatus());
        model.addAttribute("blogStatusDTO", status);
    }

    public void saveNewBlogStatus(BlogStatusDTO blogStatusDTO, Model model) {
        log.info("<--  saveNewBlogStatus blogStatusDTO {} -->", blogStatusDTO.printBlogStatusDTO());
        try {
            Blog blog = blogService.findById(blogStatusDTO.getBlogID());
            log.info("<--  saveNewBlogStatus blog {} -->", blog.printBlog());
            blog.setStatus(blogStatusDTO.getStatus());
            blogService.save(blog);
            String response = "Blog Status updated";
            model.addAttribute("saveNewBlogStatusResponse", response);
        } catch (BlogNotFoundException blogNotFoundException) {
            log.info("<--  saveNewBlogStatus blogNotFoundException {} -->", blogNotFoundException.getMessage());
            model.addAttribute("saveNewBlogStatusResponse", blogNotFoundException.getMessage());
        }


    }

    // DOCUMENTS

    public boolean validateFile(MultipartFile file) throws FileUploadException {
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
        log.info("<--  Valid File Received -->");
        return true;
    }

    public CandidateFile createCandidateFile(CandidateFileDTO fileDTO) {

     /*

       SIM DELAY

       try {
            // Sleep for 5000 milliseconds (5 seconds)
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted.");
        }*/


        log.info("<--  createCandidateFile  {} -->", fileDTO.printCandidateFileDTO());
        CandidateFile file = new CandidateFile();

        Candidate candidate = candidateService.getcandidateByID(fileDTO.getCandidateID());
        log.info("<--  createCandidateFile candidate.isPresent( {} -->", candidate.printCandidate());
        file.setCandidate(candidate);

        file.setContent_type(fileDTO.getDocumentAttachment().getContentType());
// save bytes to database
        file.setFile_data(null);

        file.setFile_name(fileDTO.getDocumentAttachment().getOriginalFilename());
        file.setFile_size(Long.toString(fileDTO.getDocumentAttachment().getSize()));
        file.setCandidateDocumentType(fileDTO.getDocumentType());
        file.setCreated(LocalDateTime.now());
        log.info("New CandidateFile {}", file.printDocument());
        // Local file storage

        String docType = fileDTO.getDocumentType().toString();
        String candidateIDNumber = candidate.getId_number();
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

            String fileName = fileDTO.getDocumentAttachment().getOriginalFilename();
            storageLocation = uploadPath.resolve(fileName);
            Files.copy(fileDTO.getDocumentAttachment().getInputStream(), storageLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("File saved storageLocation: {}", storageLocation);
        } catch (Exception e) {
            log.info("<--  createCandidateFile Exception {} -->", e.getMessage());
        }

        file.setDocumentLocation(storageLocation.toString());

        candidate.AddDocument(file);
        candidateService.save(candidate);
        file = candidateFileService.saveCandidateFile(file);


        if (file.getFileID() != null) {
            // Google cloud storage
            publishCandidateGoogleFileEvent(file.getFileID(), file);
            return file;
        } else throw new SaveFileException("Failed to create file ");


    }

    public ClientFile createClientFile(ClientFileDTO fileDTO) {
        log.info("<--  createClientFile  {} -->", fileDTO.printClientFileDTO());
        ClientFile file = new ClientFile();
        Client client = clientService.findClientByID(fileDTO.getClientID());
        Vacancy vacancy = vacancyService.findById(fileDTO.getVacancyID());
        log.info("<--  createClientFile client.isPresent( {} -->", client.printClient());
        file.setClient(client);

        file.setContent_type(fileDTO.getFileMultipart().getContentType());

        file.setFile_data(null);
        file.setVacancy(vacancy);
        file.setFile_name(fileDTO.getFileMultipart().getOriginalFilename());
        file.setFile_size(Long.toString(fileDTO.getFileMultipart().getSize()));
        file.setClientDocumentType(fileDTO.getDocumentType());
        file.setCreated(LocalDateTime.now());
        log.info("New clientFile {}", file.printDocument());
        vacancyService.save(vacancy);
        // Local file storage

        String docType = fileDTO.getDocumentType().toString();

        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));

        log.info("fileDirectory/Folder: {} {}", fileDirectory, "CLIENT_FILES");
        String directory = fileDirectory + "/CLIENT_FILES/" + client.getName() + "/" + docType + "/" + formattedDate;


        // Local storage

        Path storageLocation = null;
        try {
            Path uploadPath = Path.of(directory);
            if (Files.notExists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = fileDTO.getFileMultipart().getOriginalFilename();
            storageLocation = uploadPath.resolve(fileName);
            Files.copy(fileDTO.getFileMultipart().getInputStream(), storageLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("File saved storageLocation: {}", storageLocation);
        } catch (Exception e) {
            log.info("<--  createClientFile Exception {} -->", e.getMessage());
        }

        file.setDocumentLocation(storageLocation.toString());


        client.AddDocument(file);
        clientService.saveExistingClient(client);
        ClientFile savedClientFile = clientFileService.saveClientFile(file);

        if (savedClientFile != null) {
            // Google cloud storage

            publishClientGoogleFileEvent(file.getFileID(), client.getName(), file);


            return file;

        } else throw new SaveFileException("Failed to create file ");

    }

    public String getDocumentLocation(String term) {
        log.info("<--  getDocumentLocation {} -->", term);
        try {
            String filesResponse = candidateFileService.getDocumentLocation(term);
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

/*    public String getDocumentLocation(String term, Model model) {
        log.info("<--  getDocumentLocation {} -->", term);
        try {
            String filesResponse = candidateFileService.getDocumentLocation(term);
            if (filesResponse.isEmpty()) {
                log.info("<--  getDocumentLocation filesResponse {} -->", filesResponse);
                return filesResponse;
            } else throw new FileContentException("Error trying to find term" + term);
        } catch (FileContentException e) {
            model.addAttribute("searchFileContentResponse", e.getMessage());
            log.info("<--  getDocumentLocation FileContentException {} -->", e.getMessage());
        }
        return null;
    }*/

    public void getFilesFromContent(SearchDocumentsDTO searchDocumentsDTO, Model model) {
        log.info("<--  getFilesFromContent -->");
        try {
            List<String> filesResponse = candidateFileService.searchFileContent(searchDocumentsDTO.getTerm());
            if (!filesResponse.isEmpty()) {
                log.info("<--  getFilesFromContent filesResponse size {} -->", filesResponse.size());
                model.addAttribute("resultCount", filesResponse.size());
                model.addAttribute("resultList", filesResponse);
                model.addAttribute("term", searchDocumentsDTO.getTerm());
            } else throw new FileContentException("Error trying to find term" + searchDocumentsDTO.getTerm());
        } catch (FileContentException e) {
            model.addAttribute("searchFileContentResponse", e.getMessage());
            log.info("<--  getFilesFromContent FileContentException {} -->", e.getMessage());
        }

    }

    public void searchFiles(Model model, SearchDocumentsDTO searchDocumentsDTO) {
        log.info("<--  searchFiles searchDocumentsDTO.term {} -->", searchDocumentsDTO.getTerm());
        try {
            List<String> returnList = candidateFileService.searchFiles(searchDocumentsDTO.getTerm());
            if (!returnList.isEmpty()) {
                model.addAttribute("resultCount", returnList.size());
                model.addAttribute("resultList", returnList);
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

    // STORAGE


    public boolean publishCandidateGoogleFileEvent(Long fileID, Document document) {
        log.info("<-- publishCandidateFileUploadedEvent {} -->", document.printDocument());
        return candidateEventPublisher.publishCandidateGoogleFileEvent(fileID, document);
    }

    public boolean publishClientGoogleFileEvent(Long fileID, String clientName, Document document) {
        log.info("<-- publishClientFileUploadedEvent {} -->", document.printDocument());
        return clientEventPublisher.publishClientGoogleFileEvent(fileID, clientName, document);
    }

    // CLIENTS & CONTACT PERSONS

    public void findAllClients(Model model) {
        log.info("<--  findAllClients -->");
        try {
            List<Client> clientList = clientService.getAllClients();
            if (clientList != null) {
                model.addAttribute("clientList", clientList);
                log.info("<--  findAllClients clientList {} -->", clientList.size());
            } else throw new ClientNotFoundException("No clients found. contact system administrator");
        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findAllClientsResponse", clientNotFoundException.getMessage());
            log.info("<--  findAllClients clientNotFoundException {} -->", clientNotFoundException.getMessage());
        }
    }

    public void findAllClients(Model model, int pageNo, int pageSize, String sortField, String sortDirection) {
        log.info("<--  findAllClients -->");
        try {
            // List<Client> clientList = clientService.getAllClients();
            Page<Client> clientList = clientService.findPaginatedClients(pageNo, pageSize, sortField,
                    sortDirection);

            if (clientList != null) {
                log.info("<--  findAllClients clientList.getTotalPages() = {} -->", clientList.getTotalPages());
                log.info("<--  findAllClients clientList.getTotalElements() = {} -->", clientList.getTotalElements());
                model.addAttribute("clientList", clientList);
                model.addAttribute("totalPages", clientList.getTotalPages());
                model.addAttribute("totalItems", clientList.getTotalElements());
                model.addAttribute("currentPage", pageNo);
                model.addAttribute("sortField", sortField);
                model.addAttribute("sortDir", sortDirection);
                model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

            } else throw new ClientNotFoundException("No clients found. contact system administrator");
        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findAllClientsResponse", clientNotFoundException.getMessage());
            log.info("<--  findAllClients clientNotFoundException {} -->", clientNotFoundException.getMessage());
        }
    }


    public void saveNewClient(Model model, NewClientDTO newClientDTO) {
        log.info("<--  saveNewClient clientDTO {} -->", newClientDTO.printClientDTO());
        try {
            Client clientResponse = clientService.saveNewClient(newClientDTO);
            if (clientResponse != null) {
                log.info("<--  saveNewClient clientResponse {} -->", clientResponse.printClient());
                String saveNewClientResponse = "Client Saved: " + clientResponse.getClientID();
                model.addAttribute("saveNewClientResponse", saveNewClientResponse);
                model.addAttribute("client", clientResponse);

                int pageSize = 10;
                findClientNotes(model, clientResponse.getClientID(), 1, pageSize, "dateCaptured", "desc");


            } else throw new SaveClientException("Failed to save new client {}" + newClientDTO.getName());

        } catch (SaveClientException saveClientException) {
            model.addAttribute("saveNewClientResponse", saveClientException.getMessage());
            log.info("<--  saveNewClient saveClientException {} -->", saveClientException.getMessage());
        }
    }

    public void findClientByID(Long clientID, Model model) {
        log.info("<--  findClientByID clientID {} -->", clientID);
        try {
            Client client = clientService.findClientByID(clientID);
            if (client != null) {
                log.info("<--  findClientByID client {} -->", client.printClient());
                // ExistingClientDTO
                ExistingClientDTO existingClientDTO = new ExistingClientDTO();
                existingClientDTO.setIndustry(client.getIndustry());
                existingClientDTO.setName(client.getName());
                existingClientDTO.setClientID(clientID);
                existingClientDTO.setCreated(client.getCreated());
                existingClientDTO.setContactPeopleCount(client.getContactPeopleCount());
                model.addAttribute("client", client); // SHOULD THIS NOT BE A existingClientDTO

            } else throw new ClientNotFoundException("Client Not Found");

        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findClientByIDResponse", clientNotFoundException.getMessage());
            log.info("<--  findClientByID clientNotFoundException {} -->", clientNotFoundException.getMessage());
        }
    }

    public void findClientContacts(long clientID, Model model) {
        log.info("<--  findClientContacts clientID {} -->", clientID);
        try {
            Client client = clientService.findClientByID(clientID);
            List<ContactPerson> contactPersonList = client.getContactPeople();
            if (contactPersonList != null) {
                log.info("<-- findClientContacts contactPersonList.size() = {} -->", contactPersonList.size());
                model.addAttribute("clientID", client.getClientID());
                model.addAttribute("contactPersonList", contactPersonList);
                log.info("<--  findClientContacts contactPersonList {} -->", contactPersonList.size());
            } else throw new ContactNotFoundException("No contacts found" + client.getClientID());

        } catch (ContactNotFoundException contactNotFoundException) {
            model.addAttribute("findClientContactsResponse", contactNotFoundException.getMessage());
            log.info("<--  findClientContacts contactNotFoundException {} -->", contactNotFoundException.getMessage());
        }
    }

    public void loadClientVacancies(Long clientID, Model model) {
        log.info("<--  loadClientVacancies clientID {} -->", clientID);
        try {
            Client client = clientService.findClientByID(clientID);
            if (client != null) {
                log.info("<--  loadClientVacancies client {} -->", client.printClient());
                List<Vacancy> clientVacancyList = vacancyService.findByClient(client);
                model.addAttribute("client", client);

                model.addAttribute("clientVacancyList", clientVacancyList);

            } else throw new ClientNotFoundException("Client Not Found");

        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("loadClientVacanciesResponse", clientNotFoundException.getMessage());
            log.info("<--  loadClientVacancies clientNotFoundException {} -->", clientNotFoundException.getMessage());
            log.info("<--  loadClientVacancies clientNotFoundException -->\n", clientNotFoundException.getCause());
        }
    }

    public void saveNewClientNote(Model model, ClientNoteDTO clientNoteDTO, int pageNo, String sortField, String sortDirection) {
        log.info("<--  saveNewClientNote clientNoteDTO {} -->", clientNoteDTO.printClientNoteDTO());
        try {
            Client client = clientService.findClientByID(clientNoteDTO.getClientID());
            if (client != null) {
                log.info("<--  saveNewClientNote client {} -->", client.printClient());
                model.addAttribute("client", client);
                ClientNote clientNote = client.addNote(clientNoteDTO);
                clientService.saveClientNote(clientNote);
                clientService.saveExistingClient(client);
                model.addAttribute("saveNoteToClientResponse", "Note saved");

                //   model.addAttribute("noteSaved", Boolean.TRUE);
                //  List<ClientNote> notes = client.getNotes();
                findClientNotes(model, clientNoteDTO.getClientID(), pageNo, 10, sortField, sortDirection);

                // model.addAttribute("existingNotes", notes);
                ClientNoteDTO noteDTO = new ClientNoteDTO();
                noteDTO.setClientID(clientNoteDTO.getClientID());
                log.info("clientID: {}", clientNoteDTO.getClientID());
                model.addAttribute("clientNoteDTO", noteDTO);

            } else throw new ClientNotFoundException("Client Not Found");


        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("saveNewClientNoteResponse", clientNotFoundException.getMessage());
            log.info("<--  saveNewClientNote clientNotFoundException {} -->", clientNotFoundException.getMessage());
            log.info("<--  saveNewClientNote clientNotFoundException -->\n", clientNotFoundException.getCause());
        }
    }

/*    public void findClientNotes(Model model, Long clientID) {
        log.info("<--  findClientNotes clientID {} -->", clientID);
        try {
            Client clientResponse = clientService.findClientByID(clientID);
            if (clientResponse != null) {
                List<ClientNote> notes = clientResponse.getNotes();
                model.addAttribute("existingNotes", notes);
                model.addAttribute("client", clientResponse);
                if (notes.isEmpty() && notes != null) {
                    model.addAttribute("noNotesFound", Boolean.TRUE);
                }
                ClientNoteDTO clientNoteDTO = new ClientNoteDTO();
                clientNoteDTO.setClientID(clientResponse.getClientID());
                model.addAttribute("clientNoteDTO", clientNoteDTO);
                log.info("<--  findClientNotes clientResponse {} -->", clientResponse.printClient());
            } else throw new ClientNotFoundException("Client Note Found");

        } catch (ClientNotFoundException clientNotFoundException) {

            model.addAttribute("findClientByIDResponse", clientNotFoundException.getMessage());
            log.info("<--  findClientNotes clientNotFoundException {} -->", clientNotFoundException.getMessage());
        }
    }*/

    public void findClientNotes(Model model, long clientID, int pageNo, int pageSize, String sortField, String sortDirection) {
        log.info("<--  findClientNotes {} -->", clientID);
        try {
            Client clientResponse = clientService.findClientByID(clientID);
            if (clientResponse != null) {
                log.info("<--  clientResponse != null -->");
                // List<ClientNote> notes = clientResponse.getNotes();
                Page<ClientNote> notes = clientService.findPaginatedClientNotes(pageNo, pageSize, sortField,
                        sortDirection, clientResponse);

                model.addAttribute("existingNotes", notes);
                model.addAttribute("client", clientResponse);
                if (notes.isEmpty() && notes != null) {
                    model.addAttribute("noNotesFound", Boolean.TRUE);
                }

                ClientNoteDTO clientNoteDTO = new ClientNoteDTO();
                clientNoteDTO.setClientID(clientResponse.getClientID());
                model.addAttribute("clientNoteDTO", clientNoteDTO);


                if (notes != null) {
                    //model.addAttribute("clientList", notes);
                    model.addAttribute("totalPages", notes.getTotalPages());
                    model.addAttribute("totalItems", notes.getTotalElements());
                    model.addAttribute("currentPage", pageNo);
                    model.addAttribute("sortField", sortField);
                    model.addAttribute("sortDir", sortDirection);
                    model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

                } else throw new ClientNotFoundException("No clients found. contact system administrator");


            } else throw new ClientNotFoundException("Client Note Found");

        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findClientNotes", clientNotFoundException.getMessage());
            log.info("<--  findClientNotes clientNotFoundException {} -->", clientNotFoundException.getMessage());
        }
    }

    public void reloadClientNotes(Long clientID, Model model) {
        log.info("<--  reloadClientNotes clientID {} -->", clientID);
        try {
            Client clientResponse = clientService.findClientByID(clientID);
            if (clientResponse != null) {
                List<ClientNote> notes = clientResponse.getNotes();
                model.addAttribute("existingNotes", notes); // SHOULD THIS NOT BE A PAGINATED LIST OF NOTES OF THE CLIENT
                if (notes.isEmpty() && notes != null) {
                    model.addAttribute("noNotesFound", Boolean.TRUE);
                }
                log.info("<--  reloadClientNotes clientResponse {} -->", clientResponse.printClient());
            } else throw new ClientNotFoundException("Client Note Found");

        } catch (ClientNotFoundException clientNotFoundException) {
            model.addAttribute("findClientByIDResponse", clientNotFoundException.getMessage());
            log.info("<--  reloadClientNotes clientNotFoundException {} -->", clientNotFoundException.getMessage());
        }
    }

    public void saveUpdatedClient(ExistingClientDTO client, Model model) {
        log.info("<--  saveUpdatedClient {} -->", client.printExistingClientDTO());
        try {
            Client c = clientService.findClientByID(client.getClientID());
            if (c != null) {
                boolean valChange = false;
                if (!client.getName().equalsIgnoreCase(c.getName())) {
                    c.setName(client.getName());
                    valChange = true;
                }
                if (client.getIndustry() != c.getIndustry()) {
                    c.setIndustry(client.getIndustry());
                    valChange = true;
                }
                /*      clientService.saveUpdatedClient(c);
                 */
                if (valChange) {
                    log.info("<--  saveUpdatedClient  valChange {} -->", valChange);
                    Client updatedClient = clientService.saveExistingClient(c);
                    String updateClientResponse = "client updated " + updatedClient.getName();
                    model.addAttribute("client", updatedClient);
                    model.addAttribute("saveUpdatedClientResponse", updateClientResponse);
                    log.info("<--  saveUpdatedClient  updatedClient {} -->", updatedClient.printClient());
                } else {
                    log.info("<--  saveUpdatedClient  valChange {} -->", valChange);
                    String updateClientResponse = "client not updated";
                    model.addAttribute("client", c);
                    model.addAttribute("saveUpdatedClientResponse", updateClientResponse);
                }
            } else throw new SaveUpdatedClientException("Failed to save Updated client " + client.getClientID());
        } catch (SaveUpdatedClientException saveUpdatedClientException) {
            log.info("Exception {}", saveUpdatedClientException.getMessage());
            model.addAttribute("saveUpdatedClientResponse", saveUpdatedClientException.getMessage());
        }
    }

    public void addContactToClient(ContactPersonDTO contactPersonDTO, Model model) {
        log.info("<--  addContactToClient  contactPersonDTO {} -->", contactPersonDTO.printContactPersonDTO());
        try {
            Client c = clientService.addContactToClient(contactPersonDTO);
            if (c != null) {
                //
                String updatedContactResponse = "Contact Person added " + contactPersonDTO.getFirst_name();
                model.addAttribute("addContactPersonResponse", updatedContactResponse);
                log.info("<--  addContactToClient  contactPerson {} -->", contactPersonDTO.printContactPersonDTO());
                log.info("<--  addContactToClient  addContactPersonResponse {} -->", updatedContactResponse);
                // findContactPersonByClientID(contactPersonDTO.getContactPersonID(), model);
                findClientContacts(c.getClientID(), model);
            } else throw new AddContactPersonException("Failed to add contact person");
        } catch (AddContactPersonException addContactPersonException) {
            model.addAttribute("addContactPersonResponse", addContactPersonException.getMessage());
            log.info("<--  addContactToClient  addContactPersonException {} -->", addContactPersonException.getMessage());
        }
    }

    public void findContactPersonByID(Model model, Long contactPersonID) {
        log.info("<--  findContactPersonByID contactPersonID {} -->", contactPersonID);
        try {
            ContactPerson contactPerson = contactPersonService.findByID(contactPersonID);
            log.info("<--  findContactPersonByID contactPerson {} -->", contactPerson.printContactPerson());
            ContactPersonDTO contactPersonDTO = new ContactPersonDTO();
            contactPersonDTO.setFirst_name(contactPerson.getFirst_name());
            contactPersonDTO.setLast_name(contactPerson.getLast_name());
            contactPersonDTO.setEmail_address(contactPerson.getEmail_address());
            contactPersonDTO.setLand_line(contactPerson.getLand_line());
            contactPersonDTO.setCell_phone(contactPerson.getCell_phone());
            contactPersonDTO.setDesignation(contactPerson.getDesignation());
            contactPersonDTO.setClientID(contactPerson.getContactPersonID());
            contactPersonDTO.setContactPersonID(contactPerson.getContactPersonID());

            model.addAttribute("contactPersonDTO", contactPersonDTO);

        } catch (ContactNotFoundException contactNotFoundException) {
            model.addAttribute("contactPersonResponse", contactNotFoundException.getMessage());
            log.info("<--  findContactPersonByID contactNotFoundException {} -->", contactNotFoundException.getMessage());
        }
    }

    public void saveUpdatedContactPerson(ContactPersonDTO contactPersonDTO, Model model) {
        log.info("<--  saveUpdatedContactPerson  contactPersonID   {} -->", contactPersonDTO.printContactPersonDTO());

        try {
            ContactPerson existingContactPerson = contactPersonService.findByID(contactPersonDTO.getContactPersonID());
            try {
                log.info("<--  saveUpdatedContactPerson contactPerson {} -->", existingContactPerson.printContactPerson());

                if (existingContactPerson.getFirst_name() != null
                        && !existingContactPerson.getFirst_name().equalsIgnoreCase(contactPersonDTO.getFirst_name())) {
                    existingContactPerson.setFirst_name(contactPersonDTO.getFirst_name());
                }

                if (existingContactPerson.getLast_name() != null &&
                        !existingContactPerson.getLast_name().equalsIgnoreCase(contactPersonDTO.getLast_name())) {
                    existingContactPerson.setLast_name(contactPersonDTO.getLast_name());
                }

                if (existingContactPerson.getLand_line() != null &&
                        !existingContactPerson.getLand_line().equalsIgnoreCase(contactPersonDTO.getLand_line())) {
                    existingContactPerson.setLand_line(contactPersonDTO.getLand_line());
                }
                if (existingContactPerson.getEmail_address() != null &&
                        !existingContactPerson.getEmail_address().equalsIgnoreCase(contactPersonDTO.getEmail_address())) {
                    existingContactPerson.setEmail_address(contactPersonDTO.getEmail_address());
                }
                if (existingContactPerson.getDesignation() != null) {
                    if (!existingContactPerson.getDesignation().equalsIgnoreCase(contactPersonDTO.getDesignation())) {
                        existingContactPerson.setDesignation(contactPersonDTO.getDesignation());
                    }
                } else {
                    existingContactPerson.setDesignation(contactPersonDTO.getDesignation());
                }
                if (existingContactPerson.getCell_phone() != null &&
                        !existingContactPerson.getCell_phone().equalsIgnoreCase(contactPersonDTO.getCell_phone())) {
                    existingContactPerson.setCell_phone(contactPersonDTO.getCell_phone());
                }
                // ContactPerson c = clientService.saveUpdatedContact(contactPerson);
                ContactPerson savedContact = contactPersonService.save(existingContactPerson);
                if (savedContact != null) {
                    String response = "Contact Person: " + savedContact.getFirst_name() + " updated";
                    model.addAttribute("saveUpdatedContactResponse", response);

                    String updateClientResponse = "Contact person " + savedContact.getFirst_name() + "updated ";
                    model.addAttribute("updateClientResponse", updateClientResponse);


                    findClientContacts(savedContact.getClient().getClientID(), model);

                    log.info("<--  saveUpdatedContactResponse response {} -->", response);
                } else
                    throw new SaveContactPersonException("Failed to save contact person" + contactPersonDTO.getContactPersonID());
            } catch (SaveContactPersonException saveContactPersonException) {
                model.addAttribute("saveUpdatedContactResponse", saveContactPersonException.getMessage());
                log.info("<--  saveContactPersonException {} -->", saveContactPersonException.getMessage());
            }
        } catch (ContactNotFoundException contactNotFoundException) {
            model.addAttribute("saveUpdatedContactResponse", contactNotFoundException.getMessage());
            log.info("<--  saveUpdatedContactPerson contactNotFoundException  {} -->", contactNotFoundException.getMessage());
        }
    }

    // incoming String data validation

    public boolean cleanData(Blog blog) {
        blog.setBlog_description(Jsoup.clean(blog.getBlog_description(), Safelist.relaxed()));
        blog.setBody(Jsoup.clean(blog.getBody(), Safelist.relaxed()));
        boolean result = Jsoup.isValid(blog.getBody(), Safelist.relaxed())
                && Jsoup.isValid(blog.getBlog_description(), Safelist.relaxed());
        return result;

    }

    public boolean cleanData(BlogDTO blog) {
        blog.setBlog_description(Jsoup.clean(blog.getBlog_description(), Safelist.relaxed()));
        blog.setBody(Jsoup.clean(blog.getBody(), Safelist.relaxed()));
        boolean result = Jsoup.isValid(blog.getBody(), Safelist.relaxed())
                && Jsoup.isValid(blog.getBlog_description(), Safelist.relaxed());
        return result;

    }

    public boolean cleanData(VacancyDTO vacancy) {
        vacancy.setJob_description(Jsoup.clean(vacancy.getJob_description(), Safelist.relaxed()));
        vacancy.setRequirements(Jsoup.clean(vacancy.getRequirements(), Safelist.relaxed()));
        boolean result = Jsoup.isValid(vacancy.getJob_description(), Safelist.relaxed())
                && Jsoup.isValid(vacancy.getRequirements(), Safelist.relaxed());
        return result;
    }

    // EVENTS

/*    public boolean saveSubmissionEvent(Candidate candidate, Long vacancyID) {
        return applicationsEventPublisher.publishSaveSubmissionEvent(candidate, vacancyID);
    }*/

 /*   public void requestPasswordReset(Long employeeID, Model model, HttpServletRequest request) {

        Employee e = employeeService.findEmployeeByID(employeeID);
        try {
            sendPasswordResetVerificationEmail(WebPageURL.applicationURL(request), e);
            String passwordResetRequestResponse = "";
            model.addAttribute("passwordResetRequestResponse", passwordResetRequestResponse);

        } catch (Exception exception) {
            log.info("error");
        }

    }*/

   /* public void sendPasswordResetVerificationEmail(String url, Employee employee) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request Verification";
        String senderName = "Users Verification Service";
     *//*        communicationService.sentPasswordResetRequest(subject, senderName, employee);*//*
        communicationService.sentPasswordResetRequestEmail(url,employee);
    }
*/

}
