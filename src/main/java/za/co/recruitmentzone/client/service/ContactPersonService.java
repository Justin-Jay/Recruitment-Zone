package za.co.recruitmentzone.client.service;

import org.springframework.stereotype.Component;
import za.co.recruitmentzone.client.entity.ContactPerson;
import za.co.recruitmentzone.client.repository.ContactPersonRepository;

@Component
public class ContactPersonService {

    private final ContactPersonRepository contactPersonRepository;

    public ContactPersonService(ContactPersonRepository contactPersonRepository) {
        this.contactPersonRepository = contactPersonRepository;
    }

    public ContactPerson save(ContactPerson contactPerson){
        return contactPersonRepository.save(contactPerson);
    }

    public ContactPerson findByID(long id){
        return contactPersonRepository.findById(id).orElseThrow();
    }
}
