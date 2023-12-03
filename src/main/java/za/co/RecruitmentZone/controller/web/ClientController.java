package za.co.RecruitmentZone.controller.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.entity.Enums.BlogStatus;
import za.co.RecruitmentZone.entity.domain.Blog;
import za.co.RecruitmentZone.entity.domain.Client;
import za.co.RecruitmentZone.entity.domain.ContactPerson;
import za.co.RecruitmentZone.entity.domain.Employee;
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
    @GetMapping("/client-admin")
    public String clients(Model model) {
        List<Client> allClients = new ArrayList<>();
        try {
            allClients = recruitmentZoneService.getClients();
        } catch (Exception e) {
            log.info("Exception trying to retrieve employees, retrieving all employees ");
        }
        model.addAttribute("clients", allClients);
        return "fragments/clients/client-admin";
    }
    @GetMapping("/add-client")
    public String showCreateClientForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("contactPerson", new ContactPerson());
        return "fragments/clients/add-client";
    }
    @GetMapping("/add-contact")
    public String showAddContactForm(@RequestParam("clientID") Long clientID,Model model) {
        model.addAttribute("clientID",clientID);
        model.addAttribute("contactPerson",new ContactPerson());
        return "fragments/clients/add-contact";
    }
    @PostMapping("/save-client")
    public String saveClient(@Valid @ModelAttribute("client")Client client,
                           @Valid @ModelAttribute("contactPerson")ContactPerson contactPerson,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/add-client";
        }
        recruitmentZoneService.saveNewClient(client,contactPerson);
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
    @PostMapping("/view-client")
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
        log.info("Found {} number of contacts, {}",contacts.size());
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
    public String addContactToClient(@Valid @ModelAttribute("contactPerson")ContactPerson contactPerson, @RequestParam("clientID") Long clientID, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/update-client";
        }
        recruitmentZoneService.addContactToClient(clientID,contactPerson);
        return "redirect:/view-client";
    }
    @PostMapping("/save-updated-client")
    public String saveUpdatedClient(@Valid @ModelAttribute("client")Client client,
                                    @RequestParam("clientID") Long clientID,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/clients/update-client";
        }
        recruitmentZoneService.saveUpdatedClient(clientID,client);
        return "redirect:/client-administration";
    }

}
