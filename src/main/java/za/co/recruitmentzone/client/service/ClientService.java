package za.co.recruitmentzone.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.client.dto.NewClientDTO;
import za.co.recruitmentzone.client.dto.ContactPersonDTO;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.client.entity.ContactPerson;
import za.co.recruitmentzone.client.exception.ContactNotFoundException;
import za.co.recruitmentzone.client.repository.ClientRepository;
import za.co.recruitmentzone.client.repository.ContactPersonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client saveNewClient(NewClientDTO newClientDTO) {
        log.info("<--- saveClient ---> clientDTO: \n {} ", newClientDTO.printClientDTO());
        Client client = new Client(newClientDTO.getName(), newClientDTO.getIndustry());
        client.setCreated(LocalDateTime.now());
        ContactPerson contactPerson = new ContactPerson(newClientDTO.getContactPerson_FirstName(),
                newClientDTO.getContactPerson_last_name(), newClientDTO.getContactPerson_email_address(),
                newClientDTO.getContactPerson_land_line(), newClientDTO.getContactPerson_cell_phone(),
                newClientDTO.getContactPerson_designation());
        //contactPerson.setClient(client);
        // contactPerson.setCreated(LocalDateTime.now());
        client.addContactPerson(contactPerson);
        clientRepository.save(client);
        return client;
    }

    public Client saveExistingClient(Client client) {
        return clientRepository.save(client);
    }


    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }


    public Client findClientByID(Long clientID) {
        Optional<Client> op = clientRepository.findById(clientID);
        Client c = null;
        if (op.isPresent()) {
            c = op.get();
        }
        return c;
    }


    public Client addContactToClient(ContactPersonDTO contactPersonDTO) {
        Optional<Client> oc = clientRepository.findById(contactPersonDTO.getClientID());
        ContactPerson contactPerson = new ContactPerson(contactPersonDTO.getFirst_name(),
                contactPersonDTO.getLast_name(), contactPersonDTO.getEmail_address(),
                contactPersonDTO.getLand_line(), contactPersonDTO.getCell_phone(), contactPersonDTO.getDesignation());
        if (oc.isPresent()) {
            //contactPerson.setClient(oc.get());
            oc.get().addContactPerson(contactPerson);
        }
        return clientRepository.save(oc.get());
       
    }


}
