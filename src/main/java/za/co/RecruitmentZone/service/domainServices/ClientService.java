package za.co.RecruitmentZone.service.domainServices;

import org.apache.poi.sl.draw.geom.GuideIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.domain.Client;
import za.co.RecruitmentZone.entity.domain.ContactPerson;
import za.co.RecruitmentZone.entity.domain.JoinTables.ClientContactPerson;
import za.co.RecruitmentZone.repository.ClientRepository;
import za.co.RecruitmentZone.repository.ContactPersonRepository;

import javax.swing.text.html.Option;
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

    public void saveClient(Client client, ContactPerson contactPerson){
        clientRepository.save(client);
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

    public void addContactToClient(Long clientID,ContactPerson contactPerson){
        Optional<Client> oc = clientRepository.findById(clientID);
       if(oc.isPresent()){
           contactPerson.setClient(oc.get());
       }
        contactPersonRepository.save(contactPerson);
    }


}
