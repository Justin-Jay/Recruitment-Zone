package za.co.RecruitmentZone.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.client.dto.ClientDTO;
import za.co.RecruitmentZone.client.dto.ContactPersonDTO;
import za.co.RecruitmentZone.client.entity.Client;
import za.co.RecruitmentZone.client.entity.ContactPerson;
import za.co.RecruitmentZone.client.repository.ClientRepository;
import za.co.RecruitmentZone.client.repository.ContactPersonRepository;

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

    public void saveClient(ClientDTO clientDTO){
        Client client = new Client(clientDTO.getName(),clientDTO.getIndustry());
        clientRepository.save(client);
        ContactPerson contactPerson = new ContactPerson(clientDTO.getContactPerson_FirstName(),
                clientDTO.getContactPerson_last_name(),clientDTO.getContactPerson_email_address(),clientDTO.getContactPerson_land_line(),clientDTO.getContactPerson_cell_phone());
        contactPerson.setClient(client);
        contactPersonRepository.save(contactPerson);
    }
    public void saveUpdatedClient(Client client){
        clientRepository.save(client);
    }


    public List<Client> findAllClients(){
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

    public List<ContactPerson> findContactsByClientID(Long clientID){
        //return contactPersonRepository.findContactPeopleByClientID(clientID);
        return contactPersonRepository.findContactPersonByClient_ClientID(clientID);
    }

    public void addContactToClient(ContactPersonDTO contactPersonDTO){
        Optional<Client> oc = clientRepository.findById(contactPersonDTO.getClientID());
        ContactPerson contactPerson = new ContactPerson(contactPersonDTO.getFirst_name(),
                contactPersonDTO.getLast_name(),contactPersonDTO.getEmail_address(),
                contactPersonDTO.getLand_line(),contactPersonDTO.getCell_phone());
       if(oc.isPresent()){
           contactPerson.setClient(oc.get());
       }
        contactPersonRepository.save(contactPerson);
    }


}
