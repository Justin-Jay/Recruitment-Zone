package za.co.RecruitmentZone.client.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.client.dto.ClientDTO;
import za.co.RecruitmentZone.client.dto.ContactPersonDTO;
import za.co.RecruitmentZone.client.entity.Client;
import za.co.RecruitmentZone.client.entity.ContactPerson;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("*")
public class ClientController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(ClientController.class);

    public ClientController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }
    
    @GetMapping("/add-client")
    public String showCreateClientForm(Model model) {
        //model.addAttribute("clients", new Client());
        //model.addAttribute("contactPerson", new ContactPerson());
        model.addAttribute("ClientDTO",new ClientDTO());
        return "fragments/clients/add-client";
    }

    @GetMapping("/add-contact")
    public String showAddContactForm(@RequestParam("clientID") Long clientID,
                                    Model model) {
        model.addAttribute("clientID", clientID);
       // model.addAttribute("contactPerson", new ContactPerson());
        model.addAttribute("contactPersonDTO", new ContactPersonDTO());
        return "fragments/clients/add-contact";
    }

    @PostMapping("/save-client")
    public String saveClient(@Valid @ModelAttribute("ClientDTO") ClientDTO clientDTO,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/add-client";
        }
        recruitmentZoneService.saveNewClient(clientDTO);
        return "redirect:/client-administration";
    }

    @GetMapping("/client-administration")
    public String clientAdministration(Model model) {
        List<Client> allClients = new ArrayList<>();
        try {
            allClients = recruitmentZoneService.getClients();
        } catch (Exception e) {
            log.info("Exception trying to retrieve blogs, retrieving all vacancies ");
        }
        model.addAttribute("clients", allClients);
        return "/fragments/clients/client-administration";
    }

    @GetMapping("/view-client")
    public String showClient(@RequestParam("clientID") Long clientID, Model model) {
        Client client = recruitmentZoneService.findClientByID(clientID);
        List<ContactPerson> contacts = recruitmentZoneService.findContactsByClientID(clientID);
        log.info("Looking for {}", clientID);
        log.info(client.toString());
        model.addAttribute("client", client);
        model.addAttribute("contacts", contacts);
        return "fragments/clients/view-client";
    }

    @PostMapping("/view-client-contacts")
    public String showClientContacts(@RequestParam("clientID") Long clientID, Model model) {
        List<ContactPerson> contacts = recruitmentZoneService.findContactsByClientID(clientID);
        log.info("Looking for {}", clientID);
        log.info("Found {} number of contacts", contacts.size());
        model.addAttribute("clientID", clientID);
        model.addAttribute("contacts", contacts);
        return "fragments/clients/view-client-contacts";
    }

    @PostMapping("/update-client")
    public String updateClient(@RequestParam("clientID") Long clientID, Model model) {
        Client client = recruitmentZoneService.findClientByID(clientID);
        model.addAttribute("client", client);
        return "fragments/clients/update-client";
    }

    @PostMapping("/save-new-contact")
    public String addContactToClient(@Valid @ModelAttribute("ContactPersonDTO") ContactPersonDTO contactPersonDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/update-client";
        }
        recruitmentZoneService.addContactToClient(contactPersonDTO);
        return "redirect:/client-administration";
    }

    @PostMapping("/save-updated-client")
    public String saveUpdatedClient(@Valid @ModelAttribute("client") Client client,
                                    @RequestParam("clientID") Long clientID,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/update-client";
        }
        recruitmentZoneService.saveUpdatedClient(clientID, client);
        return "redirect:/client-administration";
    }

}
