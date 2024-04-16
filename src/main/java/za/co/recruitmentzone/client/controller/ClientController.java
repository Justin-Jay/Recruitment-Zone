package za.co.recruitmentzone.client.controller;

import com.google.cloud.storage.Storage;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.recruitmentzone.client.dto.ClientDTO;
import za.co.recruitmentzone.client.dto.ClientNoteDTO;
import za.co.recruitmentzone.client.dto.ContactPersonDTO;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.client.entity.ClientNote;
import za.co.recruitmentzone.client.entity.ContactPerson;
import za.co.recruitmentzone.client.exception.*;
import za.co.recruitmentzone.service.RecruitmentZoneService;

import java.util.List;
import java.util.Set;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/Client")
public class ClientController {

    private final RecruitmentZoneService recruitmentZoneService;
    private final Storage storage;
    private final Logger log = LoggerFactory.getLogger(ClientController.class);

    public ClientController(RecruitmentZoneService recruitmentZoneService, Storage storage) {
        this.recruitmentZoneService = recruitmentZoneService;
        this.storage = storage;
    }
//       log.info("<-- viewCandidate --> model: \n {}", model);
//       log.info("<-- saveNote -->  Exception \n {}",e.getMessage());

    @GetMapping("/client-administration")
    public String clientAdministration(Model model, @ModelAttribute("saveUpdatedContactResponse") String saveUpdatedContactResponse, @ModelAttribute("internalServerError") String internalServerError,
                                       @ModelAttribute("saveNewClientResponse") String saveNewClientResponse, @ModelAttribute("saveUpdatedClientResponse") String saveUpdatedClientResponse,
                                       @ModelAttribute("saveUpdatedClientResponse") String addContactPersonResponse) {
        try {
            recruitmentZoneService.findAllClients(model);
            model.addAttribute("saveUpdatedClientResponse", saveUpdatedClientResponse);
            model.addAttribute("saveNewClientResponse", saveNewClientResponse);
            model.addAttribute("saveUpdatedContactResponse", saveUpdatedContactResponse);
            model.addAttribute("addContactPersonResponse", addContactPersonResponse);
            model.addAttribute("internalServerError", internalServerError);
        } catch (Exception e) {
            log.info("<-- clientAdministration -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "/fragments/clients/client-administration";
    }



    // ClientDTO , internalServerError
    @GetMapping("/add-client")
    public String showCreateClientForm(Model model) {
        try {
            model.addAttribute("ClientDTO", new ClientDTO());
        } catch (Exception e) {
            log.info("<-- showCreateClientForm -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/clients/add-client";
    }
    // clientID , contactPersonDTO, internalServerError
    @GetMapping("/add-contact")
    public String showAddContactForm(@RequestParam("clientID") Long clientID,
                                     Model model) {
        try {
            model.addAttribute("clientID", clientID);
            model.addAttribute("contactPersonDTO", new ContactPersonDTO());
        } catch (Exception e) {
            log.info("<-- showAddContactForm -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/clients/add-contact";
    }
    // clientID , contactPersonDTO, internalServerError

    // redirectAttributes = saveNewClientResponse ,  saveNewClientResponse  , internalServerError
    @PostMapping("/save-client")
    public String saveClient(@Valid @ModelAttribute("ClientDTO") ClientDTO clientDTO,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/add-client";
        }
        try {
            recruitmentZoneService.saveNewClient(redirectAttributes, clientDTO);
        } catch (Exception e) {
            log.info("<-- saveClient -->  Exception \n {}",e.getMessage());
            redirectAttributes.addFlashAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "redirect:/Client/client-administration";
    }

    // model = internalServerError client , findClientByIDResponse
    @GetMapping("/view-client")
    public String showClient(@RequestParam("clientID") Long clientID, Model model) {
        try {
            recruitmentZoneService.findClientByID(clientID, model);
        } catch (Exception e) {
            log.info("<-- showClient -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/clients/view-client";
    }

// clientID , contacts, findContactPersonByClientID , internalServerError
    @PostMapping("/view-client-contacts")
    public String showClientContacts(@RequestParam("clientID") Long clientID, Model model) {
        try {
            recruitmentZoneService.findContactPersonByClientID(clientID, model);
        } catch (Exception e) {
            log.info("<-- showClientContacts -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/clients/view-client-contacts";
    }

    // contactPerson, contactPersonResponse, internalServerError
    @PostMapping("/update-client-contact")
    public String updateClientContact(@RequestParam("contactPersonID") Long contactPersonID, Model model) {
        try {
            recruitmentZoneService.findContactPersonByID(model, contactPersonID);
        } catch (Exception exception) {
            log.info("<-- updateClientContact -->  Exception \n {}",exception.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/clients/update-contact";
    }


   // saveUpdatedContactResponse , internalServerError
    @PostMapping("/save-updated-contact")
    public String saveUpdatedContact(@Valid @ModelAttribute("contactPerson") ContactPersonDTO contactPersonDTO,
                                     @RequestParam("contactPersonID") Long contactPersonID,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/update-contact";
        }
        try {
            recruitmentZoneService.saveUpdatedContactPerson(contactPersonID, contactPersonDTO, redirectAttributes);
        } catch (Exception e) {
            log.info("<-- saveUpdatedContact -->  Exception \n {}",e.getMessage());
            redirectAttributes.addFlashAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "redirect:/Client/client-administration";
    }
    // client , findClientByIDResponse , internalServerError
    @PostMapping("/update-client")
    public String updateClient(@RequestParam("clientID") Long clientID, Model model) {
        log.info("<-- updateClient --> clientID {}", clientID);
        try {
            recruitmentZoneService.findClientByID(clientID, model);
        } catch (Exception e) {
            log.info("<-- updateClient -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/clients/update-client";
    }
    //    internalServerError,  addContactPersonResponse
    @PostMapping("/save-new-contact")
    public String addContactToClient(@Valid @ModelAttribute("ContactPersonDTO") ContactPersonDTO contactPersonDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/update-client";
        }
        try {
            recruitmentZoneService.addContactToClient(contactPersonDTO, redirectAttributes);
        } catch (Exception e) {
            log.info("<-- addContactToClient -->  Exception \n {}",e.getMessage());
            redirectAttributes.addFlashAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "redirect:/Client/client-administration";
    }
    //    internalServerError,  addContactPersonResponse

    @PostMapping("/save-updated-client")
    public String saveUpdatedClient(@Valid @ModelAttribute("client") Client client,
                                    @RequestParam("clientID") Long clientID,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/update-client";
        }
        try {
            recruitmentZoneService.saveUpdatedClient(client, redirectAttributes);
        } catch (Exception e) {
            log.info("<-- saveUpdatedClient -->  Exception \n {}",e.getMessage());
            redirectAttributes.addFlashAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "redirect:/Client/client-administration";
    }

    // clientNote , existingNotes , findClientByIDResponse , internalServerError
    @PostMapping("/view-client-notes")
    public String showClientNotes(@RequestParam("clientID") Long clientID, Model model) {
        try {
            recruitmentZoneService.findClientNotes(clientID, model);
        } catch (Exception e) {
            log.info("<-- showClientNotes -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/clients/view-client-notes";
    }

    @PostMapping("/save-client-note")
    public String saveClientNote(@Valid @ModelAttribute("clientNote") ClientNoteDTO clientNote,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            log.info("HAS ERRORS");
            model.addAttribute("noteSaved", Boolean.FALSE);
            return "fragments/clients/view-client-notes";
        }
        try {
            recruitmentZoneService.saveNewClientNote(clientNote, model);
        } catch (Exception e) {
            log.info("<-- saveClientNote -->  Exception \n {}",e.getMessage());
            /*   model.addAttribute("noteSaved", Boolean.FALSE);*/
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/clients/view-client-notes";
    }

}
