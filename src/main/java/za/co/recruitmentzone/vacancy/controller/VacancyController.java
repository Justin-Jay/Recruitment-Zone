package za.co.recruitmentzone.vacancy.controller;

import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.client.dto.ClientFileDTO;
import za.co.recruitmentzone.client.entity.ClientFile;
import za.co.recruitmentzone.documents.SaveFileException;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.vacancy.dto.VacancyDTO;
import za.co.recruitmentzone.vacancy.dto.VacancyStatusDTO;


import java.util.List;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/Vacancy")
public class VacancyController {

    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(VacancyController.class);

    public VacancyController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    @GetMapping("/vacancy-administration")
    public String vacancies(Model model) {
        try {
            int pageSize = 10;
            //recruitmentZoneService.getAllVacancies(model);
           recruitmentZoneService.getAllVacancies(model, 1,pageSize, "created", "asc");
        } catch (Exception e) {
            log.error("<-- vacancies -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //  vacancyList , loadVacanciesResponse
        return "fragments/vacancy/vacancy-administration";
    }


    @GetMapping("/paginatedVacancies/{pageNo}")
    public String findPaginatedVacancies(@PathVariable(value = "pageNo") int pageNo,
                                            @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDirection, Model model) {
        int pageSize = 10;
        log.info("Page number  {}", pageNo);
        log.info("sortField {}", sortField);
        log.info("sortDirection {}", sortDirection);
        recruitmentZoneService.getAllVacancies(model, pageNo, pageSize, sortField, sortDirection);
        return "fragments/vacancy/vacancy-administration :: vacancy-admin-list";
    }



    @GetMapping("/add-vacancy")
    public String showCreateVacancyForm(Model model) {
        try {
            recruitmentZoneService.addVacancy(model);
        } catch (Exception e) {
            log.error("<-- showCreateVacancyForm -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //  vacancyDTO , clients , findAllClientsResponse  , employeeList loadEmployeesResponse , internalServerError
        return "fragments/vacancy/add-vacancy";
    }

    @PostMapping("/save-vacancy")
    public String saveVacancy(@Valid @ModelAttribute("vacancyDTO") VacancyDTO vacancy,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            recruitmentZoneService.reloadCreateVacancy(model);
            return "fragments/vacancy/add-vacancy";
        }
        try {
            recruitmentZoneService.saveNewVacancy(vacancy, model);
        } catch (Exception e) {
            log.error("<-- saveVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }

        // saveVacancyResponse , internalServerError
        return "fragments/vacancy/view-home-vacancy";
    }

    @PostMapping("/view-vacancy")
    public String showVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            recruitmentZoneService.findVacancy(vacancyID, model);
        } catch (Exception e) {
            log.error("<-- showVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancy  , findVacancyResponse
        return "fragments/vacancy/view-vacancy";
    }

    @PostMapping("/view-home-vacancy")
    public String showHomeVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            recruitmentZoneService.findVacancy(vacancyID, model);
        } catch (Exception e) {
            log.error("<-- showVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }

        return "fragments/vacancy/view-home-vacancy";
    }


    @PostMapping("/update-vacancy")
    public String updateVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            recruitmentZoneService.findVacancy(vacancyID, model);
            //model.addAttribute("vacancyStatusValues", VacancyStatus.values());
        } catch (Exception e) {
            log.error("<-- updateVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancy , findVacancyResponse
        return "fragments/vacancy/update-vacancy";
    }

    @PostMapping("/save-updated-vacancy")
    public String saveUpdatedVacancy(@Valid @ModelAttribute("vacancy") VacancyDTO vacancy, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "fragments/vacancy/update-vacancy";
        }
        try {

            log.info("<--- saveUpdatedVacancy description: ---> \n {} ", vacancy.getJob_description());
            boolean cleanInput;
            cleanInput = recruitmentZoneService.cleanData(vacancy);
            if (cleanInput) {
                recruitmentZoneService.updateVacancy(vacancy, model);
            } else {
                model.addAttribute("dirtyData", "Failed to sanitize input");
                return "fragments/vacancy/update-vacancy";
            }


        } catch (Exception e) {
            log.error("<-- saveUpdatedVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancy ,  saveVacancyResponse
        //  return "fragments/vacancy/view-vacancy";
        return "fragments/vacancy/view-home-vacancy";
    }

    @PostMapping("/view-vacancy-submission")
    public String viewVacancySubmissions(@RequestParam("vacancyID") Long vacancyID,
                                         Model model) {
        try {
            int pageSize = 5;
            recruitmentZoneService.findVacancySubmissions(vacancyID, model, 1, pageSize, "date_received", "asc");
            model.addAttribute("applicationStatus", ApplicationStatus.values());
        } catch (Exception e) {
            log.error("<-- viewVacancySubmissions -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/vacancy/view-vacancy-submission";
    }

    @GetMapping("/paginatedSubmissions/{vacancyID}/{pageNo}")
    public String findPaginatedSubmissions(@PathVariable("vacancyID") Long vacancyID, @PathVariable(value = "pageNo") int pageNo,
                                            @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDirection, Model model) {
        int pageSize = 5;
        log.info("Page number vacancyID {}", vacancyID);
        log.info("Page number  {}", pageNo);
        log.info("sortField {}", sortField);
        log.info("sortDirection {}", sortDirection);
        recruitmentZoneService.findVacancySubmissions(vacancyID, model, pageNo, pageSize, sortField, sortDirection);
        return "fragments/vacancy/view-vacancy-submission";
    }


    @PostMapping("/search-vacancies")
    public String searchVacancies(@RequestParam(name = "title") String title, Model model) {
        try {
            log.info("Searching for {}", title);
            recruitmentZoneService.searchVacancyByTitle(title, model);

            log.info("Vacancy search completed");

        } catch (Exception e) {
            model.addAttribute("internalServerError", e.getMessage());
        }
        return "fragments/layout/search-results";
    }


    @PostMapping("/update-vacancy-status")
    public String updateVacancyStatus(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            recruitmentZoneService.findVacancyStatus(vacancyID, model);
        } catch (Exception e) {
            log.error("<-- updateVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancy , findVacancyResponse
        return "fragments/vacancy/update-vacancy-status";
    }

    @PostMapping("/save-updated-vacancy-status")
    public String saveUpdatedStatus(@Valid @ModelAttribute("vacancyStatusDTO") VacancyStatusDTO vacancyStatusDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {

            return "fragments/vacancy/update-vacancy-status";
        }
        try {
            recruitmentZoneService.saveNewVacancyStatus(vacancyStatusDTO, model);
            recruitmentZoneService.findVacancyStatus(vacancyStatusDTO.getVacancyID(), model);
        } catch (Exception e) {
            log.error("<-- updateVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancy , findVacancyResponse
        return "fragments/vacancy/update-vacancy-status";
    }

    @PostMapping("/view-vacancy-documents")
    public String vacancyDocs(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            int pageSize = 5;
            recruitmentZoneService.findVacancyDocuments(model, vacancyID, 1, pageSize, "created", "asc");
        } catch (Exception e) {
            log.error("<-- vacancyDocs -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // candidate, candidateFileDTO , existingDocuments,  internalServerError , findCandidateDocumentsResponse
        return "fragments/vacancy/vacancy-documents";

    }

    @GetMapping("/paginatedVacancyDocuments/{vacancyID}/{pageNo}")
    public String findPaginatedVacancyDocuments(@PathVariable(name = "vacancyID") long vacancyID,@PathVariable(name="pageNo") int pageNo,
                                                @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDirection, Model model) {
        int pageSize = 5;
        log.info("Page number  {}", pageNo);
        log.info("sortField {}", sortField);
        log.info("sortDirection {}", sortDirection);
        recruitmentZoneService.findVacancyDocuments(model, vacancyID,pageNo, pageSize, sortField, sortDirection);
        return "fragments/vacancy/vacancy-documents";
    }



    @PostMapping("/upload-document")
    public String saveDocument(@Valid @ModelAttribute("clientFileDTO") ClientFileDTO clientFileDTO,@RequestParam("pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,@RequestParam("sortDir") String sortDir,@RequestParam("clientID") long clientID,
                               @RequestParam("vacancyID") long vacancyID,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            log.error("<-- saveDocument  hasErrors --> ");
            List<ObjectError> objectErrors = bindingResult.getAllErrors();

            for (ObjectError objectError : objectErrors) {
                log.error(objectError.getDefaultMessage(), objectError);
            }

            recruitmentZoneService.reloadVacancyDocuments(model, clientFileDTO.getClientID());
            model.addAttribute("bindingResult", INTERNAL_SERVER_ERROR);
            return "fragments/vacancy/vacancy-documents";
        }
        try {
            boolean validFile = false;
            try {
                validFile = recruitmentZoneService.validateFile(clientFileDTO.getFileMultipart());
            } catch (FileUploadException fileUploadException) {
                log.error("<-- fileUploadException  {} --> ", fileUploadException.getMessage());
                model.addAttribute("invalidFileUpload", fileUploadException.getMessage());
            }
            if (validFile) {
                try {
                    ClientFile file = recruitmentZoneService.createClientFile(clientFileDTO);
                    if (file != null) {
                        log.info("saveDocument file is not null");
                        model.addAttribute("createClientFileResponse", "File Upload Successful");
                       // recruitmentZoneService.findVacancyDocuments(model, clientFileDTO.getVacancyID());
                        log.info("Page number  {}", pageNo);
                        log.info("sortField {}", sortField);
                        log.info("sortDirection {}", sortDir);
                        recruitmentZoneService.findVacancyDocuments(model, vacancyID,pageNo, 5, sortField, sortDir);
                    }

                } catch (SaveFileException saveFileException) {
                    log.error(saveFileException.getMessage());
                    // model.addAttribute("createCandidateFileResponse", "File Upload Successful");
                    model.addAttribute("saveDocumentResponse", saveFileException.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("<-- saveDocument -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/vacancy/vacancy-documents";
    }







}
