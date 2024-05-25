package za.co.recruitmentzone.client.controller;


import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.client.dto.*;
import za.co.recruitmentzone.client.entity.ClientFile;
import za.co.recruitmentzone.documents.SaveFileException;
import za.co.recruitmentzone.service.RecruitmentZoneService;


import java.util.List;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/Client")
public class ClientController {

    private final RecruitmentZoneService recruitmentZoneService;

    private final Logger log = LoggerFactory.getLogger(ClientController.class);

    public ClientController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    @GetMapping("/client-administration")
    public String clientAdministration(Model model) {
        try {
            recruitmentZoneService.findAllClients(model);
           // throw new Exception("Client administration exception");
        } catch (Exception e) {
            log.info("<-- clientAdministration -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // clientsList , findAllClientsResponse
        return "/fragments/clients/client-administration";
    }


    @PostMapping("/add-client")
    public String showCreateClientForm(Model model) {
        try {
            model.addAttribute("newClientDTO", new NewClientDTO());
        } catch (Exception e) {
            log.info("<-- showCreateClientForm -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // clientDTO
        return "fragments/clients/add-client";
    }


    @PostMapping("/add-contact")
    public String showAddContactForm(@RequestParam("clientID") Long clientID,
                                     Model model) {
        try {
            model.addAttribute("clientID", clientID);
            model.addAttribute("contactPersonDTO", new ContactPersonDTO());
        } catch (Exception e) {
            log.info("<-- showAddContactForm -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // clientID , contactPersonDTO
        return "fragments/clients/add-contact";
    }
    @PostMapping("/save-new-contact")
    public String addContactToClient(@Valid @ModelAttribute("contactPersonDTO") ContactPersonDTO contactPersonDTO, BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasFieldErrors()) {
           recruitmentZoneService.findClientByID(contactPersonDTO.getClientID(), model);
            model.addAttribute("bindingResult", INTERNAL_SERVER_ERROR);
            // client , findClientByIDResponse , bindingResult
            return "fragments/clients/add-contact";
        }
        try {
            recruitmentZoneService.addContactToClient(contactPersonDTO, model);
        } catch (Exception e) {
            log.info("<-- addContactToClient -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //  clientID, contactPersonList,  addContactPersonResponse  , internalServerError
        return "fragments/clients/view-client-contacts";
    }

    @PostMapping("/save-client")
    public String saveClient(@Valid @ModelAttribute("newClientDTO") NewClientDTO newClientDTO,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {

            return "fragments/clients/add-client";
        }
        try {
            recruitmentZoneService.saveNewClient(model, newClientDTO);

        } catch (Exception e) {
            log.info("<-- saveClient -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //  saveNewClientResponse saveNewClientResponse , clientsList , findAllClientsResponse
        return "fragments/clients/view-client";
    }


    @PostMapping("/view-client")
    public String showClient(@RequestParam("clientID") Long clientID, Model model) {
        try {
            recruitmentZoneService.findClientByID(clientID, model);
            recruitmentZoneService.findClientNotes(clientID, model);
        } catch (Exception e) {
            log.info("<-- showClient -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // client ,findClientByIDResponse   , internalServerError
        return "fragments/clients/view-client";
    }

    @PostMapping("/view-client-contacts")
    public String showClientContacts(@RequestParam("clientID") Long clientID, Model model) {
        try {
            recruitmentZoneService.findClientContacts(clientID, model);
        } catch (Exception e) {
            log.info("<-- showClientContacts -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // clientID , contactPersonList , findContactPersonByClientIDResponse
        return "fragments/clients/view-client-contacts";
    }

    @PostMapping("/view-client-vacancies")
    public String showClientVacancies(@RequestParam("clientID") Long clientID, Model model) {
        try {
            recruitmentZoneService.loadClientVacancies(clientID, model);
           // recruitmentZoneService.findClientDocuments(model,clientID);
        } catch (Exception e) {
            log.info("<-- showClientContacts -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // clientVacancyList , internalServerError
        return "fragments/clients/view-client-vacancies";
    }

    @PostMapping("/update-client-contact")
    public String updateClientContact(@RequestParam("contactPersonID") Long contactPersonID, Model model) {
        try {
            recruitmentZoneService.findContactPersonByID(model, contactPersonID);
        } catch (Exception exception) {
            log.info("<-- updateClientContact -->  Exception \n {}", exception.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // contactPerson , contactPersonResponse
        return "fragments/clients/update-contact";
    }


    @PostMapping("/save-updated-contact")
    public String saveUpdatedContact(@Valid @ModelAttribute("contactPerson") ContactPersonDTO contactPersonDTO,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/update-contact";
        }
        try {
            recruitmentZoneService.saveUpdatedContactPerson(contactPersonDTO, model);
        } catch (Exception e) {
            log.info("<-- saveUpdatedContact -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //  saveUpdatedContactResponse, clientID , contactPersonList , findContactPersonByClientIDResponse
        return "fragments/clients/view-client-contacts";
    }

    @PostMapping("/update-client")
    public String updateClient(@RequestParam("clientID") Long clientID, Model model) {
        log.info("<-- updateClient --> clientID {}", clientID);
        try {
            recruitmentZoneService.findClientByID(clientID, model);

        } catch (Exception e) {
            log.info("<-- updateClient -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // client , findClientByIDResponse , internalServerError
        return "fragments/clients/update-client";
    }



    @PostMapping("/save-updated-client")
    public String saveUpdatedClient(@Valid @ModelAttribute("client") ExistingClientDTO client,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/update-client";
        }
        try {
            recruitmentZoneService.saveUpdatedClient(client, model);
        } catch (Exception e) {
            log.info("<-- saveUpdatedClient -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // client , saveUpdatedClientResponse
        return "fragments/clients/view-client";
    }

    // clientNote , existingNotes , findClientByIDResponse , internalServerError
    @PostMapping("/view-client-notes")
    public String showClientNotes(@RequestParam("clientID") Long clientID, Model model) {
        try {
            recruitmentZoneService.findClientNotes(clientID, model);
        } catch (Exception e) {
            log.info("<-- showClientNotes -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // existingNotes , clientNoteDTO , findClientByIDResponse
        return "fragments/clients/view-client-notes";
    }

    @PostMapping("/save-client-note")
    public String saveClientNote(@Valid @ModelAttribute("clientNoteDTO") ClientNoteDTO clientNote,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            log.info("HAS ERRORS");
            recruitmentZoneService.reloadClientNotes(clientNote.getClientID(), model);
            return "fragments/clients/view-client-notes";
        }
        try {
            recruitmentZoneService.saveNewClientNote(clientNote, model);
        } catch (Exception e) {
            log.info("<-- saveClientNote -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // existingNotes , clientNoteDTO , findClientByIDResponse
        return "fragments/clients/view-client-notes";
    }


    @PostMapping("/upload-document")
    public String saveDocument(@Valid @ModelAttribute("clientFileDTO") ClientFileDTO clientFileDTO,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("<-- saveDocument  hasErrors --> ");
            recruitmentZoneService.findClientDocuments(model, clientFileDTO.getClientID());
            model.addAttribute("bindingResult", INTERNAL_SERVER_ERROR);

            return "fragments/clients/view-client";
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
                        model.addAttribute("createCandidateFileResponse", "File Upload Successful");
                        recruitmentZoneService.findCandidateDocuments(model, clientFileDTO.getClientID());
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
        return "fragments/clients/view-client";
    }

}
