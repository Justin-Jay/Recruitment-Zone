package za.co.RecruitmentZone.client.controller;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.candidate.dto.CandidateNoteDTO;
import za.co.RecruitmentZone.candidate.entity.Candidate;
import za.co.RecruitmentZone.candidate.entity.CandidateNote;
import za.co.RecruitmentZone.client.dto.ClientDTO;
import za.co.RecruitmentZone.client.dto.ClientNoteDTO;
import za.co.RecruitmentZone.client.dto.ContactPersonDTO;
import za.co.RecruitmentZone.client.entity.Client;
import za.co.RecruitmentZone.client.entity.ClientNote;
import za.co.RecruitmentZone.client.entity.ContactPerson;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ClientController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Storage storage; // Injected or initialized as needed

    private final Logger log = LoggerFactory.getLogger(ClientController.class);

    public ClientController(RecruitmentZoneService recruitmentZoneService, Storage storage) {
        this.recruitmentZoneService = recruitmentZoneService;
        this.storage = storage;
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
    @PostMapping("/view-client-notes")
    public String showClientNotes(@RequestParam("clientID") Long clientID, Model model) {
        Client client = recruitmentZoneService.findClientByID(clientID);
        Set<ClientNote> notes = client.getNotes();
        model.addAttribute("existingNotes", notes);

        ClientNoteDTO dto = new ClientNoteDTO();
        dto.setClientID(clientID);
        log.info("clientID: {}",clientID);
        model.addAttribute("clientNote",dto);
        return "fragments/clients/view-client-notes";
    }

    @PostMapping("/save-client-note")
    public String saveNote(@Valid @ModelAttribute("clientNote")ClientNoteDTO clientNote,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            log.info("HAS ERRORS");
            model.addAttribute("noteSaved", Boolean.FALSE);
            return "fragments/clients/view-client-notes";
        }


        Long clientID = clientNote.getClientID();
        Client client = recruitmentZoneService.findClientByID(clientID);
        client.addNote(clientNote);

        boolean noteSaved = recruitmentZoneService.saveClient(client);
        model.addAttribute("noteSaved", noteSaved);

        Set<ClientNote> notes = client.getNotes();
        // sort the set according to date
        model.addAttribute("existingNotes",notes );
        ClientNoteDTO dto = new ClientNoteDTO();
        dto.setClientID(clientID);
        log.info("clientID: {}",clientID);
        model.addAttribute("clientNote",dto);
        return "fragments/clients/view-client-notes";
    }

    @GetMapping("/download-document/{id}")
    public ResponseEntity<InputStreamResource> downloadDocument(@PathVariable String id) throws IOException {
        // Specify the path to the file on your local machine
        id = "_1.pdf";
        String filePath = "C:\\uploads\\Spring_details"+ id; // Update with the actual path

        // Read the file from the local machine
        InputStream inputStream = new FileInputStream(filePath);

        // Prepare the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", id); // Use the file name as attachment name

        // Create an InputStreamResource from the file's content
        InputStreamResource resource = new InputStreamResource(inputStream);

        // Return the ResponseEntity with the InputStreamResource and headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    // upload to GCP
/*    @GetMapping("/download-document/{id}")
    public ResponseEntity<InputStreamResource> downloadDocument(@PathVariable String id) throws IOException {
        // Replace "your-bucket-name" with the actual name of your Google Cloud Storage bucket
        String bucketName = "your-bucket-name";
        String objectName = id; // Assuming the object name is the same as the document ID

        // Retrieve the file from Google Cloud Storage
        Blob blob = storage.get(bucketName, objectName);

        // Prepare the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", blob.getName());

        // Create an InputStreamResource from the Blob's content
        InputStream inputStream = new ByteArrayInputStream(blob.getContent());
        InputStreamResource resource = new InputStreamResource(inputStream);

        // Return the ResponseEntity with the InputStreamResource and headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }*/

}
