package za.co.recruitmentzone.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.client.dto.NewClientDTO;
import za.co.recruitmentzone.client.dto.ContactPersonDTO;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.client.entity.ClientNote;
import za.co.recruitmentzone.client.entity.ContactPerson;
import za.co.recruitmentzone.client.exception.ClientNotFoundException;
import za.co.recruitmentzone.client.exception.ContactNotFoundException;
import za.co.recruitmentzone.client.repository.ClientNoteRepository;
import za.co.recruitmentzone.client.repository.ClientRepository;
import za.co.recruitmentzone.client.repository.ContactPersonRepository;
import za.co.recruitmentzone.vacancy.entity.Vacancy;
import za.co.recruitmentzone.vacancy.exception.VacancyException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final ClientNoteRepository clientNoteRepository;

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    public ClientService(ClientRepository clientRepository, ClientNoteRepository clientNoteRepository) {
        this.clientRepository = clientRepository;
        this.clientNoteRepository = clientNoteRepository;
    }

    public Client saveNewClient(NewClientDTO newClientDTO) {
        log.info("<--- saveClient ---> clientDTO: \n {} ", newClientDTO.printClientDTO());
        Client client = new Client(newClientDTO.getName(), newClientDTO.getIndustry());
        client.setCreated(LocalDateTime.now());
        ContactPerson contactPerson = new ContactPerson(newClientDTO.getContactPerson_FirstName(),
                newClientDTO.getContactPerson_last_name(), newClientDTO.getContactPerson_email_address(),
                newClientDTO.getContactPerson_land_line(), newClientDTO.getContactPerson_cell_phone(),
                newClientDTO.getContactPerson_designation());
        client.addContactPerson(contactPerson);
        return clientRepository.save(client);
    }

    public Client saveExistingClient(Client client) {
        return clientRepository.save(client);
    }


    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client findClientByID(Long clientID) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientID);
        return optionalClient.orElseThrow(()-> new ClientNotFoundException("Client not found: "+clientID));
    }

    public Client addContactToClient(ContactPersonDTO contactPersonDTO) {
        Optional<Client> oc = clientRepository.findById(contactPersonDTO.getClientID());

        ContactPerson contactPerson = new ContactPerson(contactPersonDTO.getFirst_name(),
                contactPersonDTO.getLast_name(), contactPersonDTO.getEmail_address(),
                contactPersonDTO.getLand_line(), contactPersonDTO.getCell_phone(), contactPersonDTO.getDesignation());
        if (oc.isPresent()) {
            oc.get().addContactPerson(contactPerson);
        }
        return clientRepository.save(oc.get());
       
    }

    public Page<Client> findPaginatedClients(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return clientRepository.findAll(pageable);
    }

    public Page<ClientNote> findPaginatedClientNotes(int pageNo, int pageSize, String sortField, String sortDirection,Client client){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return clientNoteRepository.findAllByClient(pageable,client);
    }


}
