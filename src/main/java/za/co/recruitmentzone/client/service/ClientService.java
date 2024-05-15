package za.co.recruitmentzone.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.client.dto.ClientDTO;
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

    private final ContactPersonRepository contactPersonRepository;


    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    public ClientService(ClientRepository clientRepository, ContactPersonRepository contactPersonRepository) {
        this.clientRepository = clientRepository;
        this.contactPersonRepository = contactPersonRepository;
    }

    public Client saveClient(ClientDTO clientDTO){
        Client client = new Client(clientDTO.getName(),clientDTO.getIndustry());
        client.setCreated(LocalDateTime.now());
        ContactPerson contactPerson = new ContactPerson(clientDTO.getContactPerson_FirstName(),
                clientDTO.getContactPerson_last_name(),clientDTO.getContactPerson_email_address(),clientDTO.getContactPerson_land_line(),clientDTO.getContactPerson_cell_phone(),clientDTO.getContactPerson_designation());
        contactPerson.setClient(client);
        contactPerson.setCreated(LocalDateTime.now());
        clientRepository.save(client);
        return client;
    }
    public Client saveUpdatedClient(Client client){
        return clientRepository.save(client);
    }

    public ContactPerson saveUpdatedContact(ContactPerson contactPerson){
        return contactPersonRepository.save(contactPerson);
    }


    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }


    public Client findClientByID(Long clientID){
        Optional<Client> op = clientRepository.findById(clientID);
        Client c = null;
        if (op.isPresent()){
            c = op.get();
        }
        return c;
    }

    public List<ContactPerson> findContactPersonsByClientID(Long clientID){
        return contactPersonRepository.findContactPersonByClient_ClientID(clientID);
    }

    public ContactPerson addContactToClient(ContactPersonDTO contactPersonDTO){
        Optional<Client> oc = clientRepository.findById(contactPersonDTO.getclientID());
        ContactPerson contactPerson = new ContactPerson(contactPersonDTO.getFirst_name(),
                contactPersonDTO.getLast_name(),contactPersonDTO.getEmail_address(),
                contactPersonDTO.getLand_line(),contactPersonDTO.getCell_phone(),contactPersonDTO.getDesignation());
       if(oc.isPresent()){
           contactPerson.setClient(oc.get());
       }
        return contactPersonRepository.save(contactPerson);
    }

    public ContactPerson findContactsByID(Long contactPersonID){
        Optional<ContactPerson> opc = contactPersonRepository.findById(contactPersonID);
        return opc.orElseThrow(()-> new ContactNotFoundException("Contact Person not found: "+contactPersonID));
    }
}
